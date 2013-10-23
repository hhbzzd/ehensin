package com.ehensin.process;

import java.util.Map;

import com.ehensin.seda.StageGraph;
import com.ehensin.seda.spi.ICallBack;

/**
 * 流程处理上下�?
 * */
public interface IProcedureContext {
	/**
	 * 获取流程名字
	 * */
	public String getProcessName();
	/**获取流程其他上下文信息，以map形式*/
	public Map<String, Object> getCxtMap();
	/**
	 * 获取流程图形结构
	 * */
	public StageGraph getStageGraph();
	/**
	 * 获取回调对象
	 * */
	public ICallBack getCallBack();

}
