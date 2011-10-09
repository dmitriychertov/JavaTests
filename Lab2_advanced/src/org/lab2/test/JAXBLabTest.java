package org.lab2.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URL;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.custommonkey.xmlunit.XMLAssert;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.Test;
import org.lab2.classes.JAXBLab;
import org.lab2.generated.Catalog;
import org.xml.sax.SAXException;

public class JAXBLabTest {
	
	@Test
	public void compareXMLTest() throws SAXException, IOException{
		FileReader fr1 = null;
		FileReader fr2 = null;
		fr1 = new FileReader("C:/temp/books.xml");
		fr2 = new FileReader("C:/temp/books.xml");
		XMLUnit.setIgnoreWhitespace(true);
	    XMLUnit.setIgnoreAttributeOrder(true);
	    XMLUnit.setIgnoreComments(true);
	    XMLAssert.assertXMLEqual(fr2, fr1);
	}
	
	@Test
	public void unmarshalFromStringTest() throws JAXBException, SAXException, IOException{
		Catalog testcat = new Catalog();
		JAXBLab test = new JAXBLab();
		test.fis = new FileInputStream(new File("C:/temp/books.xml"));
		test.marshalXML(testcat);
		java.io.StringWriter sw2  = new StringWriter();
		Marshaller mar = (Marshaller) test.marshalXML(testcat);
		Unmarshaller unmar = (Unmarshaller) test.unMarshallFile(testcat); 
		test.booksCatalog2 = (Catalog) unmar.unmarshal(test.fis);
		
		String sw3;
		String sw4;
		
		mar.marshal(test.booksCatalog2, sw2);
		mar.marshal(test.booksCatalog2, test.sw);
		
		sw3=sw2.toString();
		sw4=test.sw.toString();
		XMLAssert.assertXMLEqual(sw3, sw4);
		
	}
	
}
