package com.aria.cucumber.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

    /*
    CucumberRunnerTest tells TestNG how to run Cucumber scenarios
    and acts like testng.xml but specifically for cucumber
     */

    @CucumberOptions(
    features = "src/test/resources/features" , //where to find .feature files

    glue = {"com.aria.cucumber"},//Where to find Step Definitions and Hooks, glue = the packages Cucumber scans to find @Given/@When/@Then/@Before/@After

    plugin = {
    //output plugi ns: pretty -> readable output in console
        //AllureCucumber7Jvm -> generates Allure report data after each scenario
        "pretty",
        "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
    },
    tags = "@smoke or @regression"
    )

    public class CucumberRunnerTest extends AbstractTestNGCucumberTests{
        /*
        This one goes empty, AbstractTestNGCucumberTests provides everything
        it bridges Cucumber with TestNG so scenarios run as TestNG tests
         */
    }


