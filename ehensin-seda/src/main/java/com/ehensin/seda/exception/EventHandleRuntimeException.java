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

package com.ehensin.seda.exception;

import com.ehensin.seda.spi.IEvent;


public class EventHandleRuntimeException extends RuntimeException {
	private static final long serialVersionUID = -6060795496212134802L;
	private IEvent event;
	public EventHandleRuntimeException(String msg) {
        super(msg);
    }
	
	public EventHandleRuntimeException(Exception cause, IEvent event) {
		super(cause);
		this.event = event;
	}

	public EventHandleRuntimeException(Exception e, IEvent event , String msg) {
		super(msg,e);
		this.event = event;
	}
	
	public IEvent getEvent(){
		return event;
	}
}   