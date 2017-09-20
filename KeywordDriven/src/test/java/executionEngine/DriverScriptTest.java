package executionEngine;

import org.testng.annotations.Test;
import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.util.Properties;

import org.apache.log4j.xml.DOMConfigurator;

import config.ActionKeywords;
import config.Constants;
import utility.ExcelUtils;
import utility.Log;

public class DriverScriptTest {
	
	//This is a class object, declared as 'public static'
	//So that it can be used outside the scope of main[] method
	public static Properties OR;
	public static ActionKeywords actionKeywords;
	public static String sActionKeyword;
	public static String sPageObject;
	//This is reflection class object, declared as 'public static'
	//So that it can be used outside the scope of main[] method
	public static Method method[];
	
	public static int iTestStep;
	public static int iTestLastStep;
	public static String sTestCaseID;
	public static String sRunMode;
	public static boolean bResult;
	public static String sData;
	
	//Here we are instantiating a new object of class 'ActionKeywords'

	public DriverScriptTest() throws NoSuchMethodException, SecurityException{
		actionKeywords = new ActionKeywords();
		//This will load all the methods of the class 'ActionKeywords' in it.
                //It will be like array of method, use the break point here and do the watch
		method = actionKeywords.getClass().getMethods();
	}

@Test
    public void Initialize() throws Exception {
		
		//Added this for initialize log4j system properly via TestNG
		//BasicConfigurator.configure();
    	
    	ExcelUtils.setExcelFile(Constants.Path_TestData);
    	
    	//VIMP: Call above constructor which will create an array of methods in ActionKeywords class
    	//DriverScriptTest method = new DriverScriptTest();
    	//Declaring the path of the Excel file with the name of the Excel file
    	//Instead of hard coded Excel path, a Constant Variable is used
    	//String sPath = Constants.Path_TestData;
 
    	//Here we are passing the Excel path and SheetName to connect with the Excel file
        //This method was created in the last chapter of 'Set up Data Engine'
    	//Again a Constant Variable is used in place of Excel Sheet Name
    	//ExcelUtils.setExcelFile(sPath, Constants.Sheet_TestSteps);
    	
    	//This is to start the Log4j logging in the test case
    	DOMConfigurator.configure("Log4j.xml");
    	
    	//Declaring String variable for storing Object Repository path
    	String Path_OR = Constants.Path_OR;
    	//Creating file system object for Object Repository text/property file
    	FileInputStream fs=new FileInputStream(Path_OR);
    	//Creating an Object of properties
    	OR=new Properties(System.getProperties());
    	//Loading all the properties from Object Repository property file in to OR object
    	OR.load(fs);
 
    	DriverScriptTest startEngine = new DriverScriptTest();
    	startEngine.execute_TestCase();
    }
    
