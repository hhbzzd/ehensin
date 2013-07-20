package com.ehensin.seda.spi;

import java.io.Serializable;


public interface IEvent extends Serializable{

	public int getId();

	public IEvent copy(IEvent event);
}
