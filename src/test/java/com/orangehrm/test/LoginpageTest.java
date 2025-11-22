package com.orangehrm.test;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.orangehrm.base.BaseClass;
import com.orangehrm.pages.HomePage;
import com.orangehrm.pages.LoginPage;
import com.orangehrm.utilities.ExtentManager;

public class LoginpageTest extends BaseClass{
	private LoginPage loginPage;
	private HomePage homePage;
	
	@BeforeMethod
	public void setupPages() {
		loginPage= new LoginPage(getDriver());
		homePage=new HomePage(getDriver());
		
	}
	
	@Test
	public void verifyValidLogin() {
//		ExtentManager.startTest("Valid Login test"); -- commented as this have benn implemented in testlistneres
		System.out.println("Running test1 on thread: "+ Thread.currentThread().getId());
		ExtentManager.logStep("navogating to Login Page entering username & password");
		loginPage.login("admin", "admin123");
		ExtentManager.logStep("VErifying Admin Tab is visble or not");
		Assert.assertTrue(homePage.isAdminTabVisible(), "Login Failed");
		ExtentManager.logStep("Validation successful");
		homePage.logout();
		ExtentManager.logStep("Logged out successfully");
		staticWait(2);
	}
	
	@Test
	public void invalidLoginTest() {
//		ExtentManager.startTest("Invalid Login test"); -- commented as this have benn implemented in testlistneres
		System.out.println("Running test2 on thread: "+ Thread.currentThread().getId());
		ExtentManager.logStep("navogating to Login Page entering username & password");
		loginPage.login("user", "user");
		String expectedErrMsg= "Invalid credentials";
		Assert.assertTrue(loginPage.verifyErrMsg(expectedErrMsg), "Invalid Error Message");
		ExtentManager.logStep("Validation Successful");
		ExtentManager.logStep("Logged out successfully");
		
	}
}
