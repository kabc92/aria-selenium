package com.aria.tests;

import com.aria.base.BaseTest;
import com.aria.config.ConfigReader;
import com.aria.pages.*;
import com.aria.utils.PageManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.qameta.allure.*;

@Epic("E2E Purchase Flow")
@Feature("Checkout")
public class E2ETestWithPageManager extends BaseTest {

    //I N S T A N C E  V A R I A B L E S
    private PageManager pages;

    @BeforeMethod
    public void setupPages() {
        pages = new PageManager(driver);
        driver.get(ConfigReader.get("baseUrl"));//driver.get("https://www.saucedemo.com");
    }

    @Test
    @Story("Complete Purchase")
    @Description("User logs in, adds item to cart, and completes full checkout")
    @Severity(SeverityLevel.CRITICAL)
    public void e2e_loginAddToCartAndVerify() {

        // Step 1 — Login
        pages.getPage(LoginPage.class).login("standard_user", "secret_sauce");
        Assert.assertTrue(pages.getPage(InventoryPage.class).inventoryIsDisplayed(), "Inventory page did not load");

        // Step 2 — Add item to cart
        pages.getPage(InventoryPage.class).addItemToCart();
        Assert.assertEquals(pages.getPage(InventoryPage.class).getCartCount(), "1", "Cart count should be 1");

        // Step 3 — Go to cart
        pages.getPage(InventoryPage.class).goToCart();
        Assert.assertTrue(pages.getPage(CartPage.class).cartContainerIsDisplayed(), "Cart page did not load");
        Assert.assertEquals(pages.getPage(CartPage.class).getItemCount(), 1, "Cart should have 1 item");

        // Step 4 — Proceed to checkout step one
        pages.getPage(CartPage.class).clickCheckout();
        Assert.assertTrue(driver.getCurrentUrl().contains("checkout-step-one"), "Checkout page did not load");
        //System.out.println("Current URL: " + driver.getCurrentUrl());

        //Step5 - Proceed to Checkout step two
        Assert.assertTrue(pages.getPage(CheckoutPage.class).checkoutPageIsDisplayed(), "Checkout page did not load");
        pages.getPage(CheckoutPage.class).fillForm("Milo", "Barc", "75034");
        pages.getPage(CheckoutPage.class).clickContinue();
        Assert.assertTrue(driver.getCurrentUrl().contains("checkout-step-two"), "Order summary did not load");

        //Step 6 - Checkout Overview
        Assert.assertTrue(pages.getPage(CheckoutOverviewPage.class).checkoutOverviewIsDisplayed(), "Overview page did not load");
        pages.getPage(CheckoutOverviewPage.class).clickFinish();
        Assert.assertTrue(driver.getCurrentUrl().contains("checkout-complete."), "Checkout was not completed, something went wrong");

    }
}
