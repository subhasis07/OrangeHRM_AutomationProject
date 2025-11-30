package com.orangehrm.test;

import java.util.Map;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.orangehrm.base.BaseClass;
import com.orangehrm.pages.HomePage;
import com.orangehrm.pages.LoginPage;
import com.orangehrm.utilities.DBConnection;
import com.orangehrm.utilities.DataProviders;
import com.orangehrm.utilities.ExtentManager;

public class DBVerificationTest extends BaseClass{
	private LoginPage loginPage;
	private HomePage homePage;
	
	@BeforeMethod
	public void setupPages() {
		loginPage= new LoginPage(getDriver());
		homePage=new HomePage(getDriver());
		
	}
	
	@Test(dataProvider = "empVerification",dataProviderClass = DataProviders.class)
	public void verifyEmployeeNameVerificationFromDB(String empID, String empName) {
		ExtentManager.logStep("Loggong with Admin Creds");
		loginPage.login(prop.getProperty("username"), prop.getProperty("password"));
		ExtentManager.logStep("Click on PIM Tab");
		homePage.clickOnPIMtab();
		ExtentManager.logStep("Search for Employee");
		homePage.empSearch(empName);
		ExtentManager.logStep("Get the employee from DB");
		String Emp_id=empID;
		
		Map<String,String> empDetails=DBConnection.getEmployeeDetails(Emp_id);
		
		String empFirstName= empDetails.get("firstName");
		String empMiddleName= empDetails.get("middleName");
		String empLastName= empDetails.get("lastName");
		
		String empFirstAndMiddleName=(empFirstName + " "+ empMiddleName).trim();
		
		ExtentManager.logStep("Verify the Employee first & middle name");
		Assert.assertTrue(homePage.verifyFirstAndMiddleName(empFirstAndMiddleName),"First & Middle name are not matching");
		
		ExtentManager.logStep("Verify the Employee Last name");
		Assert.assertTrue(homePage.verifyLastName(empLastName),"Employee last name is not matching");
		
		ExtentManager.logStep("Verification from DB completed");
	}
}
