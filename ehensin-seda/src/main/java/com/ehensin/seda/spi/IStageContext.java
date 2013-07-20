package com.ehensin.seda.spi;

import java.util.Map;


public interface IStageContext {

	public String getStageName();

	public Map<String,Object> getStageParameter();


	public Class<? extends IEvent>[] getSupportEvents();

	public Class<? extends IStageEventHandler> getEventHandlerClass();
}