    private void execute_TestCase() throws Exception {
    	
    	//This will return the total number of test cases mentioned in the Test cases sheet
    	int iTotalTestCases = ExcelUtils.getRowCount(Constants.Sheet_TestCases);
    	//This loop will execute number of times equal to Total number of test cases
    	for(int iTestcase=1;iTestcase<iTotalTestCases;iTestcase++){
    		//Setting the value of bResult variable to 'true' before starting every test case
			bResult = true;
    		//This is to get the Test case name from the Test Cases sheet
			sTestCaseID = ExcelUtils.getCellData(iTestcase, Constants.Col_TestCaseID, Constants.Sheet_TestCases);
			//This is to get the value of the Run Mode column for the current test case
			sRunMode = ExcelUtils.getCellData(iTestcase, Constants.Col_RunMode,Constants.Sheet_TestCases);
			//This is the condition statement on RunMode value
			if (sRunMode.equals("Yes")){
				//Start publishing the report using Extent Reports
				actionKeywords.logger=actionKeywords.reports.startTest(sTestCaseID);
				//Only if the value of Run Mode is 'Yes', this part of code will execute
				iTestStep = ExcelUtils.getRowContains(sTestCaseID, Constants.Col_TestCaseID, Constants.Sheet_TestSteps);
				iTestLastStep = ExcelUtils.getTestStepsCount(Constants.Sheet_TestSteps, sTestCaseID, iTestStep);
				Log.startTestCase(sTestCaseID);
				//Setting the value of bResult variable to 'true' before starting every test step
				bResult=true;
				//This loop will execute number of times equal to Total number of test steps
				for (;iTestStep<=iTestLastStep;iTestStep++){
					sActionKeyword = ExcelUtils.getCellData(iTestStep, Constants.Col_ActionKeyword,Constants.Sheet_TestSteps);
					sPageObject = ExcelUtils.getCellData(iTestStep, Constants.Col_PageObject, Constants.Sheet_TestSteps);
					//Now we will use the data value and pass it to the methods
					sData = ExcelUtils.getCellData(iTestStep, Constants.Col_DataSet, Constants.Sheet_TestSteps);
					execute_Actions();
					//This is the result code, this code will execute after each test step
					//The execution flow will go in to this only if the value of bResult is 'false'
					if(bResult==false){
						//If 'false' then store the test case result as Fail
						ExcelUtils.setCellData(Constants.KEYWORD_FAIL,iTestcase,Constants.Col_Result,Constants.Sheet_TestCases);
						//End the test case in the logs
						Log.endTestCase(sTestCaseID);
						//By this break statement, execution flow will not execute any more test step of the failed test case
						break;
						}
				}
				//End logging after executing every test case from Test Case sheet
				ActionKeywords.reports.endTest(actionKeywords.logger);
				ActionKeywords.reports.flush();
				
				//This will only execute after the last step of the test case, if value is not 'false' at any step	
				if(bResult==true){
				//Storing the result as Pass in the excel sheet
				ExcelUtils.setCellData(Constants.KEYWORD_PASS,iTestcase,Constants.Col_Result,Constants.Sheet_TestCases);
				Log.endTestCase(sTestCaseID);
				}
			}
    	}
    }
    
//    	
//    	
//    	
//    	
//    	
//    	
//    	
//    	
//    //Hard coded values are used for Excel row & columns for now
//    	//In later chapters we will use these hard coded value much efficiently
//    	//This is the loop for reading the values of the column 3 (Action Keyword) row by row
//		//It means this loop will execute all the steps mentioned for the test case in Test Steps sheet
//    	int rowCount = ExcelUtils.getRowCount(Constants.Sheet_TestSteps);
//    	for (int iRow = 1;iRow <= rowCount;iRow++){
//		    //This to get the value of column Action Keyword from the excel
//    		sActionKeyword = ExcelUtils.getCellData(iRow, Constants.Col_ActionKeyword);
//    		//Now get the value of column Page Object from the excel DataEngine
//    		sPageObject = ExcelUtils.getCellData(iRow, Constants.Col_PageObject);
//    		
//            //A new separate method is created with the name 'execute_Actions'
//			//You will find this method below of the this test
//			//So this statement is doing nothing but calling that piece of code to execute
//    		execute_Actions();
//    		}
//    	}


	//This method contains the code to perform some action
	//As it is completely different set of logic, which revolves around the action only,
	//It makes sense to keep it separate from the main driver script
	//This is to execute test step (Action)
    @Test
    private static void execute_Actions() throws Exception {
		//This is a loop which will run for the number of actions in the Action Keyword class 
		//method variable contain all the method and method.length returns the total number of methods
		for(int i = 0;i < method.length;i++){
			//This is now comparing the method name with the ActionKeyword value got from excel
			if(method[i].getName().equals(sActionKeyword)){
				//In case of match found, it will execute the matched method
				//Passing 'Page Object' name and 'Action Keyword' as Arguments to this method
				//This code will pass three parameters to every invoke method
				method[i].invoke(actionKeywords,sPageObject,sData);
				//This code block will execute after every test step
				if(bResult==true){
					//If the executed test step value is true, Pass the test step in Excel sheet
					ExcelUtils.setCellData(Constants.KEYWORD_PASS, iTestStep, Constants.Col_TestStepResult, Constants.Sheet_TestSteps);
					break;
				}else{
					//If the executed test step value is false, Fail the test step in Excel sheet
					ExcelUtils.setCellData(Constants.KEYWORD_FAIL, iTestStep, Constants.Col_TestStepResult, Constants.Sheet_TestSteps);
					//In case of false, the test execution will not reach to last step of closing browser
					//So it make sense to close the browser before moving on to next test case
					//ActionKeywords.closeBrowser("","");
				//Once any method is executed, this break statement will take the flow outside of for loop
				break;
				}
			}
		}
    }
			
}