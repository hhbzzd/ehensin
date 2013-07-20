package com.ehensin.seda;

import java.util.concurrent.atomic.AtomicInteger;

import com.ehensin.seda.spi.IEvent;

public abstract class StageEvent implements IEvent{
	static AtomicInteger ID_SEQ = new AtomicInteger(0);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
    public StageEvent(){
    	int value = ID_SEQ.incrementAndGet();
    	if( value == Integer.MAX_VALUE )
    		ID_SEQ.set(0);    	    		
    	id = value;
    }
    public int getId(){
    	return id;
    }

    public abstract String getStageName();

    public abstract Object getResult();
}
