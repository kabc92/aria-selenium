package com.aria.pages;

import com.aria.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutOverviewPage extends BasePage {

    //LOCATORS
    private final By checkoutSummary = By.id("checkout_summary_container");
    private final By finishBtn = By.id("finish");


    //CONSTRUCTOR
    public CheckoutOverviewPage (WebDriver driver){
        super(driver);
    }

    public boolean checkoutOverviewIsDisplayed(){
        return(isDisplayed(checkoutSummary));
    }

    public void clickFinish(){
        scrollToWebElement(finishBtn);
        click(finishBtn);
    }
}
