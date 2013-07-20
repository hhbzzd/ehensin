package com.ehensin.seda;

import java.util.HashMap;
import java.util.Map;

import com.ehensin.seda.spi.IEvent;
import com.ehensin.seda.spi.IEventDispatcher;
import com.ehensin.seda.spi.IStageContext;

public class SEDAEventDispatcher implements IEventDispatcher{
    private Map<Class<? extends IEvent>,InternalStageContext> eventMap;
    public SEDAEventDispatcher(){
    	eventMap = new HashMap<Class<? extends IEvent>,InternalStageContext>(); 
    }
	@Override
	public void dispatch(IEvent event) throws UnSupportEventException{
		InternalStageContext ctx = eventMap.get(event.getClass());
		if( ctx == null )
			throw new UnSupportEventException(event.getClass().getName());
		ctx.getStage().accept(event);
			
	}

	@Override
	public void register(Class<? extends IEvent> eventClass, IStageContext ctx) {
		eventMap.put(eventClass, (InternalStageContext)ctx);
	}

	@Override
	public void unregister(Class<? extends IEvent> eventClass) {
		eventMap.remove(eventClass);
	}

}
