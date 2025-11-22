package com.orangehrm.test;

import org.testng.SkipException;
import org.testng.annotations.Test;

import com.orangehrm.base.BaseClass;
import com.orangehrm.utilities.ExtentManager;

public class DummyClass extends BaseClass{
	
	@Test
	public void dummyTest() {
//		ExtentManager.startTest("Dummy test1 test"); -- commented as this have benn implemented in testlistneres
		String title=getDriver().getTitle();
		ExtentManager.logStep("Verifying Title");
		assert title.equals("OrangeHRM"): "Test Failed";
		System.out.println("Test Passed");
		ExtentManager.logSkip("This case is skipped");
		throw new SkipException("Skipping this as part of testing");
	}
}
