package com.knowlegevalues.assignment.service;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.knowlegevalues.assignment.model.Alternative;
import com.knowlegevalues.assignment.model.Mapping;
import com.knowlegevalues.assignment.model.Result;
import com.knowlegevalues.assignment.model.XmlFile;

/**
 * This class enables the user to start and end the consulting of xml files.
 * 
 * @author harikrishnankesavan
 *
 */
public class Player {
	
	
	/**
	 * This method consults the xml. 
	 * There are 4 different types of combinations possible
	 *  - provide root xml file name (consulting occurs from root and gives options to select)
	 *  - provide root xml file name and a target node name (consulting continues till target node is found)
	 *  - provide root xml file name and a selected node name (consulting occurs from root and gives options to select)
	 *  - provide root xml file name, a selected node name and a target node name (consulting continues till target node is found)
	 * @param xml
	 * @param selectedNode
	 * @param targetNode
	 * @return
	 */
	public Result consult(String xml, String selectedNode, String targetNode) {
		
		System.out.println("*** STARTING CONSULTATION WITH XML="+xml+", SELECTED NODE="+selectedNode+" AND TARGETNODE="+targetNode+"***");
		
		// validations
		if (null == selectedNode && null == targetNode && null == xml) {
			System.out.println("Please provide one of the below combinations:");
			System.out.println("1. xml file name");
			System.out.println("2. xml file name and target node name");
			System.out.println("3. xml file name selected node name");
			System.out.println("4. xml file name, selected node name and target node name");
		} else if (null == xml) {
			System.out.println("Please provide xml file name");
		}
		
		Result result = new Result();
		result.setTargetNode(targetNode);
		Mapping mapping = null;
		List<String> nodeNames = new ArrayList<>();
		
		if (null == selectedNode) {
			System.out.println("Consulting the xml file:"+xml);
			Node rootNode = getRoot(xml);
			mapping = getMapping(rootNode);
		} else if (selectedNode.equals(targetNode)){
			System.out.println("Found Target Node");
			result.setLeaf(true);
			result.setSelectedNode(selectedNode);
			System.out.println("*** END CONSULTING ***");
			return result;
		} else {
			
			Node currentNode = getNode(xml, selectedNode);
			mapping = getMapping(currentNode);
		}
		
		if (null != mapping) {
			if (null != mapping.getXmlFile()) {
				consult(mapping.getXmlFile().getFileName(), null, targetNode);
			}
			for (Alternative alternative:mapping.getAlternatives()) {
				nodeNames.add(alternative.getName());
				
				if (null != targetNode) {
					if (null != alternative.getMapsTo() && alternative.getMapsTo().equals(targetNode)) {
						consult(xml, alternative.getMapsTo(), targetNode);
					} else if (null != alternative.getName() && alternative.getName().equals(targetNode)) {
						System.out.println("Found Target Node");
						result.setLeaf(true);
						result.setMapping(mapping);
						System.out.println("*** END CONSULTING ***");
						return result;
					} 
				} else {
					if (null != alternative.getMapsTo()) {
						result.setSelectedNode(alternative.getMapsTo());
						result = consult(xml, alternative.getMapsTo(), null);
					} else {
						result.setSelectedNode(alternative.getName());
						result = consult(xml, alternative.getName(), null);
					}
					
					if (result.isLeaf()) {
						return result;
					}
				}
			}
			if(null != targetNode) {
				for (Alternative alternative:mapping.getAlternatives()) {
					result = consult(xml, alternative.getMapsTo(), targetNode);
					if(result.isLeaf()) {
						return result;
					}
				}
			}
			result.setChildNodeNames(nodeNames);
		}
		return result;
	}
	
