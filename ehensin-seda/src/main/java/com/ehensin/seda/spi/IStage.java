package com.ehensin.seda.spi;

import java.util.List;

import com.ehensin.seda.EventHandlingStatus;
import com.ehensin.seda.UnSupportEventException;


public interface IStage extends Runnable{

    public void register(IStageListener listener);

    public List<IEvent> getHandlingEvents();

    public List<EventHandlingStatus> getHandlingEventStatus();

    public void accept(IEvent event)throws UnSupportEventException;

    public void exception(Throwable e);
}
