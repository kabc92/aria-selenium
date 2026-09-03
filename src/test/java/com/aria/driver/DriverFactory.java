package com.aria.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.Map;

public class DriverFactory {

    public static WebDriver createDriver(String browser){


        //F I R E F O X  B R O W S E R
        if(browser.equalsIgnoreCase("firefox")){
            WebDriverManager.firefoxdriver().setup();
            return new FirefoxDriver();
        }

        if(browser.equalsIgnoreCase("edge")){
            WebDriverManager.edgedriver().setup();
            return new EdgeDriver();
        }

        if(browser.equalsIgnoreCase("chrome")){
            WebDriverManager.chromedriver().setup();

            ChromeOptions options = new ChromeOptions();
            options.addArguments(
                    "--incognito",
                    "--password-store=basic",
                    "--disable-save-password-bubble"
                    );
            options.setExperimentalOption("prefs", Map.of(
                    "credentials_enable_service", false,
                    "profile.password_manager_enabled", false
            ));

            return new ChromeDriver(options);
        }

        throw new IllegalArgumentException("Unsupported browser: " + browser);
    }
}
