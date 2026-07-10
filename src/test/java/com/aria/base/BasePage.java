package com.aria.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/*
✅ Crear BasePage
Haz que LoginPage, InventoryPage y CartPage hereden de BasePage.
Elimina el driver y el wait duplicados de esas clases.
Usa super(driver) en los constructores.
Corre todas tus pruebas y verifica que siguen pasando.
Y cuando lo tengas, no te voy a decir "bien hecho" y seguir.

This class manages the
 */
public abstract class BasePage { //No one can instantiate this class somewhere else "new BasePage()"

    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    }

}
