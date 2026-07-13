package com.aria.pages;

import com.aria.base.BasePage;
import com.aria.base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutPage extends BasePage {

    //LOCATORS
    private final By checkoutPage = By.cssSelector(".checkout_info");
    private final By firstName = By.cssSelector("[data-test='firstName']");
    private final By lastName = By.cssSelector("[data-test='lastName']");
    private final By zipCode = By.cssSelector("[data-test='postalCode']");//postalCode
    private final By continueBtn = By.id("continue");



    //CONSTRUCTOR
    public CheckoutPage(WebDriver driver){
        super(driver);
    }

    /*
Método fillForm(String firstName, String lastName, String zip)
Método clickContinue()
Método isLoaded() — verifica que la página de checkout cargó
     */

    public boolean checkoutPageIsDisplayed(){
        return isDisplayed(checkoutPage);

    }

    public void fillForm(String name, String surname, String zip){

        type(firstName,name);
        type(lastName,surname);
        type(zipCode,zip);
    }

    public void clickContinue(){

        click(continueBtn);
    }



}
