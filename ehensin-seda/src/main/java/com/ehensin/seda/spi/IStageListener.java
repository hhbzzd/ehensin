package com.ehensin.seda.spi;

import com.ehensin.seda.EventHandlingStatus;


public interface IStageListener {

    public void stageChanged(IEvent event);
 
    public void stageEnd(IEvent event);

    public void stageStatusChanged(IEvent event, EventHandlingStatus status);

    public void stageExceptionThrow(Exception e);
}
