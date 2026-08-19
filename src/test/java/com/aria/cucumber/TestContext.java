package com.aria.cucumber;

import org.openqa.selenium.WebDriver;

public class TestContext {

    //I N S T A N C E  V A R I A B L E S
    private WebDriver driver;

    //M E T H O D S
    public void setDriver(WebDriver driver){
        this.driver = driver;
    }

    public WebDriver getDriver(){
         return driver;
    }
}
