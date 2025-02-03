package driver;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.remote.DesiredCapabilities;

import config.CapabilitiesReader;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;

public class DriverManager {
    private static AndroidDriver driver;
    private static AppiumDriverLocalService service;

    public static void initializeDriver() {
        if (driver == null) {
            try {
                // Read capabilities from JSON
                DesiredCapabilities capabilities = CapabilitiesReader.getCapabilities();

                // Start the Appium Server
                service = AppiumDriverLocalService.buildDefaultService();
                service.start();

                System.out.println("✅ Appium Server started at: " + service.getUrl());

                // Ensure Appium Server is up before initializing driver
                waitForAppiumServer();

                // Initialize Android Driver
                driver = new AndroidDriver(new URL(service.getUrl().toString()), capabilities);
            } catch (MalformedURLException e) {
                throw new RuntimeException("Invalid Appium Server URL: " + e.getMessage());
            }
        }
    }

    public static AndroidDriver getDriver() {
        if (driver == null) {
            throw new IllegalStateException("Driver not initialized!");
        }
        return driver;
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
        if (service != null) {
            service.stop();
        }
    }

    private static void waitForAppiumServer() {
        int retries = 10;
        while (retries > 0) {
            try {
                Thread.sleep(2000);
                if (service.isRunning()) {
                    System.out.println("✅ Appium server is ready!");
                    return;
                }
            } catch (InterruptedException e) {
                throw new RuntimeException("Error while waiting for Appium server: " + e.getMessage());
            }
            retries--;
        }
        throw new RuntimeException("❌ Appium server is not responding!");
    }
}
