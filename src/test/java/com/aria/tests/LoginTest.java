package com.aria.tests;

import com.aria.base.BaseTest;
import com.aria.pages.LoginPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class LoginTest extends BaseTest {

    //I N S T A N C E  V A R I A B L E
    private LoginPage loginPage;

   @BeforeMethod
   public void setUpPage(){
       loginPage = new LoginPage(driver);
       driver.get("https://www.saucedemo.com");
   }


    @Test
    public void login_HappyPath(){

        loginPage.login("standard_user", "secret_sauce");

        String currentUrl = driver.getCurrentUrl();
        Assert.assertEquals(currentUrl, "https://www.saucedemo.com/inventory.html");
    }

    @Test
    public void login_verifyInventoryLoads(){
       loginPage.login("standard_user","secret_sauce");

       Assert.assertTrue(loginPage.isInventoryLoaded());
    }

   @Test
    public void login_invalidCredentials(){

        loginPage.login("invalid_user", "wrong_password");

        Assert.assertEquals(loginPage.getErrorMessage(), "Epic sadface: Username and password do not match any user in this service");
    }

   @Test
    public void login_missingPassword(){

        loginPage.login("invalid_user", "");

        Assert.assertEquals(loginPage.getErrorMessage(), "Epic sadface: Password is required");//Actual/expected

    }

   @Test
    public void login_missingUsername(){

        loginPage.login("", "invalid-password");

        Assert.assertEquals(loginPage.getErrorMessage(), "Epic sadface: Username is required");
    }

}
