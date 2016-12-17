package com.knowlegevalues.assignment.model;

import java.util.List;

public class Mapping {
	
	private String nodeName;
	private List<Alternative> alternatives;
	private XmlFile xmlFile;
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public List<Alternative> getAlternatives() {
		return alternatives;
	}
	public void setAlternatives(List<Alternative> alternatives) {
		this.alternatives = alternatives;
	}
	public XmlFile getXmlFile() {
		return xmlFile;
	}
	public void setXmlFile(XmlFile xmlFile) {
		this.xmlFile = xmlFile;
	}
}
