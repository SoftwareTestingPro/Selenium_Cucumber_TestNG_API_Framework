package com.qa.Selenium.CodeWithSushil.Level1;

import java.io.IOException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;
import com.qa.Selenium.CodeWithSushil.Server;

public class Click extends Server{
	
	public String ServerURL;
	public Server Server;
	
	public Click() throws IOException, InterruptedException {
		Server = new Server();
		Server.startserver();
		ServerURL = Server.serverURL;
	}

	@Test
	public void Task1() throws InterruptedException {
		WebDriver driver = new EdgeDriver();
		driver.get(ServerURL+"1.01link.html");
		System.out.println("URL has been launched: "+ServerURL+"1.01link.html");
		Thread.sleep(5000);
		driver.quit();
	}
	
	@AfterTest
	public void StopServer() {
		Server.stopserver();
	}
}