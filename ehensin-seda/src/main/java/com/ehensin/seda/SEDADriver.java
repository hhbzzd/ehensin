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

import java.util.Iterator;
import java.util.List;

import com.ehensin.seda.spi.ICallBack;
import com.ehensin.seda.spi.IEvent;
import com.ehensin.seda.spi.IEventDispatcher;
import com.ehensin.seda.spi.IStageContext;
import com.ehensin.seda.spi.IStageListener;
/**
 * seda driver to drive seda system to work
 * 
 * */
public class SEDADriver implements IStageListener{
	private StageGraph graph;
	private ICallBack callback;
	private IEventDispatcher dispatcher;
    public SEDADriver(StageGraph graph, ICallBack callback){
    	this.graph = graph;
    	this.callback = callback;
    	dispatcher = new SEDAEventDispatcher();
    	for( StageEdge edge : graph.getRoots() ){
    		buildDispatcher(edge);
    	}

    }

    private void buildDispatcher(StageEdge edge){
    	IStageContext ctx = edge.getStageCtx(); 
		InternalStageContext ictx = new InternalStageContext(ctx);

		ictx.getStage().register(this);
		for(Class<? extends IEvent> ec : ctx.getSupportEvents())
		    dispatcher.register(ec, ictx);
		List<StageEdge> list = edge.getNexts();
		if( list != null ){
			for( StageEdge e : list ){
				buildDispatcher(e);
			}
		}
    }
    
    public StageGraph getStageGraph(){
    	return graph;
    }

    public void signal(IEvent event) throws UnSupportEventException{
    	dispatcher.dispatch(event);
    }
    
    /**销毁事件驱动实体*/
    public void destroy(){
    	Iterator<InternalStageContext> it = ((SEDAEventDispatcher)dispatcher).eventMap.values().iterator();
    	while(it.hasNext()){
    		InternalStageContext stx = it.next();
    		stx.getStage().destroy();
    	}
    }

	@Override
	public void stageChanged(IEvent event) {
		if( callback != null && event instanceof StageEvent)
			callback.stageChanged((StageEvent)event);
	}

	@Override
	public void stageEnd(IEvent event) {
		if( !(event instanceof StageEvent) )
			return;
		if( callback != null)
			callback.stageEnd((StageEvent)event);

		StageEvent e = (StageEvent)event;
		StageEdge edge = graph.lookup(e.getStageName());
		if( edge == null )
			return;
		List<StageEdge> list = edge.getNexts();
		if( list != null ){
			for( StageEdge l : list ){
				Class<? extends IEvent>[] ets = l.getStageCtx().getSupportEvents();
				for(Class<? extends IEvent> et : ets ){
					try {
						IEvent tmp = et.newInstance();
						IEvent nextEvent = tmp.copy(event);
						this.signal(nextEvent);
					} catch (InstantiationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IllegalAccessException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (UnSupportEventException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		}
		
		
		
	}

	@Override
	public void stageExceptionThrow(Exception e) {
		if( callback != null )
			callback.stageExceptionThrow(e);
	}

	@Override
	public void stageStatusChanged(IEvent event, EventHandlingStatus status) {
		if( callback != null && event instanceof StageEvent)
			callback.stageStatusChanged((StageEvent)event, status);
	}
}
