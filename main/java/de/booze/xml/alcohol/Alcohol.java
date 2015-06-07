package de.booze.xml.alcohol;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Alcohol")
public class Alcohol {
	
	@XmlAttribute(name = "id")
	public String id;
	
	@XmlElement(name = "Booze")
	public ArrayList<Booze> booze;
}
