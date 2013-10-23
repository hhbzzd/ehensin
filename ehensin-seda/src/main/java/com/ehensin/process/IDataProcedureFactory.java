package com.ehensin.process;

import java.util.Map;
/**
 * 数据处理工厂，用于构造具体的数据处理引擎
 * 
 * */
public interface IDataProcedureFactory {
    /**
     * 初始�?
     * @param xmlLocation 数据处理描述信息存放�?
     * @param initParam  初始化参数，数据处理描述xml文本，里可能存在�?���?��替换的信息，此时
     *        initParam的参数将替换掉，xml里被标记�?{}的信�?
     * */
	public void init(String xmlLocation, Map<String, String> initParam)throws Exception;
    /**
     * 用于获取具体数据处理引擎
     * @param processName 处理流程名称
     * @return IDataProcedure 实例
     * */
	public IDataProcedure getProcedure(String processName);

}
