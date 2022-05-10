package com.automation.framework.uicontrols;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.*;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebApTool extends RemoteWebDriver {

    private static final Logger logger = LogManager.getLogger("WebApTool");
    public WebDriver driver;
    private String url;
    Properties prop;

    public WebApTool(WebDriver driver) {
        this.driver = driver;
    }

    @Override
    public void get(String url) {

        logger.info("Launch URL " + url);
        this.driver.get(url);
        this.url = url;
        waitForPageLoad(100);
        pageLoadTime();
    }

    @Override
    public String getCurrentUrl() {
        logger.info("Get current url of page " + this.driver.getCurrentUrl());
        return this.driver.getCurrentUrl();
    }

    @Override
    public String getTitle() {
        logger.info("Get title of the current page " + this.driver.getTitle());
        return this.driver.getTitle();
    }

    @Override
    public List<WebElement> findElements(By by) {
        logger.info("Find elements for by object " + by.toString());

        FluentWait<WebDriver> driverFluentWait = new FluentWait<>(this.driver).pollingEvery(Duration.ofMillis(10))
                .withTimeout(Duration.ofMillis(10))
                .ignoring(NoSuchElementException.class)
                .ignoring(ElementNotVisibleException.class)
                .ignoring(ElementNotSelectableException.class)
                .ignoring(StaleElementReferenceException.class)
                .ignoring(WebDriverException.class);

        List<WebElement> webElement = driverFluentWait.until(new Function<WebDriver, List<WebElement>>() {
            @Override
            public List<WebElement> apply(WebDriver webDriver) {
                List<WebElement> element = webDriver.findElements(by);

                if ((element != null) ||
                        (element.size() != 0)) {
                    return element;

                } else {

                    element = webDriver.findElements(by);
                    if ((element != null) ||
                            (element.size() != 0)) {
                        return element;
                    }

                }
                return null;
            }
        });

        return webElement;
    }

    @Override
    public WebElement findElement(By by){
        logger.info("Find element for by object " + by.toString());

        FluentWait<WebDriver> driverFluentWait = new FluentWait<>(this.driver).pollingEvery(Duration.ofMillis(10))
                .withTimeout(Duration.ofSeconds(80))
                .ignoring(NoSuchElementException.class)
                .ignoring(WebDriverException.class);

        WebElement webElement = driverFluentWait.until(new Function<WebDriver, WebElement>() {
            @Override
            public WebElement apply(WebDriver webDriver) {
                WebElement element = webDriver.findElement(by);
                if (element.isDisplayed()) {
                	hightLight(element);
                    return element;
                } else {

                    element = webDriver.findElement(by);
                    if (element.isDisplayed()) {
                    	hightLight(element);
                        return element;
                    }
                }
                return null;
            }
        });
    	hightLight(webElement);
        return webElement;

    }

    @Override
    public String getPageSource() {
        String pagesource = this.driver.getPageSource();
        logger.info("Page Source value" + pagesource);
        return pagesource;
    }

    @Override
    public void close() {
        logger.info("Close webdriver instance");
        this.driver.close();
    }

    @Override
    public void quit() {
        logger.info("Quit webdriver instance");
        this.driver.quit();
    }

    @Override
    public Set<String> getWindowHandles() {
        logger.info("Get Window handles");
        return this.driver.getWindowHandles();
    }

    @Override
    public String getWindowHandle() {
        String windowHandle = this.driver.getWindowHandle();
        logger.info("Get Window handle " + windowHandle);
        return windowHandle;
    }


    @Override
    public Navigation navigate() {
        logger.info("Navigate using webdriver instance");

        if (!this.getCurrentUrl().equals(this.url)) {
            this.url = this.getCurrentUrl();
            waitForPageLoad(10);
            pageLoadTime();

        }


        return this.driver.navigate();
    }

    @Override
    public Options manage() {
        logger.info("Manage options using webdriver instance");
        return this.driver.manage();
    }

    /**
     * Wait for page load for timeout:-
     *
     * @param timeout
     */
    public void waitForPageLoad(int timeout) {
        logger.info(" Wait for page load timeout " + timeout + " seconds");
        this.driver.manage().timeouts().pageLoadTimeout(timeout, TimeUnit.SECONDS);
        new WebDriverWait(this.driver, timeout).until((ExpectedCondition<Boolean>) wd -> ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
        waitForJavaScript();

    }

    /**
     * Wait for JS by default for 10-seconds
     */
    public void waitForJavaScript() {
        waitForJavaScript(10);
    }

    /**
     * Wait until JS is ready for page:-s
     *
     * @param timeout
     */
    public void waitForJavaScript(int timeout) {
        logger.info(" Wait for javascript for timeout " + timeout + " seconds");

        WebDriverWait webDriverWait = new WebDriverWait(this.driver, timeout);
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) this.driver;

        // Wait for javascript to load:-
        ExpectedCondition<Boolean> jsLoad = driver -> ((JavascriptExecutor) this.driver).
                executeScript("return document.readyState").toString().equals("complete");
        boolean javascriptLoad =
                javascriptExecutor.executeScript("return document.readyState").toString().equals("complete");

        if (!javascriptLoad) {
            webDriverWait.until(jsLoad);
        }

    }

    /**
     * Wait for JQuery for 10s:-
     *
     * @throws Exception
     */
    public void waitForJquery() throws Exception {
        waitForJquery(10);
    }

    /**
     * Wait for jquery with specified timeout
     *
     * @param timeout
     */
    public void waitForJquery(int timeout) {
        try {
            logger.info(" Wait for jquery for timeout " + timeout + " seconds");

            WebDriverWait webDriverWait = new WebDriverWait(this.driver, timeout);
            JavascriptExecutor javascriptExecutor = (JavascriptExecutor) this.driver;

            ExpectedCondition<Boolean> jqueryLoad = webDriver1 -> ((Long) ((JavascriptExecutor) this.driver).executeScript("return jQuery.active") == 0);
            // Wait for javascript to load:-
            boolean jqueryReady = (Boolean) javascriptExecutor.executeScript("return jQuery.active==0");

            if (!jqueryReady) {
                webDriverWait.until(jqueryLoad);
            }
        } catch (Exception e) {

//            e.printStackTrace();
        }

    }

    /**
     * Time taken for page to load successfully:-
     *
     * @return
     */
    public String pageLoadTime() {
        Long loadtime = (Long) ((JavascriptExecutor) this.driver).executeScript(
                "return performance.timing.loadEventEnd - performance.timing.navigationStart;");
        logger.info("Page load time for url " + this.url + " is " + loadtime + " ms");
        return loadtime + "";
    }

    @Override
    public TargetLocator switchTo() {
        return new WebApTool.WebAppFramelocation();
    }
    
    
    public void moveToElement(WebElement element)
	{
		Actions actions = new Actions(driver);
		actions.moveToElement(element).perform();

	}
    
    public void waitFor(int seconds) {
        logger.info("Waiting For seconds " + seconds);
        //driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
		
		  try { Thread.sleep(seconds*1000); } catch (Exception e) {
		  logger.info("Exception while waiting " + e); }
		 
    }
    
    public void doubleClick(WebElement element)
   	{
   		Actions actions = new Actions(driver);
   		actions.doubleClick(element).perform();
   	}
    
    
    public WebElement findElement(By by, int timeout){
    	
    	
		return driver.findElement(by);
    	
    }

    protected class WebAppFramelocation extends RemoteWebDriver.RemoteTargetLocator {

        public WebDriver frame(String frameName) {
            driver.switchTo().frame(frameName);
            return driver;
        }

    }
    public void setAttribute(WebElement element, String attName, String attValue) {
    	JavascriptExecutor js = (JavascriptExecutor) driver;

    	js.executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);", 
                element, attName, attValue);
    }
    
    public void hightLight(WebElement ele)
    {
    	JavascriptExecutor js = (JavascriptExecutor) driver;
    	js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", ele);
    }
    
    public void scrollElementToView(WebElement element)
    {
    	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);

    }

	public boolean isAlphaNumeric(String text) {
		 String regex = "^(?=.*[a-zA-Z])(?=.*[0-9])[A-Za-z0-9]+$";
		 
	        Pattern p = Pattern.compile(regex);
	        if (text.isEmpty()) {
	            return false;
	        }
	        Matcher m = p.matcher(text);
	        return m.matches();
		
	}
    
	public boolean isElementPresent(By by)
	{
		try {
			driver.findElement(by);
			return true;
		} catch (org.openqa.selenium.NoSuchElementException e) {
			return false;
		}

	}
	
	public String getProperty(String key) {
		/*
		 * FileReader reader = new
		 * FileReader("./src/main/resources/TestData.properties");
		 * 
		 * Properties prop = new Properties(); prop.load(reader);
		 */
		return prop.getProperty(key);
	}

	public void getProperties() throws IOException {
		FileReader reader = new FileReader("./src/main/resources/TestData.properties");
		prop = new Properties();
		prop.load(reader);
		
	}
	
	public void setProperty(String property, By elementBy)	
	{
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement element = driver.findElement(elementBy);
		js.executeScript(property, element);
	}

	public void clickByJavaScript(By by) {
		try {
		     driver.findElement(by).click();
		  } catch (Exception e) {
		     JavascriptExecutor executor = (JavascriptExecutor) driver;
		     executor.executeScript("arguments[0].click();", driver.findElement(by));
		  }
		
	}

	public void switchToAlertandAccept() {
		driver.switchTo().alert().accept();
		
	}
	public void switchToAlertandDismiss() {
		driver.switchTo().alert().dismiss();
		
	}
	
	public void switchToIframe(String nameOrID) {
		driver.switchTo().frame(nameOrID);
	}
	public void switchToDefaultContent()
	{
		driver.switchTo().defaultContent();
	}
	
	public void waitUntilClickable(WebElement element)
	{
		new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(element));
	}
	public boolean isElementVisible(By by)
	{
		try {
			WebElement ele = driver.findElement(by);
			return ele.isDisplayed();
		} catch (org.openqa.selenium.NoSuchElementException e) {
			return false;
		}

	}
	public void switchToTab(ArrayList<String> tabs, int tabToSwitch)
	{
		driver.switchTo().window(tabs.get(tabToSwitch));
	}

}
