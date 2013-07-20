package com.ehensin.seda;

import java.lang.Thread.UncaughtExceptionHandler;

public class StageThreadGroup extends ThreadGroup{

	UncaughtExceptionHandler handler;
	public StageThreadGroup(String name) {
		super(name);		
	}
    public void setDefaultUncaughtException(UncaughtExceptionHandler handler){
    	this.handler = handler;
    }
	public void uncaughtException(Thread t, Throwable e){
		handler.uncaughtException(t, e);
	}
}
