package com.orangehrm.test;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;

import com.orangehrm.base.BaseClass;
import com.orangehrm.pages.HomePage;
import com.orangehrm.pages.LoginPage;
import com.orangehrm.utilities.ExtentManager;

public class HomePageTest extends BaseClass{
	private LoginPage loginPage;
	private HomePage homePage;
	
	@BeforeMethod
	public void setupPages() {
		loginPage= new LoginPage(getDriver());
		homePage=new HomePage(getDriver());
		
	}
	
	public void verifyOrangeHRMLogo() {
		ExtentManager.startTest("Home page logo test");
		ExtentManager.logStep("navogating to Login Page entering username & password");
		loginPage.login("admin", "admin123");
		ExtentManager.logStep("Verifying logo is visible or not");
		Assert.assertTrue(homePage.verifyOrangeHRMLogo(),"Logo Not VISIBLE");
		ExtentManager.logStep("Validation Successful");
		ExtentManager.logStep("Logged out successfully");
		
	}
}
