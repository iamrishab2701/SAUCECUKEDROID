package driver;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import config.CapabilitiesReader;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

public class AppiumServerManager {
    private static AppiumDriverLocalService service;
    private static String emulatorName;
    private static final int MAX_RETRIES = 30; // Wait up to 5 minutes

    public static void startServerAndEmulator() {
        try {
            // Step 1: Start Appium Server First
            startAppiumServer();

            // Step 2: Fetch Emulator Name from capabilities.json
            emulatorName = CapabilitiesReader.getDeviceName();
            System.out.println("📌 Emulator Device: " + emulatorName);

            // Step 3: Start Emulator
            startEmulator(emulatorName);

            // Step 4: Wait Until Emulator is Fully Booted
            waitForEmulatorToBoot();
        } catch (Exception e) {
            throw new RuntimeException("❌ Failed to start Appium Server or Emulator: " + e.getMessage());
        }
    }

    public static void stopServerAndEmulator() {
        // Step 1: Stop Appium Server
        if (service != null) {
            service.stop();
            System.out.println("✅ Appium Server Stopped.");
        }

        // Step 2: Stop Emulator
        stopEmulator();
    }

    private static void startAppiumServer() {
        try {
            System.out.println("🚀 Starting Appium Server...");
            service = new AppiumServiceBuilder()
                    .withAppiumJS(new File("/usr/local/bin/appium")) // Adjust path if necessary
                    .withIPAddress("127.0.0.1")
                    .usingAnyFreePort() // Dynamic port selection
                    .build();
            service.start();
            System.out.println("✅ Appium Server started on " + service.getUrl());
        } catch (Exception e) {
            throw new RuntimeException("❌ Failed to start Appium Server: " + e.getMessage());
        }
    }

    public static String getServerUrl() {
        return service != null ? service.getUrl().toString() : "http://127.0.0.1:4723";
    }    

    private static void startEmulator(String emulatorName) {
        try {
            System.out.println("🚀 Starting emulator: " + emulatorName);
            ProcessBuilder processBuilder = new ProcessBuilder("emulator", "-avd", emulatorName);
            processBuilder.start();
            System.out.println("✅ Emulator command sent.");
        } catch (IOException e) {
            throw new RuntimeException("❌ Failed to start emulator: " + e.getMessage());
        }
    }

    private static void waitForEmulatorToBoot() {
        try {
            System.out.println("⏳ Waiting for emulator to boot...");
            boolean bootCompleted = false;
            int retries = MAX_RETRIES;

            while (!bootCompleted && retries > 0) {
                Process process = Runtime.getRuntime().exec("adb shell getprop sys.boot_completed");
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line = reader.readLine();
                if (line != null && line.trim().equals("1")) {
                    bootCompleted = true;
                    System.out.println("✅ Emulator boot completed!");
                    break;
                }
                Thread.sleep(10000); // Wait 10 seconds before retrying
                retries--;
            }

            if (!bootCompleted) {
                throw new RuntimeException("❌ Emulator did not boot within 5 minutes!");
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("❌ Error while waiting for emulator to boot: " + e.getMessage());
        }
    }

    private static void stopEmulator() {
        try {
            System.out.println("🛑 Stopping emulator...");
            ProcessBuilder processBuilder = new ProcessBuilder("adb", "emu", "kill");
            processBuilder.start();
            System.out.println("✅ Emulator stopped.");
        } catch (IOException e) {
            throw new RuntimeException("❌ Failed to stop emulator: " + e.getMessage());
        }
    }

}
