package com.automation.framework.webTests;


import org.testng.Assert;
import org.testng.annotations.Test;

import com.automation.framework.basetest.BaseClass;
import com.automation.framework.report.Reporting;
import com.automation.pageobjects.LoginPage;
import com.aventstack.extentreports.Status;
import com.automation.pageobjects.CartPage;
import com.automation.pageobjects.HomePage;


public class SanityTests extends BaseClass {
	
	LoginPage login;
	HomePage homePage;
	CartPage cartPage;
	

	@Test
	public void addProduct() throws Exception {

		login = new LoginPage(uiTestTools.get());
		homePage = new HomePage(uiTestTools.get());
		cartPage = new CartPage(uiTestTools.get());
		login.launchURL(prop.getProperty("BaseURL"));
		String loginName = login.login(prop.getProperty("Username"), prop.getProperty("Password"));
		Assert.assertTrue(loginName.equalsIgnoreCase(prop.getProperty("LoginName")));
		
		String searchedProduct = homePage.searchProduct(prop.getProperty("ProductName"));
		Assert.assertTrue(searchedProduct.contains(prop.getProperty("ProductName")));
		
		Assert.assertTrue(homePage.addProduct(searchedProduct));

		Assert.assertTrue(cartPage.isProducAddedToCart(searchedProduct));
		Reporting.getLogger().log(Status.INFO, "Product Added to Cart is present in Cart");

		cartPage.deleteProductFromCart(searchedProduct);
		Reporting.getLogger().log(Status.INFO, "Product deleted from Cart");


	}

	


}
