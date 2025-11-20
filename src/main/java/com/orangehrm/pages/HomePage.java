package com.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.orangehrm.actiondriver.ActionDriver;
import com.orangehrm.base.BaseClass;

public class HomePage {
	private ActionDriver actionDriver;
	
	//Locators
	private By adminTab= By.xpath("//span[text()='Admin']");
	private By userIDButton=By.className("oxd-userdropdown-name");
	private By logoutButton=By.xpath("//a[text()='Logout']");
	private By orangeHRMLogo=By.xpath("//div[@class='oxd-brand-banner']//img");
	
	
	public HomePage(WebDriver driver) {
		this.actionDriver=BaseClass.getActionDriver();
	}
	
	public boolean isAdminTabVisible() {
		return actionDriver.isDisplayed(adminTab);
	}
	
	public boolean verifyOrangeHRMLogo() {
		return actionDriver.isDisplayed(orangeHRMLogo);
	}
	
	public void logout() {
		actionDriver.click(userIDButton);
		actionDriver.click(logoutButton);
	}
	
}
