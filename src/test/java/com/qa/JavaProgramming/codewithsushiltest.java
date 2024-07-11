package com.qa.JavaProgramming;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.io.File;
import java.nio.file.Files;
import java.net.InetSocketAddress;

public class codewithsushiltest {
	
	public HttpServer server;
	
	@Test
    public void serverTest() throws IOException {
        // Start the HTTP server
        startHttpServer();

        // Launch the URL in a browser using Selenium
        launchBrowser("http://127.0.0.1:8000");
    }

    // Method to start the HTTP server and launch the browser
    public void startHttpServer() throws IOException {
        // Create an HTTP server listening on port 8000
        server = HttpServer.create(new InetSocketAddress("127.0.0.1", 8000), 0);

        // Create a context for the root URL (/)
        server.createContext("/", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                String filePath = "web" + exchange.getRequestURI().getPath();
                File file = new File(filePath);

                if (file.exists() && !file.isDirectory()) {
                    // Serve the requested file
                    byte[] response = Files.readAllBytes(file.toPath());
                    exchange.sendResponseHeaders(200, response.length);
                    try (OutputStream os = exchange.getResponseBody()) {
                        os.write(response);
                    }
                } else {
                    // Serve a 404 response if the file is not found
                    String response = "404 (Not Found)\n";
                    exchange.sendResponseHeaders(404, response.length());
                    try (OutputStream os = exchange.getResponseBody()) {
                        os.write(response.getBytes());
                    }
                }
            }
        });

        // Set an executor to handle requests, or null for a default executor
        server.setExecutor(null);

        // Start the server
        server.start();
        System.out.println("Server started on http://127.0.0.1:8000");
    }

    // Method to launch the URL in a browser using Selenium
    public void launchBrowser(String url) {
        try {
        	System.out.println("URL is: "+url);
        	url=url+"/index.html";
        	System.out.println("New URL is: "+url);
            // Create an instance of the WebDriver (e.g., ChromeDriver)
            WebDriver driver = new ChromeDriver();

            // Open the URL
            driver.get(url);

            // Optionally, keep the browser open for a while to view the page
            Thread.sleep(10000); // Wait for 10 seconds

            // Close the browser
            driver.quit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @AfterTest
    public void stopserver() {
    	server.stop(0);
    	System.out.println("Server stopped");
    }
}
