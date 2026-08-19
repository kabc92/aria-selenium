package com.aria.cucumber;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.io.ByteArrayInputStream;
import java.time.Duration;
import java.util.Map;

// Cucumber equivalent of BaseTest — runs setup/teardown around each Scenario
// Uses PicoContainer: TestContext is injected via constructor
// so Hooks and Steps share the SAME driver instance (thread-safe for parallel execution)
public class Hooks {

    private final TestContext context; // single source of truth for the driver

    public Hooks(TestContext context) {
        this.context = context; // PicoContainer injects TestContext automatically
    }

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();

        // Prevent Chrome password manager popups during test execution
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito", "--password-store=basic", "--disable-save-password-bubble");
        options.setExperimentalOption("prefs", Map.of(
                "credentials_enable_service", false,
                "profile.password_manager_enabled", false
        ));

        WebDriver driver = new ChromeDriver(options);
        context.setDriver(driver); // store in context so Steps can access it

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com"); // starting URL for every scenario
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            // Capture screenshot as bytes and attach to Allure report
            // Cast needed: TakesScreenshot is not declared in WebDriver interface
            byte[] screenshot = ((TakesScreenshot) context.getDriver()).getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment("Screenshot - " + scenario.getName() , new ByteArrayInputStream(screenshot));
        }
        if (context.getDriver() != null) {
            context.getDriver().quit();
        }
    }
}