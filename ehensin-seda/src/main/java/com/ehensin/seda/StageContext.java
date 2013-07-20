package com.ehensin.seda;

import java.util.Map;

import com.ehensin.seda.spi.IEvent;
import com.ehensin.seda.spi.IStageContext;
import com.ehensin.seda.spi.IStageEventHandler;

public class StageContext implements IStageContext{
	private String stageName;
	private Class<? extends IStageEventHandler> handlerClass;
	private Class<? extends IEvent>[] supportEvents;
	private Map<String, Object> parameter;
	public StageContext(String stageName,Class<? extends IStageEventHandler> handlerClass
			,Class<? extends IEvent>[] supportEvents, Map<String, Object> parameter){
		this.stageName = stageName;
		this.handlerClass = handlerClass;
		this.supportEvents = supportEvents;
		this.parameter = parameter;
	}
	

	@Override
	public Class<? extends IStageEventHandler> getEventHandlerClass() {
		return handlerClass;
	}

	@Override
	public String getStageName() {
		return stageName;
	}

	@Override
	public Map<String, Object> getStageParameter() {
		return parameter;
	}

	@Override
	public Class<? extends IEvent>[] getSupportEvents() {
		return supportEvents;
	}


	public Class<? extends IStageEventHandler> getHandlerClass() {
		return handlerClass;
	}


	public void setHandlerClass(Class<? extends IStageEventHandler> handlerClass) {
		this.handlerClass = handlerClass;
	}


	public Map<String, Object> getParameter() {
		return parameter;
	}


	public void setParameter(Map<String, Object> parameter) {
		this.parameter = parameter;
	}


	public void setStageName(String stageName) {
		this.stageName = stageName;
	}


	public void setSupportEvents(Class<? extends IEvent>[] supportEvents) {
		this.supportEvents = supportEvents;
	}

	
	
}
