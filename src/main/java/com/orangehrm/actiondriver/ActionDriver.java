package com.orangehrm.actiondriver;

import java.time.Duration;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.orangehrm.base.BaseClass;
import com.orangehrm.utilities.ExtentManager;

public class ActionDriver {
	private WebDriver driver;
	private WebDriverWait wait;
	public static final Logger logger= BaseClass.logger;

	public ActionDriver(WebDriver driver) {
		this.driver = driver;
		int explicitWait=Integer.parseInt(BaseClass.getProp().getProperty("explicitwait"));
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(explicitWait));
		logger.info("Webdriver instance created");
	}

	// Method to click an element
	public void click(By by) {
		String elementDescription=getElementDescription(by);
		try {
			waitForElementToBeClickable(by);
			driver.findElement(by).click();
			ExtentManager.logStep("Clicked an Element: "+elementDescription);
			logger.info("Clicked an element --> "+ elementDescription);
		} catch (Exception e) {
			System.out.println("Unable to click the element: " + e.getMessage());
			ExtentManager.logFailure(BaseClass.getDriver(), "Unable to click ", elementDescription);
			logger.error("unable to click the element");
		}
	}

	// Method to enter text
	public void enterText(By by, String value) {
		try {
			waitForElementToBeVisible(by);
			WebElement element = driver.findElement(by);
			element.clear();
			element.sendKeys(value);;
			logger.info("Entered text on: "+getElementDescription(by)+ "-->"+value);
		} catch (Exception e) {
			logger.error("Unable to enter text in the element: " + e.getMessage());
		}
	}

	// Method to get text from an input field
	public String getText(By by) {
		try {
			waitForElementToBeVisible(by);
			return driver.findElement(by).getText();
		} catch (Exception e) {
			logger.error("unable to get the text: "+e.getMessage());
			return "";
		}
	}

	// Method to compare two text
	public boolean compareText(By by, String expectedText) {
		try {
			waitForElementToBeVisible(by);
			String actualText = driver.findElement(by).getText();
			if (expectedText.equals(actualText)) {
				logger.info("Text MATCHED");
				ExtentManager.logStepWithScreenshot(BaseClass.getDriver(), "compare text", "Text Verified successfully" + actualText +" equals "+ expectedText);
				return true;
			} else {
				logger.error("Text MISMATCHED");
				ExtentManager.logFailure(BaseClass.getDriver(), "compare text", "Text Comparision failed" + actualText +" not equals "+ expectedText);
				return false;
			}
		} catch (Exception e) {
			logger.error("Unable to compare texts: " + e.getMessage());
		}
		return false;
	}

	// Method to check if an elem is displayed
	public boolean isDisplayed(By by) {
		try {
			waitForElementToBeVisible(by);
			logger.info("Element that displayed: "+ getElementDescription(by));
			ExtentManager.logStep("Element is Displayed: "+getElementDescription(by));
			ExtentManager.logStepWithScreenshot(BaseClass.getDriver(), "Element is Displayed", getElementDescription(by));
			return driver.findElement(by).isDisplayed();
			
		} catch (Exception e) {
			logger.error("Element not displayed: " + e.getMessage());
			ExtentManager.logFailure(BaseClass.getDriver(), "Element not Displayed: ", "Element is not displayed: "+ getElementDescription(by));
			return false;
		}
	}

	// wait for the page to load
	public void waitForPageLoad(int timeoutInSec) {
		try {
			wait.withTimeout(Duration.ofSeconds(timeoutInSec)).until(WebDriver -> ((JavascriptExecutor) WebDriver)
					.executeScript("return document.readyState").equals("complete"));
			logger.info("page loaded successfully");
		} catch (Exception e) {
			logger.error("Page not loaded within " + timeoutInSec + " secs .Exception: " + e.getMessage());
		}
	}

	// scroll to an element
	public void scrollToelement(By by) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			WebElement element = driver.findElement(by);
			js.executeScript("arguments[0].scrollIntoView(true);", element);
		} catch (Exception e) {
			logger.error("Unable to locate element: " + e.getMessage());
		}
	}

	// wait for element to be clickable
	private void waitForElementToBeClickable(By by) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(by));
		} catch (Exception e) {
			logger.error("element is not clickable: " + e.getMessage());
		}

	}

	// wait for element to be visible
	private void waitForElementToBeVisible(By by) {
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		} catch (Exception e) {
			logger.error("element is not visible: " + e.getMessage());
		}

	}
	
	
	//methid to get description of an element using By locator
	
	public String getElementDescription(By locator) {
		if(driver==null) {
			return "driver is null";
		}
		if(locator==null) {
			return "Locator is null";
		}
		
		
		
		try {
			WebElement element=driver.findElement(locator);
			
			
			String name=element.getDomAttribute("name");
			String id=element.getDomAttribute("id");
			String text=element.getText();	
			String placeHolder=element.getDomAttribute("placeholder");
			String classname=element.getDomAttribute("class");
			
			if(isNotEmpty(name)) {
				return "Element with name: "+ name;
			}else if(isNotEmpty(id)) {
				return "Element with id: "+ id;
			}else if(isNotEmpty(text)) {
				return "Element with text: "+ truncateText(text,30);
			}else if(isNotEmpty(classname)) {
				return "Element with class: "+classname;
			}else {
				return "Element with placeHolder: "+placeHolder;
			}
		} catch (Exception e) {
			logger.error("Unable to describe the element: "+ e.getMessage());
		}
		
		return "Unable to describe the element";
		
	}
	
	private boolean isNotEmpty(String val) {
		return val!=null && !val.isEmpty();
	}
	
	private String truncateText(String val, int maxLength) {
		if(val==null || val.length()<=maxLength) {
			return val;
		}
		return val.substring(0,maxLength)+"...";
	}
}
