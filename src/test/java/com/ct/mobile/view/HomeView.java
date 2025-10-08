package com.ct.mobile.view;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class HomeView {
    private final AppiumDriver driver;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/productTV")
    private WebElement titleMainProduct;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/titleTV")
    private WebElement titlesProducts;
    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/minusIV")
    private WebElement minusButton;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/plusIV")
    private WebElement plusButton;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/noTV")
    private WebElement quantity;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/cartBt")
    private WebElement addCartBtn;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/cartBt")
    private WebElement goCartBtn;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/mTvTitle")
    private WebElement appLogo;

    @AndroidFindBy(xpath = "//androidx.recyclerview.widget.RecyclerView[@resource-id=\"com.saucelabs.mydemoapp.android:id/productRV\"]/android.view.ViewGroup\n")
    private List<WebElement> listProducts;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/productTV")
    private WebElement nameProduct;
    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/priceTV")
    private WebElement unitPrice;


    public HomeView(AppiumDriver driver) {
        if (driver == null) {
            throw new IllegalStateException("El AppiumDriver es null. Verifica que esté inicializado antes de usar HomeView.");
        }
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(10)), this);
    }

    public boolean showMainView() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(30))
                    .until(ExpectedConditions.visibilityOf(this.appLogo));
            return this.appLogo.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public int getCountElements() {
        return listProducts.size();
    }

    public boolean selectProduct(String productName) {
        try {
            WebElement productContainer = driver.findElement(
                    AppiumBy.xpath("//androidx.recyclerview.widget.RecyclerView[@resource-id='com.saucelabs.mydemoapp.android:id/productRV']" +
                            "/android.view.ViewGroup[.//android.widget.TextView[@text='" + productName + "']]")
            );

            productContainer.click();
            return true;
        } catch (Exception e) {
            System.out.println("No se pudo abrir el producto: " + productName);
            e.printStackTrace();
            return false;
        }
    }



    public void quantityProduct(int targetQty) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement quantityText = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("com.saucelabs.mydemoapp.android:id/noTV")
        ));


        WebElement plusButton = driver.findElement(By.id("com.saucelabs.mydemoapp.android:id/plusIV"));

        int currentQuantity = Integer.parseInt(quantityText.getText());

        while (currentQuantity < targetQty) {
            plusButton.click();
            wait.until(ExpectedConditions.textToBePresentInElement(quantityText, String.valueOf(currentQuantity + 1)));
            currentQuantity++;
        }

        String finalQuantity = quantityText.getText();
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        Assert.assertEquals("Cantidad incorrecta del producto", String.valueOf(targetQty), finalQuantity);
    }

    public double getUnitPrice() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOf(unitPrice));

        String priceText = unitPrice.getText().trim()
                .replace("$", "")
                .trim();

        return Double.parseDouble(priceText);
    }

    public void addCartBtn() {
        addCartBtn.click();
    }

    public void goCartBtn() {
        try {
            WebElement btn = new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.elementToBeClickable(
                            By.id("com.saucelabs.mydemoapp.android:id/cartRL")
                    ));
            btn.click();

        } catch (Exception e) {
            System.err.println("No se pudo hacer clic en el icono del cart o cargar la vista: ");
        }
    }

}
