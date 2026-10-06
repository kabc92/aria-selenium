package com.aria.base;

import com.aria.config.ConfigReader;
import com.aria.driver.DriverFactory;
import com.aria.driver.DriverManager;
import com.aria.utils.ScreenshotUtil;
import org.openqa.selenium.WebDriver;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;


/*
Originally my tests relied on an inherited WebDriver field from BaseTest.
To support parallel execution safely, I moved driver access to a DriverManager
backed by ThreadLocal, so each execution thread can retrieve its own WebDriver instance.
 */
public class BaseTest {

        //protected WebDriver driver; //In order to use this among child classes
        @BeforeMethod
        public void setUp() {

            //Validate/load configuration before creating the WebDriver
            ConfigReader.get("baseUrl");

            //browser = property im looking for
            //chrome  = default value if the property does not exist
            String browser = System.getProperty("browser","chrome");

            //Creates a WebDriver and temporarily stores its reference in
            // a variable called newDriver so that it can be passed to DriverManager
            WebDriver newDriver = DriverFactory.createDriver(browser);// CREATE

            //Stores the driver in ThreadLocal for the current thread
            DriverManager.setDriver(newDriver);//SET

            //Retrieves the driver
            DriverManager.getDriver().manage().window().maximize();//GET
        }

    @AfterMethod
    public void tearDown(ITestResult result) {//ITestResult permite consultar informacion del resultado de la ejecucion

        //Get the WebDriver associated with the CURRENT THREAD
        WebDriver currentDriver = DriverManager.getDriver();

        //Only perform cleanup if a WebDriver was successfully created
        if(currentDriver != null) {

            try{
                //if the test failed, try to capture a screenshot BEFORE closing the browser
                if (result.getStatus() == ITestResult.FAILURE) {
                    ScreenshotUtil.takeScreenshot(currentDriver, result.getName());
                }
            } finally {

                //Even if taking the screenshot fails, we still need to close the browser
                try{

                    currentDriver.quit(); //close browser

                } finally {
                    //Even if quit() fails, always remove the WebDriver reference
                    //from ThreadLocal to avoid leaving stale references behind
                    DriverManager.removeDriver(); //delete the driver reference to the current thread
                }
            }
        }
    }
}


