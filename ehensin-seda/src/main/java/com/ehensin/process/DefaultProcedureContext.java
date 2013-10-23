package com.ehensin.process;

import java.util.Map;

import com.ehensin.seda.StageGraph;
import com.ehensin.seda.spi.ICallBack;
/**
 * 缺省实现
 * */
public class DefaultProcedureContext implements IProcedureContext {
    private String processName;
    private Map<String, Object> cxtMap;
    private ICallBack callback;
    private StageGraph stageGraph;
	public DefaultProcedureContext(String name, 
			Map<String, Object> cxtMap,StageGraph stageGraph, ICallBack callback){
		this.processName = name;
		this.cxtMap = cxtMap;
		this.stageGraph = stageGraph;
		this.callback = callback;
	}
	

	@Override
	public String getProcessName() {
		return processName;
	}
	@Override
	public ICallBack getCallBack() {
		return callback;
	}
	@Override
	public Map<String, Object> getCxtMap() {
		return cxtMap;
	}
	@Override
	public StageGraph getStageGraph() {
		return stageGraph;
	}
	

}
