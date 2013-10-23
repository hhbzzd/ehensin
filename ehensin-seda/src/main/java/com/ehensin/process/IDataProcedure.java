package com.ehensin.process;

import com.ehensin.seda.spi.IEvent;

public interface IDataProcedure {
	/**
	 *
	 * */
	public void init(IProcedureContext cxt);
	/**
	 * 
	 
	 * */
    public void execute(IEvent event)throws Exception;
   
    /**
     * 
     * */
    public void destroy();

}
