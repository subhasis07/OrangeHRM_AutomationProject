package com.orangehrm.test;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;

import com.orangehrm.base.BaseClass;
import com.orangehrm.pages.HomePage;
import com.orangehrm.pages.LoginPage;

public class HomePageTest extends BaseClass{
	private LoginPage loginPage;
	private HomePage homePage;
	
	@BeforeMethod
	public void setupPages() {
		loginPage= new LoginPage(getDriver());
		homePage=new HomePage(getDriver());
		
	}
	
	public void verifyOrangeHRMLogo() {
		loginPage.login("admin", "admin123");
		Assert.assertTrue(homePage.verifyOrangeHRMLogo(),"Logo Not VISIBLE");
	}
}
