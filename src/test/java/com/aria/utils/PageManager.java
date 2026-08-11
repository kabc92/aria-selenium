package com.aria.utils;

import com.aria.base.BasePage;
import org.openqa.selenium.WebDriver;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public class PageManager {

    // I  N  S  T  A  N  C  E   V  A  R  I  A  B  L  E  S
    private final WebDriver driver;

    //Cache - stores already created page instances
    //Key: Class<? extends BasePage> --> The IDENTITY of the page class (LoginPage.class)
    //Value: BasePage                --> The actual page object already created
    private final Map< Class<? extends BasePage>, BasePage > pageCache = new HashMap<>();

    //C  O  N  S  T  R  U  C  T  O  R
    public PageManager(WebDriver driver){
        this.driver = driver;
    }

    //M  E  T  H  O  D  S
    // <T extends BasePage> -> T can ONLY be BasePage OR any class that inherits from it
    // T getPage            -> Returns an object of type T (LoginPage, CartPage, etc)
    // Class<T> pageClass   -> CUADERNO STEP 1: RECEIVES the IDENTITY of the CLASS you want (LoginPage.class)
    public <T extends BasePage> T getPage(Class<T> pageClass){

        //IF the page does not exist in cache -> Create it using reflection!
        if(!pageCache.containsKey(pageClass)){

            try{
                //CUADERNO STEP 2 - Get the constructor that receives WebDriver | equivalent to: new LoginPage(driver)
                Constructor<T> constructor = pageClass.getConstructor(WebDriver.class);

                //CUADERNO STEP 3 - Create the object using the constructor from previous step
                T page = constructor.newInstance(driver); //T page means ANY pageClass

                //Save in cache - KEY: LoginPage.class , VALUE: The LoginPage object
                pageCache.put(pageClass, page);
            } catch(Exception e){
                //IF something fails using reflection, throw an error with the class name
                throw new RuntimeException("Could not create page: " + pageClass.getName(), e);
            }
        }
        //FINAL Step - Return the pageCache (recently created OR the existing one)
                //The casting (T) its necessary because pageCache stores BasePage
        return (T) pageCache.get(pageClass);
    }

}
