package com.ehensin.seda;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.ehensin.seda.exception.EventHandleRuntimeException;
import com.ehensin.seda.spi.IEvent;
import com.ehensin.seda.spi.IStage;
import com.ehensin.seda.spi.IStageContext;
import com.ehensin.seda.spi.IStageEventHandleStatusListener;
import com.ehensin.seda.spi.IStageEventHandler;
import com.ehensin.seda.spi.IStageListener;

public class Stage implements IStage, IStageEventHandleStatusListener{
	private IStageContext ctx;
	private IStageEventHandler handler;
    
	private StageEventQueue queue;
	
    private StageThreadPool threadPool;
   
    private Map<Integer,IEvent> handlingEvents;
   
    private Map<Integer, EventHandlingStatus> handlingEventsStatus;
    private List<IStageListener> listeners;
    public Stage(final StageThreadPool threadPool, IStageContext ctx){
    	if( threadPool == null ){
    		StageUncaughtExceptionHandler handler = new StageUncaughtExceptionHandler(this);
    		StageThreadGroup group = new StageThreadGroup(ctx.getStageName());
    		group.setDefaultUncaughtException(handler);
    		this.threadPool = new StageThreadPool(-1, group);
    	}
    	this.ctx = ctx;
    	queue = new StageEventQueue(this, -1);
    	
        try {
			handler = ctx.getEventHandlerClass().newInstance();
			handler.init(ctx);
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
    	
		listeners = new ArrayList<IStageListener>(1);
		
		handlingEvents = new ConcurrentHashMap<Integer, IEvent>();
		handlingEventsStatus = new ConcurrentHashMap<Integer, EventHandlingStatus>();
    }
	@Override
	public void accept(IEvent event) throws UnSupportEventException{
		try {
			if( supportEventCheck(event) )
			    queue.enqueue(event);
			else
				throw new UnSupportEventException("unsupported event : " + event.getClass().getName());
				
		} catch (InterruptedException e) {
			throw new UnSupportEventException(e, "unsupported event : " + event.getClass().getName());
		}
	}
	
	private boolean supportEventCheck(IEvent event){
		return true;
	}

	@Override
	public List<EventHandlingStatus> getHandlingEventStatus() {
		return  new ArrayList<EventHandlingStatus>(handlingEventsStatus.values());
	}

	@Override
	public List<IEvent> getHandlingEvents() {
		return new ArrayList<IEvent>(handlingEvents.values());
	}

	@Override
	public void register(IStageListener listener) {
		listeners.add(listener);
	}
	
	@Override
	public void exception(Throwable e){
		if( e instanceof EventHandleRuntimeException){
			EventHandleRuntimeException exception = (EventHandleRuntimeException)e;
			for( IStageListener stage : listeners ){
				stage.stageExceptionThrow(exception);
			}
		}
	}

	@Override
	public void run() {
		while( true ){
			try {
				IEvent event = queue.dequeue();
				threadPool.execute(new StageEventHandlerWrapper(handler, event, this));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
  
	@Override
	public void end(IEvent event) {
		handlingEvents.remove(event.getId());
		for( IStageListener stage : listeners ){
			stage.stageEnd(event);
		}
	}

	@Override
	public void start(IEvent event) {
		handlingEvents.put(event.getId(), event);
		for( IStageListener stage : listeners ){
			stage.stageChanged(event);
		}
	}

	@Override
	public void statusChanged(IEvent event, EventHandlingStatus status) {
		handlingEventsStatus.put(event.getId(), status);
		for( IStageListener stage : listeners ){
			stage.stageStatusChanged(event, status);
		}
	}

}
