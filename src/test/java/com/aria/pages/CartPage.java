package com.aria.pages;

import com.aria.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CartPage extends BasePage {

    //I N S T A N C E  V A R I A B L E S
    //private WebDriver driver;
    //private WebDriverWait wait;

    //L O C A T O R S
    private final By cartContainer = By.id("cart_contents_container");
    private final By cartItems = By.className("cart_item");
    private final By checkout = By.id("checkout");

    //C O N S T R U C T O R
    public CartPage(WebDriver driver){

        super(driver);
       // this.driver = driver;
        // this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public boolean cartContainerIsDisplayed(){
        return isDisplayed(cartContainer);
    }

    public int getItemCount(){

        return driver.findElements(cartItems).size();
    }



    public void clickCheckout(){
        click(checkout);
        //System.out.println("URL after checkout click: " + driver.getCurrentUrl());
        waitForUrlContains("checkout-step-one");
    }



}
