package de.booze.xml.alcohol;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

public class Booze {
	
	@XmlAttribute
	public String id;
	
	@XmlAttribute
	public String educt;
	
	@XmlAttribute(name = "maturity-time")
	public String maturityTime;
	
	@XmlAttribute
	public String alcohol;
	
	@XmlAttribute
	public String drinkable;
	
	@XmlElement(name = "Effect")
	public ArrayList<Effect> effect;
}
