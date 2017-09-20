package config;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class CustomListener extends TestListenerAdapter{
	
	@Override
	public void onTestSuccess(ITestResult tr) {
		//print the test case name with test case pass
		System.out.println("Test Case Pass ::: "+tr.getName());
		
	}	

	@Override
	public void onTestFailure(ITestResult tr) {
		//Take a screenshot if the test case fails
		File scrFile = ((TakesScreenshot)ActionKeywords.driver).getScreenshotAs(OutputType.FILE);
		// Now you can do whatever you need to do with it, for example copy somewhere
		try {
			FileUtils.copyFile(scrFile, new File("..//Selenium_Reports/target/"+tr.getName()+".png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
