package com.ct.mobile.config;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.cucumber.java.Scenario;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class MobileDriverManager {
    public static AppiumDriver driver;
    private static Scenario scenario;

    public static AppiumDriver getDriver() {
        return driver;
    }
    public static void startDriver(String platform, String serverUrl) {
        if (driver != null) return;
        Config cfg = new Config(platform);
        Capabilities caps = "ios".equalsIgnoreCase(platform)
                ? DesiredCapsFactory.forIOS(cfg)
                : DesiredCapsFactory.forAndroid(cfg);
        try {
            URL url = new URL(serverUrl != null ? serverUrl : System.getProperty("appiumServerUrl", "http://127.0.0.1:4723/"));
            driver = "ios".equalsIgnoreCase(platform)
                    ? new IOSDriver(url, caps)
                    : new AndroidDriver(url, caps);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void stopDriver() {
        if (driver != null) {
            try {
                driver.quit();
            } finally {
                driver = null;
            }
        }
    }

    public static void resetApp(String appPackage) {
        if (driver != null && driver instanceof AndroidDriver) {
            AndroidDriver androidDriver = (AndroidDriver) driver;
            androidDriver.terminateApp(appPackage);
            androidDriver.activateApp(appPackage);
        }
    }

    public static void setScenario(Scenario scn) {
        scenario = scn;
    }

    public static void takeScreenshot() {
        if (driver != null && scenario != null) {
            try {
                byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(screenshot));

                int newWidth = originalImage.getWidth() / 3;
                int newHeight = originalImage.getHeight() / 3;

                BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, originalImage.getType());
                Graphics2D g = resizedImage.createGraphics();
                g.drawImage(originalImage, 0, 0, newWidth, newHeight, null);
                g.dispose();

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(resizedImage, "png", baos);
                byte[] resizedBytes = baos.toByteArray();

                scenario.attach(resizedBytes, "image/png", "Evidencia");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
