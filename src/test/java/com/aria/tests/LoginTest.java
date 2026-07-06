package com.aria.tests;

import com.aria.base.BaseTest;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test
    public void openURL(){
        driver.navigate().to("https://www.saucedemo.com");
        driver.getTitle();

    }
}
