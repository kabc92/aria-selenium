package com.aria.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    //Instance variable only accessible from this class
    private WebDriver driver;

    //L O C A T O R S - Los elementos de la pagina
    private By usernameField = By.id("user-name");
    private By passwordField = By.id("password");
    private By loginButton = By.id("login-button");
    private By errorMessage = By.cssSelector("[data-test= 'error']");
    private By inventoryContainer = By.id("inventory_container");

    //CONSTRUCTOR - recibe el driver de BaseTest
    public LoginPage(WebDriver driver){
        this.driver = driver;
    }

    //A C T I O N S - Lo que puedes hacer en esta pagina
    public void login(String username, String password)
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField));

        driver.findElement(usernameField).sendKeys(username);
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(loginButton).click();
    }

    public String getTitle(){

        return driver.getTitle();
    }

    public String getErrorMessage(){
        return driver.findElement(errorMessage).getText();
    }

    public boolean isInventoryLoaded(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        return wait.until(ExpectedConditions.visibilityOfElementLocated(inventoryContainer))
                .isDisplayed();
    }


}
