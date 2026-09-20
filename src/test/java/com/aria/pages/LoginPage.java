package com.aria.pages;

import com.aria.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class LoginPage extends BasePage {

    //Instance variable only accessible from this class
    //private WebDriver driver;

    //L O C A T O R S - Los elementos de la pagina
    private final By usernameField = By.id("user-name");
    private final By passwordField = By.id("password");
    private final By loginButton = By.id("login-button");
    private final By errorMessage = By.cssSelector("[data-test= 'error']");
   // private final By inventoryContainer = By.id("inventory_container");

    //CONSTRUCTOR - PageManager creates LoginPage and passes the WebDriver here
                //LoginPage passes that SAME driver to BasePage through super(driver)
                //BasePage stores it, so LoginPage can use the inherited driver
    public LoginPage(WebDriver driver){
        super(driver);
    }

    //A C T I O N S - Lo que puedes hacer en esta pagina
    public void login(String username, String password)
    {
        type(usernameField, username);
        type(passwordField, password);
        click(loginButton);
    }

    public String getTitle(){

        return driver.getTitle();
    }

    public String getErrorMessage(){
        return getText(errorMessage);
    }
}
