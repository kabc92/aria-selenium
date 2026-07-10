package com.aria.tests;

import com.aria.base.BasePage;
import com.aria.base.BaseTest;
import com.aria.pages.InventoryPage;
import com.aria.pages.LoginPage;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class LoginTest extends BaseTest {

    // El flujo de navegación pertenece a la aplicación, no al test.
    //I N S T A N C E  V A R I A B L E
    private LoginPage loginPage;
    private InventoryPage inventoryPage;

   @BeforeMethod
   public void setUpPage(){
       loginPage = new LoginPage(driver);
       inventoryPage = new InventoryPage(driver);
       driver.get("https://www.saucedemo.com");
   }


   // @Test
    public void login_HappyPath(){

        loginPage.login("standard_user", "secret_sauce");

        String currentUrl = driver.getCurrentUrl();
        Assert.assertEquals(currentUrl, "https://www.saucedemo.com/inventory.html");
    }

    @Test
    public void login_verifyInventoryLoads(){
       loginPage.login("standard_user","secret_sauce");

       Assert.assertTrue(inventoryPage.inventoryIsDisplayed());
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
