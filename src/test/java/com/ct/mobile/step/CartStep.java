package com.ct.mobile.step;

import com.ct.mobile.model.Product;
import com.ct.mobile.view.CartView;
import io.appium.java_client.AppiumDriver;
import org.junit.jupiter.api.Assertions;

public class CartStep {
    private  CartView cartView;
    private  Product productData;
    private final AppiumDriver driver;

    public CartStep(AppiumDriver driver, Product productData) {
        if (driver == null) {
            throw new IllegalStateException("El driver no ha sido inicializado.");
        }
        this.driver = driver;
        this.productData = productData;
        this.cartView = new CartView(driver);
    }

    public String getTitleCart(){
        return cartView.getTitleCart();
    }
    public String getNameProductCart(){
        return cartView.getNameProductCart();
    }
    public double getUnitPriceCart(){
        return cartView.getUnitPriceCart();
    }
    public int getQuantityCart(){
        return cartView.getTotalQuantityCart();
    }
    public double getTotalPriceCart(){
        return cartView.getTotalPriceCart();
    }
    public void validDetailProduct(){
        Assertions.assertEquals(productData.getName(), cartView.getNameProductCart());
        Assertions.assertEquals(productData.getUnitPrice(), cartView.getUnitPriceCart());
        Assertions.assertEquals(productData.getQuantity(), cartView.getTotalQuantityCart());
        Assertions.assertEquals(productData.getTotalPrice(), cartView.getTotalPriceCart());
    }

}
