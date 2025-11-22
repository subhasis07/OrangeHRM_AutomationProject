package com.orangehrm.base;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.orangehrm.actiondriver.ActionDriver;
import com.orangehrm.utilities.ExtentManager;
import com.orangehrm.utilities.LoggerManager;

public class BaseClass {
	protected static Properties prop;
//	protected static WebDriver driver;
//	private static ActionDriver actionDriver;
	
	private static ThreadLocal<WebDriver> driver= new ThreadLocal<>();
	private static ThreadLocal<ActionDriver> actionDriver= new ThreadLocal<>();
	
	public static final Logger logger= LoggerManager.getLogger(BaseClass.class);
	
	
	// load the config file
	@BeforeSuite
	public void loadConfig() throws IOException {

		prop = new Properties();
		FileInputStream fis = new FileInputStream("src/main/resources/config.properties");
		prop.load(fis);
		logger.info("config.properties files loaded");
		
		//start the extent report
//		ExtentManager.getReporter(); -- commented as this have benn implemented in testlistneres
		
		
	}

	@BeforeMethod
	public synchronized void setup() throws IOException {
		System.out.println("setting up webdriver for: " + this.getClass().getSimpleName());
		launchBrowser();
		configureBrowser();
		staticWait(3);
		
		logger.info("webdriver initialized & browser maximized");
		
//		if(actionDriver==null) {
//			actionDriver=new ActionDriver(driver);
//			logger.info("Actiondriver instance created");
//		}
		
		//initialize action driver for curr thread
		actionDriver.set(new ActionDriver(getDriver()));
		logger.info("Actiondriver initialized for thread: " +Thread.currentThread().getId());
	}

	// Initialize webdriver based on browser defined
	private synchronized void launchBrowser() {

		String browser = prop.getProperty("browser");

		if (browser.equalsIgnoreCase("chrome")) {
//			driver = new ChromeDriver();
			driver.set(new ChromeDriver());
			ExtentManager.registerDriver(getDriver());
			logger.info("Chromedriver instance created");
		} else if (browser.equalsIgnoreCase("firefox")) {
//			driver = new FirefoxDriver();
			driver.set(new FirefoxDriver());
			ExtentManager.registerDriver(getDriver());
			logger.info("Firefoxdriver instance created");
		} else if (browser.equalsIgnoreCase("edge")) {
//			driver = new EdgeDriver();
			driver.set(new EdgeDriver());
			ExtentManager.registerDriver(getDriver());
			logger.info("Edgedriver instance created");
		} else {
			throw new IllegalArgumentException("Browser not SUPPORTED: " + browser);
		}
	}

	// config browser setting like, wait, maximize, navigate
	private void configureBrowser() {
		// Implicit wait
		int implicitwait = Integer.parseInt(prop.getProperty("implicitwait"));
		getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitwait));

		// maximize the browser
		getDriver().manage().window().maximize();

		// navigate to URL
		try {
			getDriver().get(prop.getProperty("url"));
		} catch (Exception e) {
			System.out.println("Failrd to load URL: " + e.getMessage());
		}
	}

	@AfterMethod
	public synchronized void tearDown() {
		if (getDriver() != null) {
			try {
				getDriver().quit();
			} catch (Exception e) {
				System.out.println("Unable to quit the driver:" + e.getMessage());
			}
		}
		System.out.println("Webdriver instance is Closed");
//		driver=null;
//		actionDriver=null;
		driver.remove();
		actionDriver.remove();
//		ExtentManager.endTest(); ---- commented as this have benn implemented in testlistneres
	}

	// Driver getter & setter method
//	public WebDriver getDriver() {
//		return driver;
//	}
//
//	public void setDriver(WebDriver driver) {
//		this.driver = driver;
//
//	}
	
	public static WebDriver getDriver() {
		if(driver.get()==null) {
			System.out.println("Webdrivre is not initialized");
			throw new IllegalStateException("Webdriver is not initialized");
		}
		return driver.get();
	}
	
	public static ActionDriver getActionDriver() {
		if(actionDriver.get()==null) {
			System.out.println("actionDriver is not initialized");
			throw new IllegalStateException("actionDriver is not initialized");
		}
		return actionDriver.get();
	}

	// prop getter & setter method
	public static Properties getProp() {
		return prop;
	}
	public void setDriver(ThreadLocal<WebDriver> driver) {
		this.driver=driver;
	}

	// static wait for pause
	public void staticWait(int seconds) {
		LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(seconds));
	}

}
