package org.lab2.classes;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;


import org.lab2.generated.*;
import org.xml.sax.InputSource;

/**
 * @author Deman
 *
 */
public class JAXBLab {
	
	
  private  Book book1 = new Book();
  private  Book book2 = new Book();
  private  Books booksList = new Books();
  //Instance of  Catalog class for marshalling xml
  private static  Catalog booksCatalog = new Catalog();
  
  //Instance of  Catalog class for unmarshalling xml from file
  public static Catalog booksCatalog2 = new Catalog();
  
//Instance of  Catalog class for unmarshalling xml from String
  private static Catalog booksCatalog3 = new Catalog();
  
  
  public static StringWriter sw = new StringWriter();
  public FileInputStream fis = null;
	
	 /**
	 * @throws JAXBException 
	 * @throws IOException 
	 * 
	 */
	public Catalog marshalXML(Catalog catalog) throws JAXBException, IOException{
		
		
		// set authors list for the books
		
		//authors for book1
		Authors authors1 = new Authors();
		authors1.getName().add("Jane Doe");
		//authors for book2
		Authors authors2 = new Authors();
		authors2.getName().add("John Brown");
		authors2.getName().add("Peter T.");
		
		
		catalog = booksCatalog;
		
		// create Marshaller and set it's properties 
		JAXBContext jc = JAXBContext.newInstance(Catalog.class); 
		Marshaller m = jc.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		m.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
		
		//create new books
		createBook(book1, 111, "Learning JAXB", 123445,"34 $", authors1, "Step by step instructions for beginners", "2003-01-01");
		createBook(book2, 222, "Java Webservices today and Beyond", 522965, "29 $", authors2,"Information for users so that they can start using Java Web Services Developer Pack", "2002-11-01");
		
		//add books to the books list
		booksList.getBook().add(book1);
		booksList.getBook().add(book2);
		
		//add bookslist to the books catalog
		booksCatalog.setBooks(booksList);
		
		//marshalling new xml file to the file and to the string
		m.marshal(JAXBLab.booksCatalog, new FileOutputStream("C:/temp/output2.xml"));
		System.out.println("Marshall XML");
		m.marshal(JAXBLab.booksCatalog, System.out);
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++");
		System.out.println("Marshall XML to the string");
		m.marshal(JAXBLab.booksCatalog, JAXBLab.sw);
		System.out.println(JAXBLab.sw);
		return booksCatalog;
	}
	
	
	/**
	 * @param catalog
	 * @return
	 * @throws JAXBException
	 */
	public Catalog unMarshallFile(Catalog catalog) throws JAXBException{
		catalog = booksCatalog2;
		JAXBContext jc = JAXBContext.newInstance(Catalog.class);
		Unmarshaller um = jc.createUnmarshaller();
		Marshaller m = jc.createMarshaller();
		try {
			fis = new FileInputStream("C:/temp/books.xml");
		} catch (FileNotFoundException e){
			e.printStackTrace();
		}
		booksCatalog2 = (Catalog) um.unmarshal(fis);
		System.out.println("Unmarshalling from file");
		//m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		m.marshal(JAXBLab.booksCatalog2, System.out);		
		return booksCatalog2;
	}
	
	public Catalog unMarshallString(Catalog catalog) throws JAXBException {
		catalog = booksCatalog3;
		JAXBContext jc = JAXBContext.newInstance(Catalog.class);
		Unmarshaller um = jc.createUnmarshaller();
		Marshaller m = jc.createMarshaller();
		StringReader strReader = new StringReader(sw.toString());
		InputSource is = new InputSource(strReader);
		System.out.println("Unmarshalling from String");
		booksCatalog3 = (Catalog) um.unmarshal(is);
		m.marshal(JAXBLab.booksCatalog3, System.out);
		return booksCatalog3;
	}
	
	/** Method for create new book
	 * @param myBook create new instance of Book class
	 * @param setID set ID for the book
	 * @param setName set the name of the book 
	 * @param setISBN set ISBN of the book
	 * @param setPrise set the price of the book
	 * @param authors set authors of the book
	 * @param setDescription set description of the book
	 * @param setPublishDate set publish date of the book 
	 */
	public void createBook(Book myBook, int setID, String setName, int setISBN,
				 String setPrise, Authors authors, String setDescription, String setPublishDate) {
			myBook.setId(setID);
			myBook.setName(setName);
			myBook.setISBN(setISBN);
			myBook.setPrice(setPrise);
			myBook.setAuthors(authors);
			myBook.setDescription(setDescription);
			myBook.setPublishDate(setPublishDate);
		}
  
  public static void main(String[] args) throws JAXBException, IOException{
		System.out.println("Start");
		JAXBLab lab = new JAXBLab();
		lab.marshalXML(booksCatalog);
		lab.unMarshallFile(booksCatalog2);
		lab.unMarshallString(booksCatalog3);
	}
		
	 
  
}
