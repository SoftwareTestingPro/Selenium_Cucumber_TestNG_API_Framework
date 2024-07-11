package com.qa.JavaProgramming;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.io.File;
import java.nio.file.Files;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class SimpleHttpServer {

	@Test
	public void StartServer() throws IOException, InterruptedException {
		// Create an HTTP server listening on port 8000
		HttpServer server = HttpServer.create(new InetSocketAddress("127.0.0.1", 8000), 0);

		// Create a context for the root URL (/)
		server.createContext("/", new RootHandler());

		// Set an executor to handle requests, or null for a default executor
		server.setExecutor(null);

		// Start the server
		server.start();
		System.out.println("Server started on http://127.0.0.1:8000");
		WebDriver driver = new ChromeDriver();
		driver.get("http://127.0.0.1:8000/web/index.html");
		Thread.sleep(5000);
		driver.quit();
		server.stop(0);
		System.out.println("Server started");
	}

	// Define a handler for the root URL
	static class RootHandler implements HttpHandler {
		@Override
		public void handle(HttpExchange exchange) throws IOException {
			String response = "Hello, World!";
			exchange.sendResponseHeaders(200, response.length());
			try (OutputStream os = exchange.getResponseBody()) {
				os.write(response.getBytes());
			}
		}
	}
	
	public String filePath;

	@Test
	public void server() throws IOException {
		// Create an HTTP server listening on port 8000
		HttpServer server = HttpServer.create(new InetSocketAddress("127.0.0.1", 8000), 0);

		// Create a context for the root URL (/)
		server.createContext("/", new HtmlHandler());

		// Set an executor to handle requests, or null for a default executor
		server.setExecutor(null);

		// Start the server
		server.start();
		System.out.println(filePath);
		WebDriver driver = new ChromeDriver();
		driver.get(filePath);
		System.out.println("Server started on http://127.0.0.1:8000");
	}

	// Define a handler for serving HTML files
	class HtmlHandler implements HttpHandler {
		@Override
		public void handle(HttpExchange exchange) throws IOException {
			filePath = "web" + exchange.getRequestURI().getPath();
			System.out.println("filePath-1: "+filePath);
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
	}

}