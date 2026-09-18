package com.aria.driver;

import org.openqa.selenium.WebDriver;


/*
setDriver() -> guarda mi driver
getDriver() -> obtiene mi driver
removeDriver() -> limpia mi referencia
 */
public class DriverManager {

    //A ThreadLocal that stores WebDrivers for each thread
    //new ThreadLocal crea el objeto que permitira hacer esa asociacion
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>(); //driver-> variable que referencia un ThreadLocal que puede almacenar WebDriver

    //Guarda este WebDriver para el thread que esta ejecutando este metodo
    public static void setDriver (WebDriver webDriver){
        driver.set(webDriver); //guarda el WebDriver recibido por parametro dentro del ThreadLocal para el thread actual
    }

    public static WebDriver getDriver(){
        return driver.get();//Obtiene el WebDriver que fue previamente guardado para el thread actual
    }

    public static void removeDriver(){
        driver.remove();//Quita del ThreadLocal la referencia al WebDriver asociada al thread actual
    }


}
