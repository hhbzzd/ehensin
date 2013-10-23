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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.RejectedExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ehensin.seda.exception.EventHandleRuntimeException;
import com.ehensin.seda.spi.IEvent;
import com.ehensin.seda.spi.IStage;
import com.ehensin.seda.spi.IStageContext;
import com.ehensin.seda.spi.IStageEventHandleStatusListener;
import com.ehensin.seda.spi.IStageEventHandler;
import com.ehensin.seda.spi.IStageListener;

public class Stage implements IStage, IStageEventHandleStatusListener{
	private IStageContext ctx;
	private IStageEventHandler handler;
    
	private StageEventQueue queue;
	
    private StageThreadPool threadPool;
   
    private Map<Integer,IEvent> handlingEvents;
   
    private Map<Integer, EventHandlingStatus> handlingEventsStatus;
    private List<IStageListener> listeners;
    private boolean isDestroyed = false;
    public Stage(final StageThreadPool threadPool, IStageContext ctx){
    	if( threadPool == null ){
    		StageUncaughtExceptionHandler handler = new StageUncaughtExceptionHandler(this);
    		StageThreadGroup group = new StageThreadGroup(ctx.getStageName());
    		group.setDefaultUncaughtException(handler);
    		this.threadPool = new StageThreadPool(-1, group);
    	}
    	this.ctx = ctx;
    	int queueSize = -1;
    	if( this.ctx.getStageParameter() != null )
    	    queueSize = this.ctx.getStageParameter().get("queuesize") == null ? -1 : Integer.valueOf((String)this.ctx.getStageParameter().get("queuesize"));
    	queue = new StageEventQueue(this, queueSize);
    	
        try {
			handler = ctx.getEventHandlerClass().newInstance();
			handler.init(ctx);
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
    	
		listeners = new ArrayList<IStageListener>(1);
		
		handlingEvents = new ConcurrentHashMap<Integer, IEvent>();
		handlingEventsStatus = new ConcurrentHashMap<Integer, EventHandlingStatus>();
    }
	@Override
	public void accept(IEvent event) throws UnSupportEventException{
		if( isDestroyed )
			return;
		try {
			if( supportEventCheck(event) )
			    queue.enqueue(event);
			else
				throw new UnSupportEventException("unsupported event : " + event.getClass().getName());
				
		} catch (InterruptedException e) {
			throw new UnSupportEventException(e, "unsupported event : " + event.getClass().getName());
		}
	}
	
	private boolean supportEventCheck(IEvent event){
		return true;
	}

	@Override
	public List<EventHandlingStatus> getHandlingEventStatus() {
		return  new ArrayList<EventHandlingStatus>(handlingEventsStatus.values());
	}

	@Override
	public List<IEvent> getHandlingEvents() {
		return new ArrayList<IEvent>(handlingEvents.values());
	}

	@Override
	public void register(IStageListener listener) {
		listeners.add(listener);
	}
	
	@Override
	public void exception(Throwable e){
		if( e instanceof EventHandleRuntimeException){
			EventHandleRuntimeException exception = (EventHandleRuntimeException)e;
			for( IStageListener stage : listeners ){
				stage.stageExceptionThrow(exception);
			}
		}
	}
	
	@Override
	public void destroy() {
		isDestroyed = true;
		if( this.queue != null )
			this.queue.destroy();
		if( this.listeners != null )
			this.listeners.clear();
		if( this.handlingEvents != null )
			this.handlingEvents.clear();
		if( this.handlingEventsStatus != null )
			this.handlingEventsStatus.clear();
		if( this.threadPool != null )
			this.threadPool.shutDown();
	}

	@Override
	public void run() {
		while( true && !isDestroyed){
			try {
				IEvent event = queue.dequeue();
				if( !threadPool.isShutDown() )
				    threadPool.execute(new StageEventHandlerWrapper(handler, event, this));
			} catch (InterruptedException e) {
				Logger.getGlobal().log(Level.SEVERE, e.getMessage(), e);
			} catch (RejectedExecutionException e){
				Logger.getGlobal().log(Level.SEVERE, e.getMessage(), e);
			}
			
		}
		
	}
  
	@Override
	public void end(IEvent event) {
		handlingEvents.remove(event.getId());
		handlingEventsStatus.remove(event.getId());
		for( IStageListener stage : listeners ){
			stage.stageEnd(event);
		}
	}

	@Override
	public void start(IEvent event) {
		handlingEvents.put(event.getId(), event);
		for( IStageListener stage : listeners ){
			stage.stageChanged(event);
		}
	}

	@Override
	public void statusChanged(IEvent event, EventHandlingStatus status) {
		handlingEventsStatus.put(event.getId(), status);
		for( IStageListener stage : listeners ){
			stage.stageStatusChanged(event, status);
		}
	}

}
