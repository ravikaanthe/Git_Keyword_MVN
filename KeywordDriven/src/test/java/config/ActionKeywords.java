package config;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import com.beust.jcommander.Parameter;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import executionEngine.DriverScriptTest;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import utility.Log;

import static executionEngine.DriverScriptTest.OR;


public class ActionKeywords {
			
		public static WebDriver driver;
		public static WebDriver mdriver;
		public static ExtentReports reports;
		public static ExtentTest logger;
		
		//Constructor to initialize the object of class ExtentReports whenever we initialize object
		public ActionKeywords(){
			//Create an object of ExtentReports class
			reports=new ExtentReports(Constants.Extent_Reports);
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
					driver.manage().window().maximize();
					logger.log(LogStatus.PASS, "opened Browser -"+ data);
					Log.info("Chrome browser started");}
				else if (data.equals("IE")){
					//You may need to change the code here to start IE Driver
					System.setProperty("webdriver.ie.driver","C:\\JARs\\MicrosoftWebDriver.exe");
					driver=new InternetExplorerDriver();
					driver.manage().window().maximize();
					logger.log(LogStatus.PASS, "opened Browser -"+ data);
					Log.info("IE browser started");}
				else if(data.equals("Mozilla")){
					System.setProperty("webdriver.gecko.driver","C:\\JARs\\geckodriver-v0.19.0-win64\\geckodriver.exe");
					driver=new FirefoxDriver();
					logger.log(LogStatus.PASS, "opened Browser -"+ data);
					Log.info("Mozilla browser started");}
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
				logger.log(LogStatus.PASS, "Succefully Clicked on button "+ object);
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
				String acutalText = driver.findElement(By.xpath(object)).getAttribute("innerText");
				if(acutalText.equals(data))
					logger.log(LogStatus.PASS, "Expected text '"+data+ "'is same as actual text'"+ object);
			}catch(Exception e){
				Log.error("Not able to compare the text --- " + e.getMessage());
				logger.log(LogStatus.FAIL, "Unable to compare the text "+ object);
				DriverScriptTest.bResult = false;
			}
			
		}
		
		public static void verifyElement(String object, String data){
			try{
				Log.info("Verifying the element '"+ object);
				driver.findElement(By.xpath(OR.getProperty(object))).isDisplayed();
				elementHighlight(driver.findElement(By.xpath(OR.getProperty(object))));
				Thread.sleep(600);
				logger.log(LogStatus.PASS, "Webelement '"+ object+"'displayed on page");
			}catch(Exception e){
				Log.error("Unable to find Webelement --- " + e.getMessage());
				logger.log(LogStatus.FAIL, "Unable to find Webelement "+ object);
	 			DriverScriptTest.bResult = false;
			}
		}
		
		
		public static void mverifyElement(String object, String data){
			try{
				Log.info("Verifying the element '"+ object);
				mdriver.findElement(By.xpath(OR.getProperty(object))).isDisplayed();
				//elementHighlight(mdriver.findElement(By.xpath(OR.getProperty(object))));
				Thread.sleep(600);
				logger.log(LogStatus.PASS, "Webelement '"+ object+"'displayed on page");
			}catch(Exception e){
				Log.error("Unable to find Webelement --- " + e.getMessage());
				logger.log(LogStatus.FAIL, "Unable to find Webelement "+ object);
	 			DriverScriptTest.bResult = false;
			}
		}
		
		
		public static void elementHighlight(WebElement element) {
			for (int i = 0; i < 2; i++) {
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript(
						"arguments[0].setAttribute('style', arguments[1]);",
						element, "color: blue; border: 3px solid blue;");
				js.executeScript(
						"arguments[0].setAttribute('style', arguments[1]);",
						element, "");
			}
		}
		
		public static void compareLinkText(String object, String data){
			try{
				Log.info("Comparing link text '"+ object);
				String lnktxt=driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("innerText");
				elementHighlight(driver.findElement(By.xpath(OR.getProperty(object))));
				Thread.sleep(600);
				if (lnktxt.equals(data))
				logger.log(LogStatus.PASS, "Link text is matching expcted value-"+data);
				
			}catch(Exception e){
				Log.error("Link text is not matching with expected value --- " + e.getMessage());
				logger.log(LogStatus.FAIL, "Link text is not matched with expected value "+ data);
	 			DriverScriptTest.bResult = false;
			}
			
		}
		
		public static void mopenBrowser(String object, String data) throws MalformedURLException{
			
			//Create an object of DesiredCapabilities class and specify android platform
			DesiredCapabilities capabilities=DesiredCapabilities.android();
			
			//Set capability to execute test in Chrome Browser of mobile. MobileCapabilityType is an Interface 
			capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, BrowserType.CHROME);
			
			//Set the capability to execute our test in android platform
			capabilities.setCapability(MobileCapabilityType.PLATFORM, Platform.ANDROID);
			
			//We need to specify platform name as well along with above platform
			capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
			
			//Set the Device name as well (you can give any name)
			capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "My Phone");
			
			//Set the Android Version as well
			capabilities.setCapability(MobileCapabilityType.VERSION, "6.0.1");
			
			//Specify object of URL class and specify Appium server address
			URL url= new URL("http://127.0.0.1:4723/wd/hub");
			
			//Create object of Android Driver class and pass the url and capability that we created
			mdriver=new AndroidDriver(url, capabilities);
			
			//Open URL
			
		}
		
		public static void mnavigate(String object, String data){
			try{
				Log.info("Navigating to URL "+ "'" + Constants.URL1+"'");
				mdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				//Constant Variable is used in place of URL
				//As it was declared as 'static', it can be used by referring the class name
				//Type the class name 'Constants' and press '.' dot, it will display all the memebers of the class Constants
				mdriver.get(Constants.URL1);
				logger.log(LogStatus.PASS, "Navigated to URL - "+ Constants.URL1);
			}catch(Exception e){
				Log.info("Not able to navigate --- " + e.getMessage());
				logger.log(LogStatus.FAIL, "Unable to Navigate to URL - "+ Constants.URL1);
				DriverScriptTest.bResult = false;
			}
				
		}
		
		public static void minput(String object, String data){
			try{
				Log.info("Entering the text in "+ object);
				//Constant Variable is used in place of UserName
				//This is fetching the xpath of the element from the Object Repository property file
				mdriver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);
				mdriver.findElement(By.xpath(OR.getProperty(object))).sendKeys(Keys.TAB);
				logger.log(LogStatus.PASS, "Entered the text in "+ object);
			}catch(Exception e){
				Log.error("Not able to Enter UserName --- " + e.getMessage());
				DriverScriptTest.bResult = false;
				logger.log(LogStatus.FAIL, "Not able to enter text in "+ object);
			}
			 
		}
		
		public static void mclick(String object, String data){
			try{
				Log.info("Clicking on Webelement "+ object);
				mdriver.findElement(By.xpath(OR.getProperty(object))).click();
				logger.log(LogStatus.PASS, "Succefully Clicked on button "+ object);
			}catch(Exception e){
				Log.error("Not able to click --- " + e.getMessage());
				logger.log(LogStatus.FAIL, "Unable to Click on button "+ object);
	 			DriverScriptTest.bResult = false;
			}
			
		}
		
		public static void mcloseBrowser(String object, String data){
			try{
				Log.info("Closing the Browser");
				mdriver.quit();
				logger.log(LogStatus.PASS, "Succefully closed the browser -"+ object);
			}catch(Exception e){
				Log.error("Not able to Close the Browser --- " + e.getMessage());
				logger.log(LogStatus.FAIL, "Unable to close the browser -"+ object);
				DriverScriptTest.bResult = false;
			}
		}
}
