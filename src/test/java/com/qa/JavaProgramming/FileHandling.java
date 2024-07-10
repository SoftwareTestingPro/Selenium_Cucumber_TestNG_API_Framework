package com.qa.JavaProgramming;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

import javax.xml.transform.OutputKeys;

import java.io.*;

public class FileHandling {

	@Test
	public void CreateXML() throws Exception {
		// Create a DocumentBuilder
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();

		// Create a new Document
		Document document = builder.newDocument();

		// Create root element
		Element root = document.createElement("library");
		document.appendChild(root);

		// Create book elements and add text content
		Element book1 = document.createElement("Program1");
		book1.appendChild(document.createTextNode("Java Programming"));
		Element book2 = document.createElement("Program2");
		book2.appendChild(document.createTextNode("Python Programming"));
		Element book3 = document.createElement("Program3");
		book3.appendChild(document.createTextNode("JavaScript"));
		Element book4 = document.createElement("Program4");
		book4.appendChild(document.createTextNode("C Programming"));
		root.appendChild(book1);
		root.appendChild(book2);
		root.appendChild(book3);
		root.appendChild(book4);

		// Write to XML file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(document);

		// Specify your local file path
		StreamResult result = new StreamResult(System.getProperty("user.dir") + "/target/output.xml");
		transformer.transform(source, result);
		System.out.println("XML file created successfully at "+System.getProperty("user.dir") + "/target/output.xml");
	}

	@Test
	public void ReadXML() throws Exception {
		CreateXML();
		// Specify the file path as a File object
		File xmlFile = new File(System.getProperty("user.dir") + "/target/output.xml");
		System.out.println("Reading XML file at location : "+xmlFile);

		// Create a DocumentBuilder
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();

		// Parse the XML file
		Document document = builder.parse(xmlFile);

		// Access elements by tag name
		NodeList nodeList = document.getElementsByTagName("library");
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			System.out.println("Element Content: " + node.getTextContent());
		}
	}

	@Test
	public void ModifyXML() throws Exception {
		CreateXML();
		// Load the XML file
		File inputFile = new File(System.getProperty("user.dir") + "/target/output.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(inputFile);
		doc.getDocumentElement().normalize();

		// Get the employee with id="2"
		NodeList nList = doc.getElementsByTagName("employee");
		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node nNode = nList.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				if (eElement.getAttribute("id").equals("2")) {
					// Modify the name element
					Node nameNode = eElement.getElementsByTagName("name").item(0);
					nameNode.setTextContent("Jane Doe");
				}
			}
		}

		// Write the modified XML back to the file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File(System.getProperty("user.dir") + "/target/ModifiedOutput.xml"));
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.transform(source, result);
		System.out.println("XML file updated successfully");
	}
}