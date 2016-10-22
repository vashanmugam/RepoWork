package com.vmetry.pageObjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class Login_PO {

	// UAN
		@FindBy(how = How.ID, using = "username")
		public WebElement uan_textbox;

		public void enter_uantextbox(String uanval) {
			uan_textbox.sendKeys(uanval);
		}

		// Password
		@FindBy(how = How.ID, using = "password")
		public WebElement password;

		public void enter_Password(String pass) {
			password.sendKeys(pass);
		}

		// Login button
		@FindBy(how = How.CSS, using = "input.inputStyle")
		public WebElement login;

		public void click_SignIn() {
			login.click();
		}

}
