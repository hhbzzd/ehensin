package com.ehensin.seda.test;

import java.util.concurrent.TimeUnit;

import com.ehensin.process.IDataProcedure;
import com.ehensin.process.IProcedureContext;
import com.ehensin.seda.SEDADriver;
import com.ehensin.seda.UnSupportEventException;
import com.ehensin.seda.spi.IEvent;

/**
 * 快开其处理有两种方式，一种是写一个定时器，轮训当前是否有期结的信息，如果有期结信�?
 * 就触发一个数据处理流程�?
 * 另一种方式期结发生后主动触发数据处理�?
 * 本实现根据上下文决定使用哪一种方�?
 * */
public class LotteryOrderProcedure implements IDataProcedure{
	public IProcedureContext context; 
	private SEDADriver driver;
	public LotteryOrderProcedure(){
	}
	@Override
	public void init(IProcedureContext cxt) {
			this.context = cxt;		
			driver = new SEDADriver(this.context.getStageGraph(), this.context.getCallBack());
	}
	

	@Override
	public void destroy() {
		driver.destroy();
		
	}

	@Override
	public void execute(IEvent event) throws Exception {
		if( event != null ){
			try {
				driver.signal(event);
			} catch (UnSupportEventException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}


}
