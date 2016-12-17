package com.knowlegevalues.assignment.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Alternative {
	
	private String name;
	private String mapsTo;
	
	@XmlAttribute
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMapsTo() {
		return mapsTo;
	}
	public void setMapsTo(String mapsTo) {
		this.mapsTo = mapsTo;
	}

}
