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

import com.ehensin.seda.exception.EventHandleRuntimeException;
import com.ehensin.seda.spi.IEvent;
import com.ehensin.seda.spi.IStageEventHandleStatusListener;
import com.ehensin.seda.spi.IStageEventHandler;

class StageEventHandlerWrapper implements Runnable{
	private IStageEventHandler handler;
	private IEvent event;
	private IStageEventHandleStatusListener listener;
	StageEventHandlerWrapper(IStageEventHandler handler, IEvent event, IStageEventHandleStatusListener listener){
		this.handler = handler;
		this.event = event;
		this.listener = listener;
		this.handler.register(listener);
	}
	@Override
	public void run() {
		try{
			this.listener.start(event);
		    handler.handle(event);
		    this.listener.end(event);
		}catch(Exception e){
			throw new EventHandleRuntimeException(e, event);
		}
	}

}
