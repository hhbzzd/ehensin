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
