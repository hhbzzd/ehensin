package com.ehensin.seda.spi;

import com.ehensin.seda.EventHandlingStatus;


public interface IStageEventHandleStatusListener {

    public void start(IEvent event);
    

    public void end(IEvent event);
    

    public void statusChanged(IEvent event, EventHandlingStatus status);
}
