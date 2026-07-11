package com.aria.base;

import com.aria.utils.ScreenshotUtil;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;
import java.util.Map;

public class BaseTest {

        protected WebDriver driver; //in order to use this among child classes

        @BeforeMethod
        public void setUp() {
            WebDriverManager.chromedriver().setup();

            ChromeOptions options = new ChromeOptions(); //ChromeOptions allows us to customize Chrome browser behavior before launching it
            options.addArguments("--incognito"); // Launch Chrome in incognito mode
            options.addArguments("--password-store=basic"); // Tell Chrome not to use the system password store
            options.addArguments("--disable-save-password-bubble"); // Disable the "Save password" bubble that appears after login forms
            options.setExperimentalOption("prefs", Map.of(    // Disable Chrome's password manager entirely via browser preferences
                    "credentials_enable_service", false,     // credentials_enable_service → turns off the credential saving service
                    "profile.password_manager_enabled", false     // password_manager_enabled   → turns off the password manager UI
            ));

            // Launch Chrome with all the options configured above
            driver = new ChromeDriver(options);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); //implicitWait - GLOBAL PARA CADA findElement()
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


