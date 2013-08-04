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
