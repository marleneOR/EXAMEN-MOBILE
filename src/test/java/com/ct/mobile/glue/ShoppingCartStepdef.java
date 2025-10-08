package com.ct.mobile.glue;

import com.ct.mobile.config.MobileDriverManager;
import com.ct.mobile.model.Product;
import com.ct.mobile.step.CartStep;
import com.ct.mobile.step.HomeStep;
import io.appium.java_client.AppiumDriver;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.Scenario;

import org.junit.jupiter.api.Assertions;


public class ShoppingCartStepdef {

    private final AppiumDriver driver = MobileDriverManager.getDriver();
    private Product productData = new Product(); // Datos del producto

    private HomeStep homeStep;
    private CartStep cartStep;

    public ShoppingCartStepdef() {
        homeStep = new HomeStep(driver, productData);
        cartStep = new CartStep(driver, productData);
    }

    @Before
    public void beforeScenario(Scenario scenario) {
        MobileDriverManager.setScenario(scenario);
    }

    @Given("estoy en la aplicación de SauceLabs")
    public void estoyEnLaAplicaciónDeSauceLabs() {
        homeStep.showDetailView();
    }

    @And("valido que carguen correctamente los productos en la galeria")
    public void validoQueCarguenCorrectamenteLosProductosEnLaGaleria() {
        homeStep.galleryValidation();
        MobileDriverManager.takeScreenshot();
    }

    @When("agrego {int} del siguiente producto {string}")
    public void agregoDelSiguienteProducto(int quantity, String product) {
        homeStep.showDetailView();
        homeStep.selectQuantityOfProduct(quantity, product);
        MobileDriverManager.takeScreenshot();

        System.out.println("@@@@@ ProductData - HomeStep @@@@@");
        System.out.println("Nombre: " + productData.getName());
        System.out.println("Precio unitario: " + productData.getUnitPrice());
        System.out.println("Cantidad: " + productData.getQuantity());
        System.out.println("Total: " + productData.getTotalPrice());
    }

    @Then("valido el carrito de compra actualice correctamente")
    public void validoElCarritoDeCompraActualiceCorrectamente() {
        String titleCart = cartStep.getTitleCart();
        String expectTitleCart = "My Cart";
        Assertions.assertTrue(titleCart.equalsIgnoreCase(expectTitleCart),
                "El título del carrito no coincide. Esperado: " + expectTitleCart + " | Obtenido: " + titleCart);
        Assertions.assertEquals(expectTitleCart.toUpperCase(), titleCart.toUpperCase());

        MobileDriverManager.takeScreenshot();
        System.out.println("@@@@@ CartStep @@@@@");
        System.out.println("Nombre: " + cartStep.getNameProductCart());
        System.out.println("Precio unitario: " + cartStep.getUnitPriceCart());
        System.out.println("Cantidad: " + cartStep.getQuantityCart());
        System.out.println("Total: " + cartStep.getTotalPriceCart());
        cartStep.validDetailProduct();
    }
}
