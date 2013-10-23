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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class StageThreadPool {
	private ExecutorService threadPool;

	public StageThreadPool(int poolSize,ThreadGroup group) {
		if( poolSize > 0 ){
			threadPool = new ThreadPoolExecutor(poolSize, poolSize, 3, TimeUnit.SECONDS,
					new LinkedBlockingQueue<Runnable>(), new StageThreadFactory(group),
					new ThreadPoolExecutor.CallerRunsPolicy());
		}else{
			threadPool = Executors.newCachedThreadPool(new StageThreadFactory(group));
		}
	}

	public void execute(Runnable task) {
		threadPool.execute(task);
    }

	public void shutDown() {		
		threadPool.shutdown();
	}
	
	public boolean isShutDown(){
		return threadPool.isShutdown() || threadPool.isTerminated();
	}
}
