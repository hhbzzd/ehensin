package com.ehensin.process;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlType
public class XmlProcessContext {
	@XmlElement(name = "init-param")
	List<XmlProccessInitParam> initParames;
	
	@XmlElement(name = "callback")
	String callback;

	@XmlTransient
	public List<XmlProccessInitParam> getInitParames() {
		return initParames;
	}

	public void setInitParames(List<XmlProccessInitParam> initParames) {
		this.initParames = initParames;
	}

	@XmlTransient
	public String getCallback() {
		return callback;
	}

	public void setCallback(String callback) {
		this.callback = callback;
	}
	
	
}
