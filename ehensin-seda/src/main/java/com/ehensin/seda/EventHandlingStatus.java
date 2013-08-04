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

import com.ehensin.seda.spi.IEvent;
/**
 * status class used by Event Handler,Sometimes the Envent Handler need to 
 * notify Business to know the event handling status. for example, the UI need to
 * show the progress of some event handling
 * */

public class EventHandlingStatus {
	/*which event the handler is handling*/
	private IEvent event ;
	/*status list, the event handler can fill the status when 
	 * something done*/
	private List<EventHandlingStatusItem> status;
	public EventHandlingStatus(IEvent event){
		this.event = event;
		status = new ArrayList<EventHandlingStatusItem>();
	}

    public IEvent getEvent(){
    	return event;
    }

	public List<EventHandlingStatusItem> getStatus() {
		return status;
	}

	public void setStatus(List<EventHandlingStatusItem> status) {
		this.status = status;
	}

	public void setEvent(IEvent event) {
		this.event = event;
	}
	public void addStatus(EventHandlingStatusItem s){
    	status.add(s);
    }
	
	public void updateStatus(String name, Object value){
		EventHandlingStatusItem item = getStatusItem(name);
		if( item != null )
		    item.setValue(value);
    }
	
	public EventHandlingStatusItem getStatusItem(String statusName){
    	for(EventHandlingStatusItem item : status){
    		if( item.getName().equals(statusName) )
    			return item;
    	}
    	return null;
    }
	
	
}
