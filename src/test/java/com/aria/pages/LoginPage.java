package com.aria.pages;

import com.aria.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage extends BasePage {

    //Instance variable only accessible from this class
    //private WebDriver driver;

    //L O C A T O R S - Los elementos de la pagina
    private final By usernameField = By.id("user-name");
    private final By passwordField = By.id("password");
    private final By loginButton = By.id("login-button");
    private final By errorMessage = By.cssSelector("[data-test= 'error']");
   // private final By inventoryContainer = By.id("inventory_container");

    //CONSTRUCTOR - recibe el driver de BasePage?
    public LoginPage(WebDriver driver){
        super(driver);
        //this.driver = driver;
    }

    //A C T I O N S - Lo que puedes hacer en esta pagina
    public void login(String username, String password)
    {
       // WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        //wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField));
        type(usernameField, username);
        type(passwordField, password);
        click(loginButton);

        //driver.findElement(usernameField).sendKeys(username);
        //driver.findElement(passwordField).sendKeys(password);
        //driver.findElement(loginButton).click();
    }

    public String getTitle(){

        return driver.getTitle();
    }

    public String getErrorMessage(){
        return getText(errorMessage);
    }

    /*
    public boolean inventoryIsDisplayed(){
        return isDisplayed(inventoryContainer);
    }

     */



}
