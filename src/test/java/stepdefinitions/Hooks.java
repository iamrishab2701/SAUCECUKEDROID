package stepdefinitions;

import driver.AppiumServerManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;

public class Hooks {
    @Before
    public void setUp() {
        System.out.println("🛠 Starting Test Setup...");
        AppiumServerManager.startServerAndEmulator(); // Start Appium & Emulator
        System.out.println("✅ Test Setup Complete.");
    }

    @After
    public void tearDown() {
        System.out.println("🛑 Closing Test...");
        AppiumServerManager.stopServerAndEmulator(); // Stop Appium & Emulator
        System.out.println("✅ Test Execution Completed.");
    }
}
