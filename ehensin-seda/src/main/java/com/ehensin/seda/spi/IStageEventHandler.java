package com.ehensin.seda.spi;

import com.ehensin.seda.exception.EventHandleRuntimeException;

public interface IStageEventHandler {

    public void handle(IEvent event) throws EventHandleRuntimeException;

    public void register(IStageEventHandleStatusListener listener);

    public void init(IStageContext cxt);
}
