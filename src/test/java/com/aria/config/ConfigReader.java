package com.aria.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/*
Loads configuration values from config.properties and makes them
available to the framework through get(key)

C O M P L E T E  F L O W
- src/test/resources/config.properties
              |-> Maven copies the resource
- target/test-classes/config.properties
              | -> target/test-classes is part of the test classpath
- ConfigReader.class
              | -> gets the ClassLoader associated with ConfigReader
- ClassLoader
              | -> searches the classpath for "config.properties"
- InputStream
              | -> opens the resource for reading
- properties.load(input)
              | -> reads and stores the key=value pairs
 * Properties object { baseUrl -> https://www.saucedemo.com }
              |-> get("baseUrl") -> "https://www.saucedemo.com"

The rest of the framework can therefore read configuration values
without knowing how or where the configuration file was loaded.
 */
public class ConfigReader {

//Stores one Shared copy of the configuration in memory
    private static final Properties properties = new Properties(); //empty object


    //Runs once ConfigReader is initialized and loads config.properties
    static{
        //input will reference the InputStream used to read config.properties
        try(InputStream input  =
                    ConfigReader.class // Gets the Class object that represents ConfigReader at runtime
                            .getClassLoader() //Gets the ClassLoader associated with ConfigReader
                            .getResourceAsStream("config.properties")){ //Finds the resource in the classpath and opens it for reading | returns null if the file is NOT in the classpath
            if(input == null){
                throw new IllegalStateException("config.properties not found");
            }
            //Reads the file's  key=value pairs and stores them in "properties"
            properties.load(input);
        } catch (IOException e) { //Input/Output exception store it in the variable e
            //Stop execution if the configuration file cannot be read
            throw new RuntimeException("Failed to load config.properties" ,e);
        }
    }

    //Returns a value previously loaded from config.properties using its key
    // Example: get("baseUrl") --> "https://www.saucedemo.com"
    public static String get(String key){
        return properties.getProperty(key);
    }


}

