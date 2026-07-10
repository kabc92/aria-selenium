package com.aria.pages;

import com.aria.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class InventoryPage extends BasePage {

    //I N S T A N C E  V A R I A B L E S
    //private WebDriver driver;
    //private WebDriverWait wait;

    //L O C A T O R S
    private final By inventoryContainer = By.id("inventory_container");
    private final By cartIcon = By.className("shopping_cart_link");
    private final By addToCartBtn = By.className("btn_primary");
    private final By cartBadge = By.className("shopping_cart_badge"); //It displays a badge on the cart Icon once customer adds items

    //C O N S T R U C T O R
    public InventoryPage(WebDriver driver){

        super(driver);
        //this.driver = driver;
        //this.wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Wait 10 seconds until page loads

    }

    //M E T H O D S
    public boolean inventoryIsDisplayed(){
        return isDisplayed(inventoryContainer); //wait.until(ExpectedConditions.visibilityOfElementLocated(inventoryContainer)).isDisplayed();
    }

    public void addItemToCart(){
        click(addToCartBtn);
    }

    public String getCartCount(){
        return getText(cartBadge); //driver.findElement(cartBadge).getText();
    }

    public void goToCart(){
        click(cartIcon);
    }

}
