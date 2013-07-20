/* @(#)MessageQueue.java
 *
 * (c) COPYRIGHT 1998-2010 Newcosoft INC. All rights reserved.
 * Newcosoft CONFIDENTIAL PROPRIETARY
 * Newcosoft Advanced Technology and Software Operations
 *
 * REVISION HISTORY:
 * Author             Date                   Brief Description
 * -----------------  ----------     ---------------------------------------
 * hhbzzd            ����09:33:37                init version
 * 
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
