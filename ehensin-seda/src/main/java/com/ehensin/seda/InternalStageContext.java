package com.ehensin.seda;

import java.util.Map;

import com.ehensin.seda.spi.IEvent;
import com.ehensin.seda.spi.IStage;
import com.ehensin.seda.spi.IStageContext;
import com.ehensin.seda.spi.IStageEventHandler;

class InternalStageContext implements IStageContext{
	private IStageContext ctx;
	private IStage stage;
	public InternalStageContext(IStageContext ctx){
		this.ctx = ctx;
		this.stage = new Stage(null, this);
	}
	@Override
	public Class<? extends IStageEventHandler> getEventHandlerClass() {
		return ctx.getEventHandlerClass();
	}

	public IStage getStage() {
		return this.stage;
	}

	@Override
	public String getStageName() {
		return ctx.getStageName();
	}

	@Override
	public Map<String, Object> getStageParameter() {
		return ctx.getStageParameter();
	}

	@Override
	public Class<? extends IEvent>[] getSupportEvents() {
		return ctx.getSupportEvents();
	}

}
