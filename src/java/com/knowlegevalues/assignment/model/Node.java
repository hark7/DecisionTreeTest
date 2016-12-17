package com.knowlegevalues.assignment.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class Node {
	
	private String name;
	private List<Node> childrennodes;
	private List<Alternative> mappinglist;
	private XmlFile xmlFile;
	private boolean leaf;
	
	@XmlAttribute
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Node> getChildren() {
		return childrennodes;
	}
	public void setChildren(List<Node> childrennodes) {
		this.childrennodes = childrennodes;
	}
	public List<Alternative> getAlternatives() {
		return mappinglist;
	}
	public void setAlternatives(List<Alternative> mappinglist) {
		this.mappinglist = mappinglist;
	}
	public XmlFile getXmlFile() {
		return xmlFile;
	}
	public void setXmlFile(XmlFile xmlFile) {
		this.xmlFile = xmlFile;
	}
	public boolean isLeaf() {
		return leaf;
	}
	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}
	
}
