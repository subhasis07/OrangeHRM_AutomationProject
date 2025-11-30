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
	private By pimTab=By.xpath("//span[text()='PIM']");
	private By empSearchInput=By.xpath("//label[text()='Employee Name']/parent::div/following-sibling::div/div/div/inpit");
	private By empSearchButton=By.xpath("//button[@type='submit']");
	private By empFirstAndMiddleName=By.xpath("//div[@class='oxd-table-card']/div/div[3]");
	private By emplastName=By.xpath("//div[@class='oxd-table-card']/div/div[4]");
	
	
	public HomePage(WebDriver driver) {
		this.actionDriver=BaseClass.getActionDriver();
	}
	
	public boolean isAdminTabVisible() {
		return actionDriver.isDisplayed(adminTab);
	}
	
	public boolean verifyOrangeHRMLogo() {
		return actionDriver.isDisplayed(orangeHRMLogo);
	}
	
	public void clickOnPIMtab() {
		actionDriver.click(pimTab);
	}
	
	public void empSearch(String value) {
		actionDriver.enterText(empSearchInput, value);
		actionDriver.click(empSearchButton);
		actionDriver.scrollToelement(empFirstAndMiddleName);
		 
	}
	
	public boolean verifyFirstAndMiddleName(String empFirstAndMiddlenameFromDB){
		return actionDriver.compareText(empFirstAndMiddleName, empFirstAndMiddlenameFromDB);
	}
	
	public boolean verifyLastName(String empLastNameFromDB){
		return actionDriver.compareText(emplastName, empLastNameFromDB);
	}
	
	public void logout() {
		actionDriver.click(userIDButton);
		actionDriver.click(logoutButton);
	}
	
}
