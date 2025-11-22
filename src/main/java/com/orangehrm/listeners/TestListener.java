package com.orangehrm.listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.orangehrm.base.BaseClass;
import com.orangehrm.utilities.ExtentManager;

public class TestListener implements ITestListener{

	@Override
	public void onTestStart(ITestResult result) {
		String testName=result.getMethod().getMethodName();
		ExtentManager.startTest(testName);
		ExtentManager.logStep("Test Started: "+testName);
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		String testName=result.getMethod().getMethodName();
		ExtentManager.logStepWithScreenshot(BaseClass.getDriver(), "Test Passed successfully!", "Test End: " + testName + " ✅ Test Passed");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		String testName=result.getMethod().getMethodName();
		String failMsg=result.getThrowable().getMessage();
		ExtentManager.logStep(failMsg);
		ExtentManager.logFailure(BaseClass.getDriver(), "Test Failed", "Test End: " + testName + " ❎ Test Failed");
		
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		String testName=result.getMethod().getMethodName();
		ExtentManager.logSkip("Test Skipped: "+ testName);
	}

	@Override
	public void onStart(ITestContext context) {
		ExtentManager.getReporter();
	}

	@Override
	public void onFinish(ITestContext context) {
		ExtentManager.endTest();
	}

}
