package com.aria.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/*
ConfigReader centralizes configuration so values like the base URL
are not hardcoded throughout the framework

Configuration values are stored in environment-specific properties files
The environment can be selected using the "env" Java System property

Example:

Environment selection:
mvn test              -> config-default.properties
mvn test -Denv=qa     -> config-qa.properties
mvn test -Denv=staging -> config-staging.properties

"default" is used when -Denv is not provided

ConfigReader loads the selected configuration file into a Properties object
and exposes its values through the get(key) method

The rest of the framework can therefore read configuration values
without knowing how or where the configuration file was loaded
*/


/*
WHY STATIC?

ConfigReader uses static because the framework only needs ONE shared configuration.
There is no need to create ConfigReader objects.

- static Properties -> stores ONE shared Properties object.
- static {}         -> loads the configuration ONCE when the class is initialized.
- static get()      -> allows access with ConfigReader.get("key")
                       without creating a ConfigReader object.

Calling ConfigReader.get(...) for the first time triggers class initialization,
which causes the static block to run.
*/
public class ConfigReader {

    // Stores one shared Properties object containing the loaded configuration.
    // It starts empty and is populated by properties.load(input)
    private static final Properties properties = new Properties();


    // Runs once when ConfigReader is initialized
    // and loads the selected environment configuration
    static {

        //Gets the environment from -Denv; uses "default" if not provided
        String environment = System.getProperty("env", "default");

        //Builds the config filename. Example: "staging" -> config-staging.properties
        String configFile = "config-" + environment + ".properties";

        // input references the InputStream used to read the selected configuration file
        try (InputStream input =
                     ConfigReader.class // Gets the Class object that represents ConfigReader at runtime
                             .getClassLoader() // Gets the ClassLoader associated with ConfigReader
                             .getResourceAsStream(configFile)) { // Finds the resource in the classpath and opens it for reading.Returns null if not found

            // Fail if the selected configuration file does not exist
            if (input == null) {
                throw new IllegalStateException(
                        "Configuration file not found: " + configFile
                );
            }

            // Reads the file's key=value pairs and stores them in "properties"
            properties.load(input);

        } catch (IOException e) {

            // Stops execution if the configuration file exists but cannot be read
            throw new RuntimeException(
                    "Failed to load configuration file: " + configFile, e
            );
        }
    }



    //Gets a configuration value by key. Example: "baseUrl" -> URL
    public static String get(String key) {
        return properties.getProperty(key);
    }


    /*
    How does your ConfigReader select and load the environment configuration?

    RESPONSE:
    The environment can be provided through the "env" Java System property
    If it is not provided, the framework uses "default"

    ConfigReader builds the corresponding configuration filename,
    gets the ClassLoader associated with ConfigReader, and searches
    the classpath for that resource

    getResourceAsStream() opens the resource as an InputStream,
    and Properties.load() loads the key-value pairs into the shared
    Properties object
    */





    /*
CONFIGURATION FLOW:

- Reads the environment from the Java System property:
  -Denv=staging -> "staging"
  No -Denv      -> "default"

- Builds the configuration filename:
  "staging" -> config-staging.properties

- The configuration file lives in:
  src/test/resources/

- During the Maven test build, resources are copied to:
  target/test-classes/

- target/test-classes is part of the test classpath

- ClassLoader searches the classpath for the selected config file

- getResourceAsStream() finds the resource and opens it as an InputStream

- Properties.load() reads and stores the key=value pairs

- ConfigReader.get("key") returns the requested configuration value


EASY WAY TO REMEMBER:

I put the configuration file in src/test/resources.
Maven copies it to target/test-classes, which is part of the test classpath
The ClassLoader searches that classpath for the resource, and
getResourceAsStream() allows ConfigReader to read it


CURRENT ARIA BEHAVIOR:

- mvn test
  -> config-default.properties
  -> Runs normally.

- mvn test -Denv=staging
  -> config-staging.properties
  -> File does not exist yet, so execution fails.

Aria is ready to support additional environments when their
real configuration files become available
*/

}


