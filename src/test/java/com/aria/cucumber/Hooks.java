package com.aria.cucumber;

import com.aria.config.ConfigReader;
import com.aria.driver.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.ByteArrayInputStream;

// Cucumber equivalent of BaseTest — runs setup/teardown around each Scenario
// Uses PicoContainer: TestContext is injected via constructor
// so Hooks and Steps share the SAME driver instance
public class Hooks {

    private final TestContext context; // single source of truth for the driver

    public Hooks(TestContext context) {
        this.context = context; // PicoContainer injects TestContext automatically
    }

    @Before
    public void setUp() {

        //browser = property im looking for
        //chrome  = default value if the property does not exist
        String browser = System.getProperty("browser", "chrome");
        WebDriver driver = DriverFactory.createDriver(browser);//Create the driver here
        context.setDriver(driver); // store in context so Steps can access it

        //driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get(ConfigReader.get("baseUrl"));// starting URL for every scenario
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