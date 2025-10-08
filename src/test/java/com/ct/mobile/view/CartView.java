package com.ct.mobile.view;

import com.ct.mobile.config.MobileDriverManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class CartView {
    private final AppiumDriver driver;

    @AndroidFindBy(xpath = "//android.view.ViewGroup[@resource-id='com.saucelabs.mydemoapp.android:id/cartCL']" +
            "/android.widget.ScrollView/android.view.ViewGroup/android.widget.TextView")
    private WebElement titleCart;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/productIV")
    private WebElement productCart;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/itemsTV")
    private WebElement totalQuantityCart;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/totalPriceTV")
    private WebElement totalPriceCart;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/priceTV")
    private WebElement unitPriceCart;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/titleTV")
    private WebElement nameProductCart;

    public CartView() {
        this(MobileDriverManager.getDriver());
    }

    public CartView(AppiumDriver driver) {
        if (driver == null) {
            throw new IllegalStateException("El AppiumDriver es null. Verifica que esté inicializado antes de usar CartView.");
        }
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(10)), this);
    }

    public String getTitleCart() {
        WebElement titleCart = new WebDriverWait(driver, Duration.ofSeconds(30))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[contains(@text,'My Cart')]")));
        return titleCart.getText();
    }

    public String getNameProductCart() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOf(nameProductCart));
        return nameProductCart.getText().trim();
    }

    public double getUnitPriceCart() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOf(unitPriceCart));

        String priceText = unitPriceCart.getText().trim()
                .replace("$", "")
                .trim();

        return Double.parseDouble(priceText);
    }

    public int getTotalQuantityCart() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOf(totalQuantityCart));

        String totalQuantityText = totalQuantityCart.getText().trim();
        return Integer.parseInt(totalQuantityText.replaceAll("[^0-9]", ""));
    }

    public double getTotalPriceCart() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOf(totalPriceCart));

        String totalPriceText = totalPriceCart.getText().trim()
                .replace("$", "")
                .trim();
        return Double.parseDouble(totalPriceText);
    }
}
