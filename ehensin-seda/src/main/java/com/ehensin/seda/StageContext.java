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
