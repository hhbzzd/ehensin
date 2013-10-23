package com.ehensin.process;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlType
public class XmlProcessStage {
	@XmlAttribute(name = "name")
	private String name;
	@XmlAttribute(name = "prev")
	private String prev;
	@XmlElement(name = "support-event")
	List<String> supportEvents;	
	@XmlElement(name = "handler")
	String handler;
	
	@XmlElement(name = "init-param")
	List<XmlProcessStageInitParam> initParames;

	@XmlTransient
	public List<String> getSupportEvents() {
		return supportEvents;
	}

	public void setSupportEvents(List<String> supportEvents) {
		this.supportEvents = supportEvents;
	}

	@XmlTransient
	public String getHandler() {
		return handler;
	}

	public void setHandler(String handler) {
		this.handler = handler;
	}

	@XmlTransient
	public List<XmlProcessStageInitParam> getInitParames() {
		return initParames;
	}

	public void setInitParames(List<XmlProcessStageInitParam> initParames) {
		this.initParames = initParames;
	}

	@XmlTransient
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlTransient
	public String getPrev() {
		return prev;
	}

	public void setPrev(String prev) {
		this.prev = prev;
	}
	
	
	
}
