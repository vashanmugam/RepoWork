package com.vmetry.TestBase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import atu.testng.reports.ATUReports;

public class Intialization {
	
	public static WebDriver wd = null;
	public static Properties locator = null;
	public static FileInputStream fis = null;
	public static FileInputStream fisinput = null;
	public static FileOutputStream fos = null;
	public static File f = null;
	public static SimpleDateFormat dateFormat = null;
	public static Date dt = null;
	public static Map<String, String> map = null;
	public static boolean initialize = false;
	public static Logger lg=null;

	public static boolean Start() throws IOException {

		fis = new FileInputStream(
				System.getProperty("user.dir") + "\\src\\test\\resources\\com\\vmetry\\Locators\\config.properties");
		locator = new Properties();
		locator.load(fis);

		if (locator.getProperty("browser").equalsIgnoreCase("Firefox")) {
			wd = new FirefoxDriver();
		} else if (locator.getProperty("browser").equalsIgnoreCase("IE")) {
			System.setProperty("webdriver.ie.driver", "E:\\IEDriver\\IEDriverServer.exe");
			wd = new InternetExplorerDriver();
		} else {
			wd = new ChromeDriver();
		}

		wd.get(locator.getProperty("baseURL"));
		map = new HashMap<String, String>();

		dt = new Date();
		dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

//		System.setProperty("atu.reporter.config", System.getProperty("user.dir") + "\\lib\\atu.properties");
//		ATUReports.setWebDriver(wd);
//		ATUReports.indexPageDescription = "Functional ATU Reports";
//		
		PropertyConfigurator.configure(System.getProperty("user.dir")+"\\lib\\Log4j.properties");
		

		initialize = true;

		return initialize;

	}


}
