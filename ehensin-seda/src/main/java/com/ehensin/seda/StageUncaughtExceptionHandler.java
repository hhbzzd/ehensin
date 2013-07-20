package com.ehensin.seda;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ehensin.seda.spi.IStage;

public class StageUncaughtExceptionHandler  implements java.lang.Thread.UncaughtExceptionHandler{
	private final static Logger log = LoggerFactory.getLogger(StageUncaughtExceptionHandler.class);
	private IStage stage;
	public StageUncaughtExceptionHandler(IStage stage){
		this.stage = stage;
	}
	@Override
	public void uncaughtException(Thread t, Throwable e) {
		log.error("catched a exception from thread : " + t.getId() + "," + e.getClass().getName(), e);
		if( stage != null ){
		    stage.exception(e);
		}
	}

}
