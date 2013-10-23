package com.ehensin.process;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name = "processes")
public class XmlRootProccesses {
	@XmlElement(name = "process")
	List<XmlProcess> processes;

	@XmlTransient
	public List<XmlProcess> getProcesses() {
		return processes;
	}

	public void setProcesses(List<XmlProcess> processes) {
		this.processes = processes;
	}
	
	
}
