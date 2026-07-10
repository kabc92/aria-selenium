package com.aria.utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility class for taking browser screenshots.
 *
 * This is a utility class because it does not represent a page
 * or a test. It simply provides reusable helper methods.
 */
public class ScreenshotUtil {

    /**
     * Takes a screenshot of the browser's current state.
     * @param driver   The WebDriver instance controlling the browser.
     * @param testName The test name used to identify the screenshot file.
     */
    public static void takeScreenshot(WebDriver driver, String testName) {

        // A try/catch block is used because writing files to disk
        // may throw an IOException.
        try {

            // WebDriver does not directly expose the getScreenshotAs() method,
            // so it must be cast to the TakesScreenshot interface.
            //
            // OutputType.FILE tells Selenium to return the screenshot
            // as a temporary file.
            File screenshot = ((TakesScreenshot) driver)
                    .getScreenshotAs(OutputType.FILE);

            // Generate a timestamp so each screenshot has a unique name
            // and no existing file is overwritten.
            String timestamp = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));

            // Build the destination path for the screenshot.
            // Example: screenshots/login_HappyPath_2026-07-09_18-45-10.png
            String path = "screenshots/" + testName + "_" + timestamp + ".png";

            // Copy the temporary screenshot file created by Selenium
            // into the project's screenshots folder.
            FileUtils.copyFile(screenshot, new File(path));

            // Print a success message with the saved file location.
            System.out.println("Screenshot saved: " + path);

        } catch (IOException e) {

            // If saving the file fails, print the error message
            // to help with debugging.
            System.out.println("Screenshot failed: " + e.getMessage());
        }
    }
}