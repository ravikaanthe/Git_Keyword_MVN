package executionEngine;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import utility.Log;

public class MultiBrowser {
	
	static WebDriver driver;
	@Test
	@Parameters("browser")
	public static void multiBrowser(String browserName){
		try{
			Log.info("Opening Browser");
			//If value of the parameter is Chrome, this will execute
			if (browserName.equals("Chrome")){
				System.setProperty("webdriver.chrome.driver","C:\\JARs\\chromedriver_win32\\chromedriver.exe");
				driver=new ChromeDriver();
				driver.manage().window().maximize();
				Log.info("Chrome browser started");}
			else if (browserName.equals("IE")){
				//You may need to change the code here to start IE Driver
				System.setProperty("webdriver.ie.driver","C:\\JARs\\MicrosoftWebDriver.exe");
				driver=new InternetExplorerDriver();
				driver.manage().window().maximize();
				Log.info("IE browser started");}
			else if(browserName.equals("Mozilla")){
				System.setProperty("webdriver.gecko.driver","C:\\JARs\\geckodriver-v0.19.0-win64\\geckodriver.exe");
				driver=new FirefoxDriver();
				Log.info("Mozilla browser started");}
			//This block will execute only in case of an exception
		}catch(Exception e){
			//This is to print the logs - Method Name & Error description/stack
			Log.info("Not able to open Browser --- " + e.getMessage());
			//Set the value of result variable to false
			DriverScriptTest.bResult = false;
		}
		
	}

}
