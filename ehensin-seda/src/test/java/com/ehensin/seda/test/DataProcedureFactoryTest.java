package com.ehensin.seda.test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;

import com.ehensin.process.DefaultDataProcedureFactory;
import com.ehensin.process.IDataProcedure;
import com.ehensin.process.IDataProcedureFactory;


public class DataProcedureFactoryTest {
	IDataProcedureFactory factory;
    @Before
    public void init() throws Exception{
    	factory = new DefaultDataProcedureFactory();
    	String xmlLocation = "/processes.xml";
    	Map<String, String> initParam = new HashMap<String,String>();
    	initParam.put("lps.ip", "10.1.1.10");
    	initParam.put("lps.port","9100");
    	factory.init(xmlLocation, initParam);
    }
    @Test
    public void testGetProcedure() throws Exception{
    	String processName = "orderprocess";
    	IDataProcedure procedure = factory.getProcedure(processName);
    	if( procedure != null ){
    		LotteryOrderAcceptEvent event = new LotteryOrderAcceptEvent();
    		event.setAmount(1000l);
    		event.setOrder("test order");
    		event.setUserNum("testuser");
    		procedure.execute(event);
    		
    		LotteryOrderPayEvent pay = new LotteryOrderPayEvent();
    		pay.setAmount(1000l);
    		pay.setOrderSerialNum("10001");
    		pay.setUserNum("testuser");
    		procedure.execute(pay);
    		
    		LotteryOrderLpsEvent lps = new LotteryOrderLpsEvent();
    		lps.setOrder("test order");
    		procedure.execute(lps);
    		try {
    			TimeUnit.SECONDS.sleep(1);
    		} catch (InterruptedException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    		procedure.destroy();
    	}
    }
    
    
}
