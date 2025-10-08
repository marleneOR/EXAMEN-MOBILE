package com.ct.mobile.step;

import com.ct.mobile.model.Product;
import com.ct.mobile.view.HomeView;
import io.appium.java_client.AppiumDriver;
import org.junit.Assert;

public class HomeStep {
    private HomeView homeView;
    private Product productData;
    private final AppiumDriver driver;

    public HomeStep(AppiumDriver driver, Product productData) {
        if (driver == null) {
            throw new IllegalStateException("El driver no ha sido inicializado.");
        }
        this.driver = driver;
        this.productData = productData;
        this.homeView = new HomeView(driver);
    }

    public void showDetailView() {
        boolean visible = homeView.showMainView();

        if (!visible) {
            throw new AssertionError("No se mostró la vista principal en el tiempo de espera.");
        }
    }

    public void galleryValidation() {
        Assert.assertTrue(homeView.getCountElements() > 1);

    }

    public void selectQuantityOfProduct(int quantity, String product) {
        boolean opened = homeView.selectProduct(product);

        if (!opened) {
            System.out.println(" Producto no se pudo abrir, saltando la actualización de cantidad");
            return;
        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        homeView.quantityProduct(quantity);

        double priceProductDetail = homeView.getUnitPrice();
        double totalPrice = quantity * priceProductDetail;
        productData.setName(product);
        productData.setQuantity(quantity);
        productData.setUnitPrice(priceProductDetail);
        productData.setTotalPrice(totalPrice);
        homeView.addCartBtn();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        homeView.goCartBtn();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
