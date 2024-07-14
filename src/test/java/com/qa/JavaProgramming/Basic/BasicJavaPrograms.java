package com.qa.JavaProgramming.Basic;

import java.util.Random;

import org.testng.annotations.Test;

public class BasicJavaPrograms {

	@Test
	public void SwapValuesOf2VariablesWithoutThirdVariable() {
		int a = 10;
		int b = 20;
		System.out.println("Initial value of a is:" + a);
		System.out.println("Initial value of b is:" + b);
		a = a + b;
		b = a - b;
		a = a - b;
		System.out.println("Swapped value of a is:" + a);
		System.out.println("Swapped value of b is:" + b);
	}

	@Test
	public void FibonacciSeries() {
		int n = 10; // Number of terms in the Fibonacci series
		int firstTerm = 0;
		int secondTerm = 1;

		System.out.println("Fibonacci Series up to " + n + " terms:");

		for (int i = 1; i <= n; ++i) {
			System.out.print(firstTerm + " ");

			// Compute the next term
			int nextTerm = firstTerm + secondTerm;
			firstTerm = secondTerm;
			secondTerm = nextTerm;
		}
	}
	
	@Test
	public void GenerateRandomNumbers() {
		// Define the range
        int min = 10; // Minimum value (inclusive)
        int max = 50; // Maximum value (inclusive)

        // Create an instance of the Random class
        Random random = new Random();

        // Generate a random integer within the specified range
        int randomNumber = random.nextInt((max - min) + 1) + min;

        // Print the random number
        System.out.println("Random Integer between " + min + " and " + max + ": " + randomNumber);
	}
}