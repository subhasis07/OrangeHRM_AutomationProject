package com.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.orangehrm.actiondriver.ActionDriver;
import com.orangehrm.base.BaseClass;

public class LoginPage {
	private ActionDriver actionDriver;
	
	//Define locators using By class
	private By userNameField= By.name("username");
	private By passwordField=By.cssSelector("input[type='password']");
	private By loginButton=By.xpath("//button[text()=' Login ']");
	private By errorMessage=By.xpath("//p[text()='Invalid credentials']");
	
//	public LoginPage(WebDriver driver) {
//		this.actionDriver=new ActionDriver(driver);
//	}
	
	public LoginPage(WebDriver driver) {
		this.actionDriver=BaseClass.getActionDriver();
	}
	
	//Method to perform Login
	public void login(String userName, String password) {
		actionDriver.enterText(userNameField, userName);
		actionDriver.enterText(passwordField, password);
		actionDriver.click(loginButton);

	}
	
	//Method to check if Error message is displayed
	public boolean isErrMsgDisplayed() {
		return actionDriver.isDisplayed(errorMessage);
		
	}
	
	//Method to get the text fro Error msg
	public String getErrorMessage() {
		return actionDriver.getText(errorMessage);
		
	}
	
	//verify error message
	public boolean verifyErrMsg(String expectedError) {
		return actionDriver.compareText(errorMessage, expectedError);
	}
	
	
	
}
