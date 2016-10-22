package com.vmetry.pageObjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class HomePage_PO {
	
	@FindBy(how = How.LINK_TEXT, using = "LOGOUT")
	public WebElement logout;

	public void click_Logout() {
		logout.click();
	}

	@FindBy(how = How.CSS, using = "span.menubartext>b")
	public WebElement Usertext;

	public String verifyDisplayName() {
		String dname = Usertext.getText();
		return dname;
	}


}
