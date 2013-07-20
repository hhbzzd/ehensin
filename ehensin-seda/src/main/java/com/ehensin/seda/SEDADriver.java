package com.ehensin.seda;

import java.util.List;

import com.ehensin.seda.spi.ICallBack;
import com.ehensin.seda.spi.IEvent;
import com.ehensin.seda.spi.IEventDispatcher;
import com.ehensin.seda.spi.IStageContext;
import com.ehensin.seda.spi.IStageListener;

public class SEDADriver implements IStageListener{
	private StageGraph graph;
	private ICallBack callback;
	private IEventDispatcher dispatcher;
    public SEDADriver(StageGraph graph, ICallBack callback){
    	this.graph = graph;
    	this.callback = callback;
    	dispatcher = new SEDAEventDispatcher();
    	for( StageEdge edge : graph.getRoots() ){
    		buildDispatcher(edge);
    	}

    }

    private void buildDispatcher(StageEdge edge){
    	IStageContext ctx = edge.getStageCtx(); 
		InternalStageContext ictx = new InternalStageContext(ctx);

		ictx.getStage().register(this);
		for(Class<? extends IEvent> ec : ctx.getSupportEvents())
		    dispatcher.register(ec, ictx);
		List<StageEdge> list = edge.getNexts();
		if( list != null ){
			for( StageEdge e : list ){
				buildDispatcher(e);
			}
		}
    }
    
    public StageGraph getStageGraph(){
    	return graph;
    }

    public void signal(IEvent event) throws UnSupportEventException{
    	dispatcher.dispatch(event);
    }

	@Override
	public void stageChanged(IEvent event) {
		if( callback != null && event instanceof StageEvent)
			callback.stageChanged((StageEvent)event);
	}

	@Override
	public void stageEnd(IEvent event) {
		if( !(event instanceof StageEvent) )
			return;
		if( callback != null)
			callback.stageEnd((StageEvent)event);

		StageEvent e = (StageEvent)event;
		StageEdge edge = graph.lookup(e.getStageName());
		if( edge == null )
			return;
		List<StageEdge> list = edge.getNexts();
		if( list != null ){
			for( StageEdge l : list ){
				Class<? extends IEvent>[] ets = l.getStageCtx().getSupportEvents();
				for(Class<? extends IEvent> et : ets ){
					try {
						IEvent tmp = et.newInstance();
						IEvent nextEvent = tmp.copy(event);
						this.signal(nextEvent);
					} catch (InstantiationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IllegalAccessException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (UnSupportEventException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		}
		
		
		
	}

	@Override
	public void stageExceptionThrow(Exception e) {
		if( callback != null )
			callback.stageExceptionThrow(e);
	}

	@Override
	public void stageStatusChanged(IEvent event, EventHandlingStatus status) {
		if( callback != null && event instanceof StageEvent)
			callback.stageStatusChanged((StageEvent)event, status);
	}
}
