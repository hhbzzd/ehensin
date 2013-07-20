package com.ehensin.seda.spi;

import com.ehensin.seda.EventHandlingStatus;
import com.ehensin.seda.StageEvent;


public interface ICallBack {

    public void stageChanged(StageEvent event);

    public void stageEnd(StageEvent event);

    public void stageStatusChanged(StageEvent event, EventHandlingStatus status);

    public void stageExceptionThrow(Exception e);
}
