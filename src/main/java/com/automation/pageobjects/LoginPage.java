package com.automation.pageobjects;

import org.openqa.selenium.By;


import com.automation.framework.report.Reporting;
import com.automation.framework.uicontrols.WebApTool;
import com.aventstack.extentreports.Status;

public class LoginPage extends BasePage {

	public LoginPage(WebApTool webApTool) {
		super(webApTool);
	}
	
	By txt_user = By.xpath("//input[@id='ap_email']");
	By btn_continue = By.xpath("//input[@id='continue']");
	By txt_pass = By.xpath("//input[@id='ap_password']");
	By btn_signIn = By.xpath("//input[@id='signInSubmit']");
	
	By lbl_loginName = By.xpath("//span[@id='nav-link-accountList-nav-line-1']");
	By lbl_SignIn = By.xpath("//span[text()='Hello, Sign in']");

	/**
	 * Login into Amazon:-
	 */

	public String login(String userName, String password) throws Exception {

		Reporting.getLogger().log(Status.INFO, "UserName " + userName);
		webApTool.findElement(lbl_SignIn).click();
		webApTool.findElement(txt_user).clear();
		webApTool.findElement(txt_user).sendKeys(userName);
		webApTool.findElement(btn_continue).click();

		webApTool.findElement(txt_pass).clear();;
		webApTool.findElement(txt_pass).sendKeys(password);
		webApTool.findElement(btn_signIn).click();

		String loginName = webApTool.findElement(lbl_loginName).getText();

		Reporting.getLogger().log(Status.INFO, "Logged In with name " + loginName.split(",")[1].trim());
		return loginName.split(",")[1].trim();
	}

}
