package com.automation.framework.report;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import java.util.HashMap;
import java.util.Map;


public class Reporting {

	static Map extentTestMap = new HashMap();
	static ExtentReports extent;

	public static void setExtenReports(ExtentReports extentReports) {

		extent = extentReports;
	}

	public static synchronized ExtentTest getLogger() {
		return (ExtentTest) extentTestMap.get((int) (long) (Thread.currentThread().getId()));
	}

	public static synchronized void endTest() {
		extentTestMap.remove((ExtentTest) extentTestMap.get((int) (long) (Thread.currentThread().getId())));
	}

	public static synchronized ExtentTest startTest(String testName, String desc) {
		ExtentTest test = extent.createTest(testName, desc);
		extentTestMap.put((int) (long) (Thread.currentThread().getId()), test);
		return test;
	}
}
