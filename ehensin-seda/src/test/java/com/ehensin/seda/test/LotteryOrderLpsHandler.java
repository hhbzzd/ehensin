/* @()LotteryOrderAcceptHandler.java
 *
 * (c) COPYRIGHT 2009-2013 Newcosoft INC. All rights reserved.
 * Newcosoft CONFIDENTIAL PROPRIETARY
 * Newcosoft Advanced Technology and Software Operations
 *
 * REVISION HISTORY:
 * Author             Date                   Brief Description
 * -----------------  ----------     ---------------------------------------
 * User            上午11:47:54                init version
 * 
 */
package com.ehensin.seda.test;

import com.ehensin.seda.exception.EventHandleRuntimeException;
import com.ehensin.seda.spi.IEvent;
import com.ehensin.seda.spi.IStageContext;
import com.ehensin.seda.spi.IStageEventHandleStatusListener;
import com.ehensin.seda.spi.IStageEventHandler;


/** 
 * <pre>
 * CLASS:
 * Describe class, extends and implements relationships to other classes.
 * 
 * RESPONSIBILITIES:
 * High level list of things that the class does
 * -) 
 * 
 * COLABORATORS:
 * List of descriptions of relationships with other classes, i.e. uses, contains, creates, calls...
 * -) class   relationship
 * -) class   relationship
 * 
 * USAGE:
 * Description of typical usage of class.  Include code samples.
 * 
 * 
 **/
public class LotteryOrderLpsHandler implements IStageEventHandler{
	IStageContext cxt;
	@Override
	public void handle(IEvent event) throws EventHandleRuntimeException {
		System.out.println(event.getClass().getName());
		System.out.println(cxt.getStageParameter());
	}

	@Override
	public void init(IStageContext cxt) {
		this.cxt = cxt;
	}

	@Override
	public void register(IStageEventHandleStatusListener listener) {
		
	}

}
