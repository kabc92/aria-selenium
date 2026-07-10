package com.aria.tests;

import com.aria.base.BaseTest;
import com.aria.pages.CartPage;
import com.aria.pages.InventoryPage;
import com.aria.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class E2ETest extends BaseTest {

    private LoginPage loginPage;
    private InventoryPage inventoryPage;
    private CartPage cartPage;

    @BeforeMethod
    public void setUpPages() {

        loginPage = new LoginPage(driver);
        inventoryPage = new InventoryPage(driver);
        cartPage = new CartPage(driver);

        driver.get("https://www.saucedemo.com");
    }

    @Test
    public void e2e_loginAddToCartAndVerify() {

        // Step 1 — Login
        loginPage.login("standard_user", "secret_sauce");
        Assert.assertTrue(inventoryPage.inventoryIsDisplayed(), "Inventory page did not load");

        // Step 2 — Add item to cart
        inventoryPage.addItemToCart();
        Assert.assertEquals(inventoryPage.getCartCount(), "1", "Cart count should be 1");

        // Step 3 — Go to cart
        inventoryPage.goToCart();
        Assert.assertTrue(cartPage.cartContainerIsDisplayed(), "Cart page did not load");
        Assert.assertEquals(cartPage.getItemCount(), 1, "Cart should have 1 item");

        // Step 4 — Proceed to checkout
        cartPage.clickCheckout();
        //Assert.assertTrue(driver.getCurrentUrl().contains("checkout-step-one"), "Checkout page did not load");
        System.out.println("Current URL: " + driver.getCurrentUrl());

        //Assert.assertTrue(driver.getCurrentUrl().contains("checkout-step-one"), "checkout-step-one");



    }
}