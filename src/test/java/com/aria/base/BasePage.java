package com.aria.base;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/*
protected void click(By locator)

protected void type(By locator, String text)

protected String getText(By locator)

protected boolean isDisplayed(By locator)

protected WebElement find(By locator)
 */
public abstract class BasePage { //No one can instantiate this class somewhere else "new BasePage()"

    //I N S T A N C E  V A R I A B L E S
    protected WebDriver driver;
    protected WebDriverWait wait;

    //C O N S T R U C T O R  - INITIALIZES WebDriver driver AND WebDriverWait wait for ALL child classes
    protected BasePage(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));//each wait will wait up to 10 seconds per element
    }

    //P A G E   A C T I O N S
    protected void click(By locator){
        wait.until(ExpectedConditions.elementToBeClickable(locator))
                .click();
    }


    protected boolean isDisplayed(By locator){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator))
                .isDisplayed();
    }

    protected String getText(By locator){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator))
                .getText();
    }

    protected void type(By locator, String text){
        //Find the desired text box using By locator
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

        //Clear the text box and then make sure that the focus is in the element
        element.clear();
        element.click();

        //Type the desired text
        element.sendKeys(text);
    }

    protected void waitForUrlContains(String urlFragment) {

        wait.until(ExpectedConditions.urlContains(urlFragment));
    }


    protected void scrollToWebElement(By locator){
        // Wait until the element exists in the DOM
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));

        //Create JavaScript executor from the current driver
        JavascriptExecutor js = (JavascriptExecutor) driver;

        //Execute JavaScript to scroll the page until the provided element is visible
        js.executeScript("arguments[0].scrollIntoView(true);" , element);

    }










}
