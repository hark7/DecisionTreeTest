package com.knowlegevalues.assignment.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class XmlFile {

	private String name;
	private String nodeName;
	
	@XmlAttribute
	public String getFileName() {
		return name;
	}
	public void setFileName(String fileName) {
		this.name = fileName;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	
}
