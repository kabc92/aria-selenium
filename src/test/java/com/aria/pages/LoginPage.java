package com.aria.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {

    //Instance variable only accessible from this class
    private WebDriver driver;

    //CONSTRUCTOR - recibe el driver de BaseTest
    public LoginPage(WebDriver driver){
        this.driver = driver;
    }

    //L O C A T O R S - Los elementos de la pagina
    private By usernameField = By.id("user-name");
    private By passwordField = By.id("password");
    private By loginButton = By.id("login-button");

    //A C T I O N S - Lo que puedes hacer en esta pagina
    public void login(String username, String password)
    {
        driver.findElement(usernameField).sendKeys(username);
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(loginButton).click();
    }

    public String getTitle(){
        return driver.getTitle();
    }

}