	/**
	 * This method gets the xml document from the file name
	 * @param xmlFileName
	 * @return
	 */
	private Document getDocument(String xmlFileName) {
		try {
			DOMParser parser = new DOMParser();
			parser.parse(xmlFileName);
		
			return parser.getDocument();
			
		} catch (SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/** 
	 * This method gets the root node name from the xml file name
	 * @param xmlFileName
	 * @return
	 */
	private Node getRoot(String xmlFileName) {
		String root = null;
		Document document = getDocument(xmlFileName);
		Node node = document.getFirstChild();
		return node;
	}
	
	/**
	 * This method gets the node details for a given node name
	 * @param xmlFileName
	 * @param nodeName
	 * @return
	 */
	private Node getNode(String xmlFileName, String nodeName) {
		Document document = getDocument(xmlFileName);
		NodeList nodelist = document.getChildNodes();
		Node node = null;
		for (int i=0;i<nodelist.getLength();i++) {
			node = getNode(nodelist.item(i), nodeName);
		}
		return node;
	}
	
	/**
	 * This method is used to get the node based on given node name
	 * @param node
	 * @param nodeName
	 * @return
	 */
	private Node getNode(Node node, String nodeName) {

		if(null != node && node.hasAttributes()) {
			if(checkNode(node, nodeName)) {
				return node;
			} else {
				NodeList childNodes = node.getChildNodes();
				for (int i=0;i<childNodes.getLength();i++) {
					node = getNode(childNodes.item(i), nodeName);
					if(checkNode(node, nodeName)) {
						return node;
					}
				}
			}
		} else {
			NodeList childNodes = node.getChildNodes();
			for (int i=0;i<childNodes.getLength();i++) {
				node = getNode(childNodes.item(i), nodeName);
				if(checkNode(node, nodeName)) {
					return node;
				}
			}
		}

		return null;
	}
	
	/**
	 * This method has logic to check if the node is found by 
	 * checking the attribute with name as name of node.
	 * @param node
	 * @param nodeName
	 * @return
	 */
	private boolean checkNode(Node node, String nodeName) {
		Element e = (Element)node;
		if(null != e && e.hasAttribute("name") && e.getAttribute("name").equals(nodeName)) {
			return true;
		} else {
			return false;
		}
	}
	
	
	/**
	 * This method gets the Mapping object for a given node
	 * This object is used to further consult the xml
	 * @param node
	 * @return
	 */
	private Mapping getMapping(Node node) {
		Mapping mapping = new Mapping();
		List<Alternative> alternatives = new ArrayList<>();
		
		if (node.hasAttributes() && ((Element)node).hasAttribute("name")) {
			mapping.setNodeName(((Element)node).getAttribute("name"));
		}
		
		if (node.hasChildNodes()) {
			NodeList nodeList = node.getChildNodes();
			for (int i=0;i<nodeList.getLength();i++) {
				Node childNode = nodeList.item(i);
				if (null != childNode) {					
					if("mappinglist".equals(childNode.getNodeName()) && childNode.hasChildNodes()) {
						NodeList altList = childNode.getChildNodes();
						for (int j=0;j<altList.getLength();j++) {
							Node altNode = altList.item(j);
							if (null != altNode && altNode.hasAttributes()) {
								Element altE = (Element)altNode;
							
								Alternative alternative = new Alternative();
								if (altE.hasAttribute("name")) {
									alternative.setName(altE.getAttribute("name"));
								}
								if (altE.hasAttribute("mapsTo")) {
									alternative.setMapsTo(altE.getAttribute("mapsTo"));
								}
								alternatives.add(alternative);
							}
						}
						break;
					} else if ("alternative".equals(childNode.getNodeName())){
						Element childE = (Element)childNode;
						Alternative alternative = new Alternative();
						if (childE.hasAttribute("name")) {
							alternative.setName(childE.getAttribute("name"));
						}
						if (childE.hasAttribute("mapsTo")) {
							alternative.setMapsTo(childE.getAttribute("mapsTo"));
						}
						alternatives.add(alternative);
					} else if ("xmlFile".equals(childNode.getNodeName())){
						Element childE = (Element)childNode;
						XmlFile xmlFile = new XmlFile();
						if (childE.hasAttribute("name")) {
							xmlFile.setFileName(childE.getAttribute("name"));
						}
						if (childE.hasAttribute("nodeName")) {
							xmlFile.setNodeName(childE.getAttribute("nodeName"));
						}
						mapping.setXmlFile(xmlFile);
						break;
					}
				}
			}
		} 
		mapping.setAlternatives(alternatives);
		return mapping;
	}
	
	

}
