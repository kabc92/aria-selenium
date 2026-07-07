package com.aria.tests;

import com.aria.base.BaseTest;
import com.aria.pages.LoginPage;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {




    @Test
    public void openURL(){

        driver.get("https://www.saucedemo.com");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");

        System.out.println(loginPage.getTitle());
    }
}
