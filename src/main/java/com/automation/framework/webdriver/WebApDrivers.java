package com.automation.framework.webdriver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class WebApDrivers {

    /**
     * Get Webdriver instance:-
     *
     * @return
     */
    public WebDriver getWebDriver(String browser) {
        WebDriver driver = null;
        if (browser.equalsIgnoreCase("CHROME")) {

            // Set Driver path:-
            System.setProperty("webdriver.chrome.driver", "./src/main/resources/drivers/chromedriver");

            // Set Custom download file path for any file that needs to be download online:-
            String downloadFilePath = System.getProperty("user.dir") + "/";
            Map<String, Object> chromePrefs = new HashMap<>();
            chromePrefs.put("profile.default_content_settings.popups", 0);
            chromePrefs.put("download.default_directory", downloadFilePath);
            chromePrefs.put("profile.default_content_setting_values.notifications",2);
            chromePrefs.put("profile.default_content_setting_values_location",2);

            // ChromeOptions
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.setExperimentalOption("prefs", chromePrefs);
            chromeOptions.addArguments("test-type");
            chromeOptions.addArguments("enable-strict-powerful-feature-restrictions");
            chromeOptions.addArguments("--disable-geolocation");

            // Logging Preferences:-
            LoggingPreferences loggingPreferences = new LoggingPreferences();
            loggingPreferences.enable(LogType.BROWSER, Level.ALL);


            // Desired Capabilities:-
            DesiredCapabilities desiredCapabilities = DesiredCapabilities.chrome();
            desiredCapabilities.setJavascriptEnabled(true);
            desiredCapabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, "true");
            desiredCapabilities.setCapability(CapabilityType.LOGGING_PREFS, loggingPreferences);

            chromeOptions.merge(desiredCapabilities);

            driver = new ChromeDriver(chromeOptions);
        } else if (browser.equalsIgnoreCase("SAFARI")) {

            // Desired Capabilities:-
            DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
            desiredCapabilities.setJavascriptEnabled(true);
            desiredCapabilities.setCapability("handlesAlerts", true);
            desiredCapabilities.setCapability("cssSelectorsEnabled", true);
            desiredCapabilities.setCapability("automaticInspection", true);
            desiredCapabilities.setCapability("automaticProfiling", true);

            // Logging Preferences:-
            LoggingPreferences loggingPreferences = new LoggingPreferences();
            loggingPreferences.enable(LogType.BROWSER, Level.ALL);
            loggingPreferences.enable(LogType.PERFORMANCE, Level.ALL);
            desiredCapabilities.setCapability(CapabilityType.LOGGING_PREFS, loggingPreferences);

            // SafariOptions :-
            SafariOptions safariOptions = new SafariOptions();
            safariOptions.merge(desiredCapabilities);

            driver = new SafariDriver(safariOptions);
        }

        return driver;
    }
}
