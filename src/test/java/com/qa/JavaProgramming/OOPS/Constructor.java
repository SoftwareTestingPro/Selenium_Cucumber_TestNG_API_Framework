package com.qa.JavaProgramming.OOPS;

public class Constructor {

	// Instance variables
	private int id;
	private String name;
	private String position;

	// Constructor to initialize the Employee object
	public Constructor(int id, String name, String position) {
		this.id = id;
		this.name = name;
		this.position = position;
	}

	// Method to display employee details
	public void displayEmployeeDetails() {
		System.out.println("Employee ID: " + id);
		System.out.println("Employee Name: " + name);
		System.out.println("Employee Position: " + position);
	}

	// Main method to test the Employee class
	public static void main(String[] args) {
		// Creating Employee objects using the constructor
		Constructor emp1 = new Constructor(1, "John Doe", "Developer");
		Constructor emp2 = new Constructor(2, "Jane Smith", "Designer");

		// Displaying the details of the employees
		emp1.displayEmployeeDetails();
		emp2.displayEmployeeDetails();
	}
}