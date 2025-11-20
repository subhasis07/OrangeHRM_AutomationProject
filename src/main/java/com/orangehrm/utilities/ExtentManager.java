package com.orangehrm.utilities;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {
	private static ExtentReports extent;
	private static ThreadLocal<ExtentTest> test= new ThreadLocal<>();
	private static Map<Long, WebDriver> driverMap= new HashMap<>();
	
	//initialize extent report
	public synchronized static ExtentReports getReporter() {
		if(extent==null) {
			String reportPath=System.getProperty("user.dir")+"/src/test/resources/ExtentReport/ExtentReport.html";
			ExtentSparkReporter spark= new ExtentSparkReporter(reportPath);
			spark.config().setReportName("Automation Test Report");
			spark.config().setDocumentTitle("OrangeHRM Report");
			spark.config().setTheme(Theme.DARK);
			
			extent=new ExtentReports();
			//Add system Info
			extent.setSystemInfo("OS", System.getProperty("os.name"));
			extent.setSystemInfo("Java Version", System.getProperty("java.version"));
			extent.setSystemInfo("User Name", System.getProperty("user.name"));
			
		}
		
		return extent;
	}
	
	//start the test
	public synchronized static ExtentTest startTest(String testname) {
		ExtentTest extentTest = getReporter().createTest(testname);
		test.set(extentTest);
		return extentTest;
	}
	
	//End the test
	public synchronized static void endTest() {
		getReporter().flush();
	}
	
	//getCurrent thread's name
	public synchronized static ExtentTest getTest() {
		return test.get();
	}
	
	//Method to get the name of the current test
	public static String getTestname() {
		ExtentTest currentTest=getTest();
		if(currentTest!=null) {
			return currentTest.getModel().getName();
		}else {
			return "No test is currently active for this test";
		}
	}
	
	
	//Log a step
	public static void logStep(String logMessage) {
		getTest().info(logMessage);
	}
	
	//Log a step validation with Screenshot
	public static void logStepWithScreenshot(WebDriver driver, String logMessage, String screenshotMessage) {
		getTest().pass(screenshotMessage);
		attachScreenshot(driver, screenshotMessage);
	}
	
	//Log a failure
	public static void logFailure(WebDriver driver, String logMessage, String screenshotMessage) {
		getTest().fail(logMessage);
		attachScreenshot(driver, screenshotMessage);
	}
	
	//log a skip
	public static void logSkip(String logMessage) {
		getTest().skip(logMessage);
	}
	
	//taking ss with date & time
	public synchronized static String takeScreenshot(WebDriver driver, String screenShotName) {
		TakesScreenshot ts = (TakesScreenshot)driver;
		File src= ts.getScreenshotAs(OutputType.FILE);
		//Format date & time for filename
		String timeStamp= new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
		
		//saving ss in a file
		String destPath=System.getProperty("user.dir")+"/src/test/resources/ExtentReport/screenshots"+screenShotName+"_"+timeStamp+".png";
		File finalPath = new File(destPath);
		try {
			FileUtils.copyFile(src, finalPath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//conver ss to base64 for embedding in report
		String base64Format=convertToBase64(src);
		return base64Format;
	}
	
	//Convert ss to base64 format
	public static String convertToBase64(File screenshotFile) {
		String base64Format="";
		//Read the file content into a byte array
		
		try {
			byte[] fileContent = FileUtils.readFileToByteArray(screenshotFile);
			base64Format=Base64.getEncoder().encodeToString(fileContent);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//convert the byte to base64
		return base64Format;
		
	}
	
	//Attach ss to report using bsae64
	public synchronized static void attachScreenshot(WebDriver driver, String message) {
		try{
			String screenshotBase64=takeScreenshot(driver, getTestname());
			getTest().info(message,com.aventstack.extentreports.MediaEntityBuilder.createScreenCaptureFromBase64String(screenshotBase64).build());
		}catch(Exception e){
			getTest().fail("Failed to attach Screenshot"+ e.getMessage());
			
		}
		
	}
	//register webdriver for current thread
	public static void registerDriver(WebDriver driver) {
		driverMap.put(Thread.currentThread().getId(), driver);
	}
}
