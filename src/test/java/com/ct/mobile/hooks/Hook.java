package com.ct.mobile.hooks;

import com.ct.mobile.config.MobileDriverManager;
import io.appium.java_client.AppiumDriver;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.*;

public class Hook {
    private AppiumDriver driver;

    @BeforeAll
    public static void beforeAll() {
        String platform = System.getProperty("platformName", "Android");
        String serverUrl = System.getProperty("appiumServerUrl", "http://127.0.0.1:4723/");
        if (MobileDriverManager.getDriver() == null) {
            MobileDriverManager.startDriver(platform, serverUrl);
        }
    }

    @Before
    public void beforeScenario() {
        try {
            AppiumDriver driver = MobileDriverManager.getDriver();
            if (driver != null) {
                String platform = System.getProperty("platformName", "Android");
                if ("android".equalsIgnoreCase(platform)) {
                    MobileDriverManager.resetApp("com.saucelabs.mydemoapp.android");
                } else {
                    MobileDriverManager.resetApp("com.saucelabs.mydemoapp.ios");
                }
            }
        } catch (Exception e) {
            System.out.println("Error al reiniciar la app: " + e.getMessage());
        }
    }

    @AfterAll
    public static void afterAll() {
        try {
            MobileDriverManager.stopDriver();
        } catch (Exception e) {
            System.out.println("Error al detener el driver: " + e.getMessage());
        }
    }
}
