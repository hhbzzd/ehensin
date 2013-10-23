package com.ehensin.process;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlType
public class XmlProcess {
	@XmlAttribute(name = "name")
	private String name;
	@XmlAttribute(name = "engine")
	private String engine;
	@XmlElement(name = "context")
	XmlProcessContext context;
	@XmlElement(name = "stage")
	List<XmlProcessStage> stages;
	
	@XmlTransient
	public XmlProcessContext getContext() {
		return context;
	}
	public void setContext(XmlProcessContext context) {
		this.context = context;
	}
	
	@XmlTransient
	public List<XmlProcessStage> getStages() {
		return stages;
	}
	public void setStages(List<XmlProcessStage> stages) {
		this.stages = stages;
	}
	
	@XmlTransient
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@XmlTransient
	public String getEngine() {
		return engine;
	}
	public void setEngine(String engine) {
		this.engine = engine;
	}
	
    	
}
