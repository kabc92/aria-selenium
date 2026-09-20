package com.aria.pages;

import com.aria.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class InventoryPage extends BasePage {

    //L O C A T O R S
    private final By inventoryContainer = By.id("inventory_container");
    private final By cartIcon = By.className("shopping_cart_link");
    private final By addToCartBtn = By.className("btn_primary");
    private final By cartBadge = By.className("shopping_cart_badge"); //It displays a badge on the cart Icon once customer adds items

    //C O N S T R U C T O R
    public InventoryPage(WebDriver driver){

        /*
        PageManager creates InventoryPage and passes its WebDriver to this constructor
        InventoryPage passes that SAME driver to BasePage through super(driver)
        BasePage stores it, so InventoryPage can use the inherited driver
        and BasePage's reusable Selenium methods
         */
        super(driver);
    }

    //M E T H O D S
    public boolean inventoryIsDisplayed(){
        return isDisplayed(inventoryContainer);
    }

    public void addItemToCart(){
        click(addToCartBtn);
    }

    public String getCartCount(){
        return getText(cartBadge);
    }

    public void goToCart(){
        click(cartIcon);
    }

}
