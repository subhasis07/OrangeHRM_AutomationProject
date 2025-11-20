package com.orangehrm.test;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.orangehrm.base.BaseClass;
import com.orangehrm.pages.HomePage;
import com.orangehrm.pages.LoginPage;

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
		loginPage.login("admin", "admin123");
		Assert.assertTrue(homePage.isAdminTabVisible(), "Login Failed");
		homePage.logout();
		staticWait(2);
	}
	
	@Test
	public void invalidLoginTest() {
		loginPage.login("user", "user");
		String expectedErrMsg= "Invalid credentials";
		Assert.assertTrue(loginPage.verifyErrMsg(expectedErrMsg), "Invalid Error Message");
		
	}
}
