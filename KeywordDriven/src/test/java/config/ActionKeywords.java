package config;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.Assert;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import executionEngine.DriverScriptTest;
import utility.Log;

import static executionEngine.DriverScriptTest.OR;


public class ActionKeywords {
			
		public static WebDriver driver;
		public static ExtentReports reports;
		public static ExtentTest logger;
		
		public ActionKeywords(){
			reports=new ExtentReports("src\\test\\java\\Reports\\Report.html");
		}
		//This block of code will decide which browser type to start
		//All the methods in this class now accept 'Object' name as an argument
		public static void openBrowser(String object, String data){
			try{
				Log.info("Opening Browser");
				//If value of the parameter is Chrome, this will execute
				if (data.equals("Chrome")){
					System.setProperty("webdriver.chrome.driver","C:\\JARs\\chromedriver_win32\\chromedriver.exe");
					driver=new ChromeDriver();	
					logger.log(LogStatus.PASS, "opened Browser -"+ data);
					Log.info("Chrome browser started");}
				else if (data.equals("IE")){
					//You may need to change the code here to start IE Driver
					driver=new InternetExplorerDriver();
					logger.log(LogStatus.PASS, "opened Browser -"+ data);
					Log.info("IE browser started");}
				else if(data.equals("Mozilla")){
					driver=new FirefoxDriver();
					logger.log(LogStatus.PASS, "opened Browser -"+ data);
					Log.info("Mozilla browser started");}
				int implicitWaitTime=(10);
				driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
				//This block will execute only in case of an exception
			}catch(Exception e){
				//This is to print the logs - Method Name & Error description/stack
				Log.info("Not able to open Browser --- " + e.getMessage());
				logger.log(LogStatus.FAIL, "Unable to opened Browser -"+ data);
				//Set the value of result variable to false
				DriverScriptTest.bResult = false;
			}
			
		}
		
		public static void navigate(String object, String data){
			try{
				Log.info("Navigating to URL "+ "'" + Constants.URL+"'");
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				//Constant Variable is used in place of URL
				//As it was declared as 'static', it can be used by referring the class name
				//Type the class name 'Constants' and press '.' dot, it will display all the memebers of the class Constants
				driver.get(Constants.URL);
				logger.log(LogStatus.PASS, "Navigated to URL - "+ Constants.URL);
			}catch(Exception e){
				Log.info("Not able to navigate --- " + e.getMessage());
				logger.log(LogStatus.FAIL, "Unable to Navigate to URL - "+ Constants.URL);
				DriverScriptTest.bResult = false;
			}
				
		}
		
		public static void input(String object, String data){
			try{
				Log.info("Entering the text in "+ object);
				//Constant Variable is used in place of UserName
				//This is fetching the xpath of the element from the Object Repository property file
				driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);
				logger.log(LogStatus.PASS, "Entered the text in "+ object);
			}catch(Exception e){
				Log.error("Not able to Enter UserName --- " + e.getMessage());
				DriverScriptTest.bResult = false;
				logger.log(LogStatus.FAIL, "Not able to enter text in "+ object);
			}
			 
		}
		
//		public static void input_Password(String object){
//			try{
//				Log.info("Entering the text in "+ object);
//				driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(Constants.Password);
//			}catch(Exception e){
//				Log.error("Not able to Enter Password --- " + e.getMessage());
//				DriverScriptTest.bResult = false;
//			}
//			
//		}
		
		public static void click(String object, String data){
			try{
				Log.info("Clicking on Webelement "+ object);
				driver.findElement(By.xpath(OR.getProperty(object))).click();
				logger.log(LogStatus.PASS, "Succefully Clicked on button"+ object);
			}catch(Exception e){
				Log.error("Not able to click --- " + e.getMessage());
				logger.log(LogStatus.FAIL, "Unable to Click on button "+ object);
	 			DriverScriptTest.bResult = false;
			}
			
		}
		
		public static void waitFor(String object, String data) throws Exception{
			try{
				Log.info("Wait for 5 seconds");
				Thread.sleep(2000);
			}catch(Exception e){
				Log.error("Not able to Wait --- " + e.getMessage());
				DriverScriptTest.bResult = false;
			}
			
		}
		
		public static void closeBrowser(String object, String data){
			try{
				Log.info("Closing the Browser");
				driver.quit();
				logger.log(LogStatus.PASS, "Succefully closed the browser -"+ object);
			}catch(Exception e){
				Log.error("Not able to Close the Browser --- " + e.getMessage());
				logger.log(LogStatus.FAIL, "Unable to close the browser -"+ object);
				DriverScriptTest.bResult = false;
			}
			
		}
		
		public static String GetText(String object, String data){
			try{
				Log.info("Getting the text of '"+object+"'");
				String Text = driver.findElement(By.xpath(OR.getProperty(object))).getText();
				return Text;
			}catch (Exception e){
				Log.error("Not able read the text --- " + e.getMessage());
				DriverScriptTest.bResult = false;
				return null;
			}
			
		}
		
		public static void compareGetText(String object, String data){
			try{
				Log.info("Comparing the text '" +data+ "' with '"+object+"'" );
				String acutalText = driver.findElement(By.xpath(object)).getText();
				Assert.assertEquals(data, acutalText);
			}catch(Exception e){
				Log.error("Not able to compare the text --- " + e.getMessage());
				logger.log(LogStatus.FAIL, "Unable to compare the text"+ object);
				DriverScriptTest.bResult = false;
			}
			
		}
		
}