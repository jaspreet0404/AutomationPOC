package com.automation.pageobjects;

import java.util.ArrayList;

import org.openqa.selenium.By;


import com.automation.framework.report.Reporting;
import com.automation.framework.uicontrols.WebApTool;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.Keys;

public class HomePage extends BasePage {

	public HomePage(WebApTool webApTool) {
		super(webApTool);
	}

	By drpDownCustSelect = By.id("CustomerSelect");
	By txt_SearchBox = By.xpath("//input[@id='twotabsearchtextbox']");
	By txt_SearchResult1 = By.xpath("(//div[text()='Deal of the Day'])[1]");
	By txt_ProductName;
	By btn_addToCart = By.id("add-to-cart-button");
	By lbl_addedToCart = By.xpath("//span[contains(text(),'Added to Cart')]");
	String product = null;

	public String searchProduct(String productName) {
		Reporting.getLogger().log(Status.INFO, "Search product as " + productName);

		webApTool.findElement(txt_SearchBox).sendKeys(productName);
		webApTool.findElement(txt_SearchBox).sendKeys(Keys.ENTER);
		txt_ProductName = By.xpath("(//span[contains(text(),'" + productName + "')])[2]");
		product = webApTool.findElement(txt_ProductName).getText();
		Reporting.getLogger().log(Status.INFO, "Search result product title " + product);

		return product;
	}

	public boolean addProduct(String productName) {
		Reporting.getLogger().log(Status.INFO, "Clicked on product ");

		webApTool.findElement(txt_ProductName).click();
		ArrayList<String> tabs2 = new ArrayList<String>(webApTool.getWindowHandles());
		webApTool.switchToTab(tabs2, 1);
		webApTool.findElement(btn_addToCart).click();

		Reporting.getLogger().log(Status.INFO, "Add to Cart button clicked");

		return webApTool.isElementPresent(lbl_addedToCart);
	}

}
