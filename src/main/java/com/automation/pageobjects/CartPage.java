package com.automation.pageobjects;


import org.openqa.selenium.By;

import com.automation.framework.uicontrols.WebApTool;


public class CartPage extends BasePage {

	public CartPage(WebApTool webApTool) {
		super(webApTool);
	}

	By btn_cart = By.id("nav-cart");
	By txt_ProductName ;
	By btn_delete = By.xpath("(//input[@data-action='delete'])[1]");
	
	public boolean isProducAddedToCart(String searchedProduct) {
		webApTool.findElement(btn_cart).click();
		txt_ProductName = By.xpath("(//span[contains(text(),'"+searchedProduct+"')])[2]");
		return webApTool.isElementPresent(txt_ProductName);
	}

	public void deleteProductFromCart(String searchedProduct) {
		webApTool.findElement(btn_delete).click();

	}

}
