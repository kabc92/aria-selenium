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

            //Creates a WebDriver and temporarily stores its reference in a variable called newDriver so that it can be passed to DriverManager
            WebDriver newDriver = DriverFactory.createDriver(browser);// CREATE

            //Stores the driver in ThreadLocal for the current thread
            DriverManager.setDriver(newDriver);//SET

            //Retrieves the driver
            DriverManager.getDriver().manage().window().maximize();//GET
        }

    @AfterMethod
    public void tearDown(ITestResult result) {

        WebDriver currentDriver = DriverManager.getDriver();

        if(currentDriver != null) {
            // Screenshot automático si el test falla
            if (result.getStatus() == ITestResult.FAILURE) {
                ScreenshotUtil.takeScreenshot(currentDriver, result.getName());
            }
            currentDriver.quit();
            DriverManager.removeDriver();
        }
    }
}


