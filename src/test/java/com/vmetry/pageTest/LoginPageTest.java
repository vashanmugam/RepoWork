package com.vmetry.pageTest;

import java.io.IOException;

import org.openqa.selenium.support.PageFactory;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.vmetry.TestBase.Intialization;
import com.vmetry.TestUtil.UtilClass;
import com.vmetry.pageObjects.HomePage_PO;
import com.vmetry.pageObjects.Login_PO;

import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;

	//@Listeners({ ConfigurationListener.class, MethodListener.class, ATUReportsListener.class })
	public class LoginPageTest extends Intialization {

		public static Login_PO lp = null;
		public static HomePage_PO hp = null;
		
		

		@BeforeClass
		public void bfrcls() throws IOException {
			
			Start();
			lg=LoggerFactory.getLogger(LoginPageTest.class);	
			lp = PageFactory.initElements(wd, Login_PO.class);
			hp = PageFactory.initElements(wd, HomePage_PO.class);
			lg.info("Before Class Executed");

		}

		@Test(dataProvider = "sdfkjshdfksd")
		public void testLoginPage(String TCID, String uan, String pass, String type)
				throws InterruptedException, IOException {
				Thread.sleep(2000);
				lp.enter_uantextbox(uan);
				lp.enter_Password(pass);
				lp.click_SignIn();
				Thread.sleep(2000);
				UtilClass.alertchkandaccept();
				if (type.equalsIgnoreCase("Valid")) {
					System.out.println(hp.verifyDisplayName());
					if (("Welcome Vasanth Shanmugam" + "\n" + "UAN  100411794217").equalsIgnoreCase(hp.verifyDisplayName())) {
						System.out.println("Pass");
						map.put(TCID, "Passed");
						lg.info("Logged result"+TCID+"- Passed");
						hp.click_Logout();
						UtilClass.alertchkandaccept();
					} else {
						System.out.println("Fail");
						map.put(TCID, "Failed");
						lg.info("Logged result"+TCID+"- Failed");
						hp.click_Logout();
						Thread.sleep(1000);
						UtilClass.alertchkandaccept();
						SoftAssert sf=new SoftAssert();
						sf.assertTrue(false);
						sf.assertAll();
					}

				} else if (type.equalsIgnoreCase("InValid")) {
					Thread.sleep(2000);
					if (wd.getCurrentUrl().equalsIgnoreCase(locator.getProperty("homepageURL"))) {
						map.put(TCID, "Failed");
						lg.info("Logged result"+TCID+"- Failed");
					} else {

						map.put(TCID, "Passed");
						lg.info("Logged result"+TCID+"- Passed");
					}

				}
				

			} 

		

		@DataProvider(name = "sdfkjshdfksd")
		public static Object[][] exceldata() throws IOException {

			if (UtilClass.runModeVerify("LoginPage") == true) {
				Object[][] data = UtilClass.getExcelData("LoginPage");

				return data;

			}
			return null;

		}

		@AfterClass
		public void afrcls() throws IOException {

			UtilClass.writeExcelFile("LoginPage");
			lg.info("Excel File Written and After class Executed");
			wd.quit();

		}

	}


