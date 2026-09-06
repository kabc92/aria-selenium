package com.aria.base;

import com.aria.driver.DriverFactory;
import com.aria.utils.ScreenshotUtil;
import org.openqa.selenium.WebDriver;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;


public class BaseTest {

        protected WebDriver driver; //In order to use this among child classes

        @BeforeMethod
        public void setUp() {

            //browser = property im looking for
            //chrome  = default value if the property does not exist
            String browser = System.getProperty("browser","chrome");
            driver = DriverFactory.createDriver(browser);

            driver.manage().window().maximize();
        }

    @AfterMethod
    public void tearDown(ITestResult result) {
        // Screenshot automático si el test falla
        if (result.getStatus() == ITestResult.FAILURE) {
            ScreenshotUtil.takeScreenshot(driver, result.getName());
        }
        if (driver != null) {
           driver.quit();
        }
    }
}


