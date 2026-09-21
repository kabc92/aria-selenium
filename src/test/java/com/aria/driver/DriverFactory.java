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
        //E D G E  B R O W S E R
        if(browser.equalsIgnoreCase("edge")){
            WebDriverManager.edgedriver().setup();
            return new EdgeDriver();
        }
        //C H R O M E  B R O W S E R
        if(browser.equalsIgnoreCase("chrome")){
            WebDriverManager.chromedriver().setup();

            ChromeOptions options = new ChromeOptions();

            //Reads the "headless" system property to decide how Chrome should run
            //Defaults to false for LOCAL execution; CI can enable it with -Dheadless=true
            boolean headless = Boolean.parseBoolean(System.getProperty("headless", "false"));

            //Adds Chrome configuration required for headless execution in CI
            if(headless) {
                options.addArguments(
                        "--headless=new",
                        "--window-size=1920,1080",
                        "--no-sandbox",
                        "--disable-dev-shm-usage"
                );
            }

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
