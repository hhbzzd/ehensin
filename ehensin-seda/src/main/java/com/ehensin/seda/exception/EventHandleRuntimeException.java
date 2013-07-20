package com.ehensin.seda.exception;

import com.ehensin.seda.spi.IEvent;


public class EventHandleRuntimeException extends RuntimeException {
	private static final long serialVersionUID = -6060795496212134802L;
	private IEvent event;
	public EventHandleRuntimeException(String msg) {
        super(msg);
    }
	
	public EventHandleRuntimeException(Exception cause, IEvent event) {
		super(cause);
		this.event = event;
	}

	public EventHandleRuntimeException(Exception e, IEvent event , String msg) {
		super(msg,e);
		this.event = event;
	}
	
	public IEvent getEvent(){
		return event;
	}
}   