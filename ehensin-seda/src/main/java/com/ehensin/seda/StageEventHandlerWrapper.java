package com.ehensin.seda;

import com.ehensin.seda.exception.EventHandleRuntimeException;
import com.ehensin.seda.spi.IEvent;
import com.ehensin.seda.spi.IStageEventHandleStatusListener;
import com.ehensin.seda.spi.IStageEventHandler;

class StageEventHandlerWrapper implements Runnable{
	private IStageEventHandler handler;
	private IEvent event;
	private IStageEventHandleStatusListener listener;
	StageEventHandlerWrapper(IStageEventHandler handler, IEvent event, IStageEventHandleStatusListener listener){
		this.handler = handler;
		this.event = event;
		this.listener = listener;
		this.handler.register(listener);
	}
	@Override
	public void run() {
		try{
			this.listener.start(event);
		    handler.handle(event);
		    this.listener.end(event);
		}catch(Exception e){
			throw new EventHandleRuntimeException(e, event);
		}
	}

}
