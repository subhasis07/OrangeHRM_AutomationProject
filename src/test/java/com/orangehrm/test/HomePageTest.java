package com.orangehrm.test;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.orangehrm.base.BaseClass;
import com.orangehrm.pages.HomePage;
import com.orangehrm.pages.LoginPage;
import com.orangehrm.utilities.DataProviders;
import com.orangehrm.utilities.ExtentManager;

public class HomePageTest extends BaseClass{
	private LoginPage loginPage;
	private HomePage homePage;
	
	@BeforeMethod
	public void setupPages() {
		loginPage= new LoginPage(getDriver());
		homePage=new HomePage(getDriver());
		
	}
	@Test(dataProvider = "validLoginData",dataProviderClass = DataProviders.class)
	public void verifyOrangeHRMLogo(String username, String password) {
//		ExtentManager.startTest("Home page logo test"); -- commented as this have benn implemented in testlistneres
		ExtentManager.logStep("navogating to Login Page entering username & password");
		loginPage.login(username, password);
		ExtentManager.logStep("Verifying logo is visible or not");
		Assert.assertTrue(homePage.verifyOrangeHRMLogo(),"Logo Not VISIBLE");
		ExtentManager.logStep("Validation Successful");
		ExtentManager.logStep("Logged out successfully");
		
	}
}
