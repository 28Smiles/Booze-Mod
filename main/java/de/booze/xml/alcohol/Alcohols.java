package de.booze.xml.alcohol;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Alcohols", namespace = "de.booze.xml.alcohol")
public class Alcohols {
	
	@XmlElement(name = "Alcohol")
	public ArrayList<Alcohol> alcohols;
}
