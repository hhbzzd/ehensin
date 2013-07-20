package com.ehensin.seda.spi;

import com.ehensin.seda.UnSupportEventException;


public interface IEventDispatcher {
    public void register(Class<? extends IEvent> eventClass, IStageContext ctx);

    public void unregister(Class<? extends IEvent> eventClass);

    public void dispatch(IEvent event)throws UnSupportEventException;
}
