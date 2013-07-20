package com.ehensin.seda;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class StageThreadPool {
	private ExecutorService threadPool;

	public StageThreadPool(int poolSize,ThreadGroup group) {
		if( poolSize > 0 ){
			threadPool = new ThreadPoolExecutor(poolSize, poolSize, 3, TimeUnit.SECONDS,
					new LinkedBlockingQueue<Runnable>(), new StageThreadFactory(group),
					new ThreadPoolExecutor.CallerRunsPolicy());
		}else{
			threadPool = Executors.newCachedThreadPool(new StageThreadFactory(group));
		}
	}

	public void execute(Runnable task) {
		threadPool.execute(task);
    }

	public void shutDown() {		
		threadPool.shutdown();
	}
}
