package com.ehensin.seda;

import java.util.ArrayList;
import java.util.List;

import com.ehensin.seda.spi.IEvent;


public class EventHandlingStatus {
	private IEvent event ;
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
