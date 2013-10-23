package com.ehensin.process;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import com.ehensin.seda.StageContext;
import com.ehensin.seda.StageGraph;
import com.ehensin.seda.spi.ICallBack;
import com.ehensin.seda.spi.IEvent;
import com.ehensin.seda.spi.IStageEventHandler;
/**
 * 
 * 
 * */
public class DefaultDataProcedureFactory implements IDataProcedureFactory {
    private Map<String, IDataProcedure> pEngineMap;
	@Override
	public void init(String xmlLocation, Map<String, String> initParam) throws Exception {
		
		InputStream in = DefaultDataProcedureFactory.class.getResourceAsStream(xmlLocation);
		String xml = inputStreamTOString(in);
		Pattern p = Pattern.compile("\\$\\{.*}");
		Matcher matcher = p.matcher(xml);
		StringBuffer sb = new StringBuffer();
		while(matcher.find()){
			String param = matcher.group();
			String key = param.replace("${", "").replace("}", "");
			if(initParam.get(key) != null)
			   matcher.appendReplacement(sb, initParam.get(key));
		}
		matcher.appendTail(sb);
        /**/
		JAXBContext	context = JAXBContext.newInstance(XmlRootProccesses.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		XmlRootProccesses	config = (XmlRootProccesses) unmarshaller.unmarshal(new StringReader(sb.toString()));
		/**/
		pEngineMap = new HashMap<String, IDataProcedure>();
		for( XmlProcess pDes : config.getProcesses() ){
			IDataProcedure engine = buildProcedure(pDes);
			if( engine != null )
			    pEngineMap.put(pDes.getName(), engine);
		}        
	}

	@Override
	public IDataProcedure getProcedure(String processName) {
		return pEngineMap.get(processName);
	}

	/**/
	private String inputStreamTOString(InputStream in) throws Exception {

		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] data = new byte[4096];
		int count = -1;
		while ((count = in.read(data, 0, 4096)) != -1)
			outStream.write(data, 0, count);

		data = null;
		return new String(outStream.toByteArray(), "UTF-8");
	}
	
	private IDataProcedure buildProcedure(XmlProcess processDes) throws Exception{
		
		/*stage graph*/
		List<XmlProcessStage> stages = processDes.getStages();
		Map<String,  StageContext> stageMap = new HashMap<String, StageContext>();
		StageGraph graph = new StageGraph();
		for(XmlProcessStage stage : stages){
			StageContext cxt = buildStageContext(stage);
			stageMap.put(stage.getName(), cxt);
		}
		/**/
		while(stages.size() > 0 ){
			Iterator<XmlProcessStage> it = stages.iterator();
			while(it.hasNext()){
				XmlProcessStage stage = it.next();
				if( stage.getPrev() == null || stage.getPrev().trim().equals("") ){
					graph.addEdge(stageMap.get(stage.getName()));
					Logger.getLogger("log").info("add root : " + stage.getName());
					it.remove();
				}else{
					StageContext cxt = stageMap.get(stage.getPrev());
					if( cxt == null ){
						/**/
						throw new Exception("cannot find stage named with : " + stage.getPrev());
					}
					boolean result = graph.append(cxt, stageMap.get(stage.getName()));
					if( result ){
						it.remove();
					}
				}
			}
	   }
		/*process context*/
		List<XmlProccessInitParam> initParam = processDes.getContext().getInitParames();
		Map<String,Object> cxtMap = new HashMap<String, Object>();
		if( initParam != null ){
			for(XmlProccessInitParam param : initParam){
				cxtMap.put(param.getName(), param.getValue());
			}
		}
		String callback = processDes.getContext().getCallback();
		if ( callback == null || callback.trim().equals("")){
		    callback = null;	
		}
		DefaultProcedureContext cxt = new DefaultProcedureContext(processDes.getName(), cxtMap, graph, callback != null ? (ICallBack)Class.forName(callback).newInstance():null);
		
		IDataProcedure procedure = (IDataProcedure)Class.forName(processDes.getEngine()).newInstance();
		procedure.init(cxt);
	    return procedure;
	}
	
	@SuppressWarnings("unchecked")
	private StageContext buildStageContext(XmlProcessStage stage) throws ClassNotFoundException{
		Class<? extends IEvent>[] eventClasses ;
		List<String> events = stage.getSupportEvents();
		eventClasses = (Class<? extends IEvent>[]) new Class<?>[events.size()];
		for(int i = 0; i < events.size(); i++){
			eventClasses[i] = (Class<? extends IEvent>)Class.forName(events.get(i));
		}
		Map<String, Object> paramMap = new HashMap<String,Object>();
		if( stage.getInitParames() != null ){
			for(XmlProcessStageInitParam param : stage.getInitParames()){
				paramMap.put(param.getName(), param.getValue());
			}
		}
			
		StageContext issueCxt = new StageContext(stage.getName(),
				(Class<? extends IStageEventHandler>)Class.forName(stage.getHandler()), eventClasses, paramMap);
		return issueCxt;
	}
}
