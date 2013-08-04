/*
 * Copyright 2013 The Ehensin SEDA Project
 *
 * The Ehensin SEDA Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

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
