package com.automation.pageobjects;

import com.automation.framework.report.Reporting;
import com.automation.framework.uicontrols.WebApTool;
import com.aventstack.extentreports.Status;


public class BasePage {

    protected WebApTool webApTool;

    public BasePage(WebApTool webApTool) {
        this.webApTool = webApTool;
    }

    public void launchURL(String url) {
        Reporting.getLogger().log(Status.INFO, "Launch URL " + url);
        webApTool.get(url);
        webApTool.manage().window().maximize();
    }
    
   
}
