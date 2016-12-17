package com.knowlegevalues.assignment.model;

import java.util.List;

public class Result {
	
	private String selectedNode;
	private String targetNode;
	private List<String> childNodeNames;
	private Mapping mapping;
	private boolean isLeaf;
	public String getSelectedNode() {
		return selectedNode;
	}
	public void setSelectedNode(String selectedNode) {
		this.selectedNode = selectedNode;
	}
	public String getTargetNode() {
		return targetNode;
	}
	public void setTargetNode(String targetNode) {
		this.targetNode = targetNode;
	}
	public List<String> getChildNodeNames() {
		return childNodeNames;
	}
	public void setChildNodeNames(List<String> childNodeNames) {
		this.childNodeNames = childNodeNames;
	}
	public Mapping getMapping() {
		return mapping;
	}
	public void setMapping(Mapping mapping) {
		this.mapping = mapping;
	}
	public boolean isLeaf() {
		return isLeaf;
	}
	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}
	

}
