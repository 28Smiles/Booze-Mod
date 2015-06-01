package de.booze.xml;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class XMLReader {
	
	private static final String DEPENDECIES = "dependencies";
	private static final String ID = "id";
	private static final String BUCKET = "bucket";
	private static final String CAN = "can";
	private static final String DENSITY = "density";
	private static final String VISCOSITY = "viscosity";
	
	private static final String FRUID = "Fruid";
	private static final String TYPE = "type";
	private static final String MASH = "mash";
	
	private static final String DAMPRESIDUE = "DampResidue";
	private static final String RESIDUE = "Residue";
	
	public static List<Mash> mash = new ArrayList<Mash>();
	
	public static void init() throws Exception {
		XMLInputFactory factory = XMLInputFactory.newInstance();
		
		InputStream in = Class.class.getResourceAsStream("/assets/booze/alcoholics/mash.xml");
		XMLEventReader eventReader = factory.createXMLEventReader(in);
		
		while (eventReader.hasNext()) {
	        XMLEvent event = eventReader.nextEvent();
	        Mash mash_element = new Mash();
	        
	        if (event.isStartElement()) {
	        	StartElement startElement = event.asStartElement();
	        	
	        	if(startElement.getName().toString().equals("Mash")) {
	        		Iterator<Attribute> attributes = startElement.getAttributes();
	        		while (attributes.hasNext()) {
	        			Attribute attribute = attributes.next();
	        			if(attribute.getName().toString().equals(DEPENDECIES))
	        				mash_element.setDependencies(attribute.getValue());
	        			if(attribute.getName().toString().equals(ID))
	        				mash_element.setId(attribute.getValue());
	        			if(attribute.getName().toString().equals(VISCOSITY))
	        				mash_element.setViscosity(Integer.parseInt(attribute.getValue()));
	        			if(attribute.getName().toString().equals(DENSITY))
	        				mash_element.setDensity(Integer.parseInt(attribute.getValue()));
	        		}
		        	while(eventReader.hasNext()){
		        		event = eventReader.nextEvent();
		        		if(event.toString().equals("</Mash>"))
		        			break;
		        		
			        	if (event.isStartElement())
			            if (event.asStartElement().getName().getLocalPart().equals(FRUID)) {
			            	attributes = event.asStartElement().getAttributes();
			        		while (attributes.hasNext()) {
			        			Attribute attribute = attributes.next();
			        			if(attribute.getName().toString().equals(TYPE)) {
			        				String temp = attribute.getValue();
			        				if(temp.equals("new"))
			        					mash_element.setFruid_type(ItemType.NEW);
			        				if(temp.equals("standard"))
			        					mash_element.setFruid_type(ItemType.STANDARD);
			        			}
			        			if(attribute.getName().toString().equals(MASH))
			        				mash_element.setMash(Integer.parseInt(attribute.getValue()));
			        			if(attribute.getName().toString().equals(ID))
			        				mash_element.setFruid_id(attribute.getValue());
			        		}
			            }
			        	
			        	if (event.isStartElement())
			            if (event.asStartElement().getName().getLocalPart().equals(DAMPRESIDUE)) {
			            	attributes = event.asStartElement().getAttributes();
			        		while (attributes.hasNext()) {
			        			Attribute attribute = attributes.next();
			        			if(attribute.getName().toString().equals(TYPE)) {
			        				String temp = attribute.getValue();
			        				if(temp.equals("new"))
			        					mash_element.setResidue_damp_type(ItemType.NEW);
			        				if(temp.equals("standard"))
			        					mash_element.setResidue_damp_type(ItemType.STANDARD);
			        			}
			        			if(attribute.getName().toString().equals(MASH))
			        				mash_element.setResidue_damp_mash(Integer.parseInt(attribute.getValue()));
			        			if(attribute.getName().toString().equals(ID))
			        				mash_element.setResidue_damp_id(attribute.getValue());
			        		}
			            }
			        	
			        	if (event.isStartElement())
			            if (event.asStartElement().getName().getLocalPart().equals(RESIDUE)) {
			            	attributes = event.asStartElement().getAttributes();
			        		while (attributes.hasNext()) {
			        			Attribute attribute = attributes.next();
			        			if(attribute.getName().toString().equals(TYPE)) {
			        				String temp = attribute.getValue();
			        				if(temp.equals("new"))
			        					mash_element.setResidue_type(ItemType.NEW);
			        				if(temp.equals("standard"))
			        					mash_element.setResidue_type(ItemType.STANDARD);
			        			}
		        				if(attribute.getName().toString().equals(ID))
			        				mash_element.setResidue_id(attribute.getValue());
			        		}
			            }
		        	}
		        	mash.add(mash_element);
	        	}
	        }
		}
	}
}
