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

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import com.ehensin.seda.spi.IEvent;
import com.ehensin.seda.spi.IStage;

/**
 * <pre>
 * CLASS:
 *   stage queue is for caching all arrived event.
 *   MessageConsumer thread consume these message and notify all handler 
 * 
 * RESPONSIBILITIES:
 * High level list of things that the class does
 * -)cache messages
 * -)notify MessageConsumer to handle message when message arrived. 
 * 
 * COLABORATORS:
 * List of descriptions of relationships with other classes, i.e. uses, contains, creates, calls...
 * -) class   relationship
 * -) class   relationship
 * 
 * USAGE:
 * Description of typical usage of class.  Include code samples.
 * 
 * 
 **/
@SuppressWarnings("unchecked")
public class StageEventQueue implements Comparable {
	private long queueSize;
	private BlockingQueue<IEvent> eventQueue;
	public StageEventQueue(IStage stage, long queueSize) {
		this.queueSize = queueSize;
		if( queueSize <= 0 ){
			queueSize = Integer.MAX_VALUE;
		}
		eventQueue = new LinkedBlockingQueue<IEvent>();
		
		Thread t = new Thread(stage);
		t.start();
	}

	public void enqueue(IEvent event) throws InterruptedException {
		if( isFull() ){
			TimeUnit.SECONDS.sleep(1);
		}
		eventQueue.put(event);
	}

	public IEvent dequeue() throws InterruptedException {
		return eventQueue.take();
	}

	public Integer getSize() {
		return eventQueue.size();
	}

	public boolean isFull() {
		return queueSize == eventQueue.size();
	}
	
	public void destroy(){
		eventQueue.clear();
	}

	@Override
	public int compareTo(Object arg0) {
		StageEventQueue q = (StageEventQueue) arg0;
		return getSize().compareTo(q.getSize());
	}
}
