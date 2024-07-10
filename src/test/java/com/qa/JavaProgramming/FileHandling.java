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
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.xml.transform.OutputKeys;

public class FileHandling {

	public String XMLfilePath = System.getProperty("user.dir") + "/target/employeeXML.xml";
	public String ModifiedXMLFilePath = System.getProperty("user.dir") + "/target/employeeXML-updated.xml";
	public String XLSXFilePath = System.getProperty("user.dir") + "/target/employeeXLSX.xlsx";
	public String ModifiedXLSXFilePath = System.getProperty("user.dir") + "/target/employeeXLSX-updated.xlsx";

	@Test
	public void CreateXML() throws Exception {
		// Create a new Document
		DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
		Document doc = documentFactory.newDocumentBuilder().newDocument();

		// Root element
		Element root = doc.createElement("employees");
		doc.appendChild(root);

		// Employee 1
		Element employee1 = doc.createElement("employee");
		employee1.setAttribute("id", "1");

		Element name1 = doc.createElement("name");
		name1.appendChild(doc.createTextNode("John Doe"));
		employee1.appendChild(name1);

		Element position1 = doc.createElement("position");
		position1.appendChild(doc.createTextNode("Developer"));
		employee1.appendChild(position1);

		root.appendChild(employee1);

		// Employee 2
		Element employee2 = doc.createElement("employee");
		employee2.setAttribute("id", "2");

		Element name2 = doc.createElement("name");
		name2.appendChild(doc.createTextNode("Jane Smith"));
		employee2.appendChild(name2);

		Element position2 = doc.createElement("position");
		position2.appendChild(doc.createTextNode("Designer"));
		employee2.appendChild(position2);

		root.appendChild(employee2);

		// Create the XML file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
		DOMSource domSource = new DOMSource(doc);
		StreamResult streamResult = new StreamResult(new File(XMLfilePath));

		transformer.transform(domSource, streamResult);
		System.out.println("XML file created successfully at " + XMLfilePath);
	}

	@Test
	public void ReadXML() throws Exception {
		CreateXML();
		// Specify the file path as a File object
		File xmlFile = new File(XMLfilePath);
		System.out.println("Reading XML file at location : " + xmlFile);

		// Create a DocumentBuilder
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();

		// Parse the XML file
		Document document = builder.parse(xmlFile);

		// Access elements by tag name
		NodeList nodeList = document.getElementsByTagName("employee");
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			System.out.println("Element Content: " + node.getTextContent());
		}
	}

	@Test
	public void ModifyXML() throws Exception {
		CreateXML();
		// Load the XML file
		File inputFile = new File(XMLfilePath);
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
		StreamResult result = new StreamResult(new File(ModifiedXMLFilePath));
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.transform(source, result);
		System.out.println("XML file modified successfully. Access modified file at " + ModifiedXMLFilePath);
	}

	@Test
	public void CreateExcel() throws IOException {
		// Employee data to be written to the Excel file
		String[][] employeeData = { { "ID", "Name", "Position" }, { "1", "John Doe", "Developer" },
				{ "2", "Jane Smith", "Designer" }, { "3", "Mike Johnson", "Manager" } };

		// Create a new Workbook
		Workbook workbook = new XSSFWorkbook();

		// Create a new Sheet
		Sheet sheet = workbook.createSheet("Employees");

		// Iterate through the employee data and write it to the sheet
		int rowCount = 0;

		for (String[] employee : employeeData) {
			Row row = sheet.createRow(rowCount++);
			int columnCount = 0;

			for (String field : employee) {
				Cell cell = row.createCell(columnCount++);
				cell.setCellValue(field);
			}
		}

		// Write the output to a file
		FileOutputStream fos = new FileOutputStream(XLSXFilePath);
		workbook.write(fos);
		workbook.close();
		System.out.println("Excel file created successfully at " + XLSXFilePath);
	}

	@Test
	public void ReadExcel() throws IOException {
		CreateExcel();
		// Create a FileInputStream to read the Excel file
		FileInputStream fis = new FileInputStream(new File(XLSXFilePath));

		// Create a Workbook object to represent the Excel file
		Workbook workbook = new XSSFWorkbook(fis);

		// Get the first sheet from the workbook
		Sheet sheet = workbook.getSheetAt(0);

		// Iterate through each row in the sheet
		for (Row row : sheet) {
			// Iterate through each cell in the row
			for (Cell cell : row) {
				// Print the cell value
				switch (cell.getCellType()) {
				case STRING:
					System.out.print(cell.getStringCellValue() + "\t");
					break;
				case NUMERIC:
					System.out.print(cell.getNumericCellValue() + "\t");
					break;
				case BOOLEAN:
					System.out.print(cell.getBooleanCellValue() + "\t");
					break;
				default:
					System.out.print("UNKNOWN\t");
				}
			}
			System.out.println();
		}

		// Close the workbook and the FileInputStream
		workbook.close();
		fis.close();
	}

	@Test
	public void ModifyExcel() throws IOException {
		CreateExcel();
		// Open the Excel file
		FileInputStream fis = new FileInputStream(XLSXFilePath);
		Workbook workbook = new XSSFWorkbook(fis);

		// Get the first sheet
		Sheet sheet = workbook.getSheetAt(0);

		// Modify the value in cell B2 (row 1, column 1)
		Row row = sheet.getRow(1);
		if (row == null) {
			row = sheet.createRow(1);
		}
		Cell cell = row.getCell(1);
		if (cell == null) {
			cell = row.createCell(1);
		}
		cell.setCellValue("Johnathan Doe");

		// Modify the value in cell C3 (row 2, column 2)
		row = sheet.getRow(2);
		if (row == null) {
			row = sheet.createRow(2);
		}
		cell = row.getCell(2);
		if (cell == null) {
			cell = row.createCell(2);
		}
		cell.setCellValue("Lead Designer");

		// Close the input stream
		fis.close();

		// Write the changes back to the file
		FileOutputStream fos = new FileOutputStream(ModifiedXLSXFilePath);
		workbook.write(fos);
		workbook.close();
		fos.close();
		System.out.println("Excel file modified successfully. Create new file at "+ModifiedXLSXFilePath);
	}
}