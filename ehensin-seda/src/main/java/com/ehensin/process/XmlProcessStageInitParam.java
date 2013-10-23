package com.ehensin.process;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlType
public class XmlProcessStageInitParam {
	@XmlElement(name = "name")
	String name;
	@XmlElement(name = "value")
	String value;
	
	@XmlTransient
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@XmlTransient
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
