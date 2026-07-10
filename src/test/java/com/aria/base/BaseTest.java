package com.aria.base;

import com.aria.utils.ScreenshotUtil;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;

public class BaseTest {

        protected WebDriver driver; //in order to use this among child classes

        @BeforeMethod
        public void setUp() {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); //implicitWait - GLOBAL PARA CADA findElement()
            driver.manage().window().maximize();
        }

    @AfterMethod
    public void tearDown(ITestResult result) {
        // Screenshot automático si el test falla
        if (result.getStatus() == ITestResult.FAILURE) {
            ScreenshotUtil.takeScreenshot(driver, result.getName());
        }
        //if (driver != null) {
         //   driver.quit();
        //}
    }
}


