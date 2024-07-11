package com.qa.Selenium.Tasks.Level1;

import java.io.IOException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;
import com.qa.Selenium.Tasks.StartServer;

public class Click {
	
	public String ServerURL;
	
	public Click() throws IOException, InterruptedException {
		StartServer Server = new StartServer();
		Server.serverTest();
		ServerURL = Server.serverURL;
	}

	@Test
	public void Task1() throws InterruptedException {
		WebDriver driver = new FirefoxDriver();
		driver.get(ServerURL+"1.01link.html");
		Thread.sleep(10000);
		driver.quit();
	}
}