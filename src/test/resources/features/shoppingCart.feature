Feature: Agregar productos al carrito en SauceLabs
  Como usuario de la aplicación SauceLabs
  Quiero agregar productos desde la galería
  Para validar que el carrito se actualice correctamente
  @LoginTest
  Scenario Outline: Validar actualización del carrito al agregar productos
    Given estoy en la aplicación de SauceLabs
    And valido que carguen correctamente los productos en la galeria
    When agrego <UNIDADES> del siguiente producto "<PRODUCTO>"
    Then valido el carrito de compra actualice correctamente


    Examples:
      | PRODUCTO                        | UNIDADES |
      | Sauce Labs Backpack             | 1        |
      | Sauce Labs Bolt - T-Shirt       | 1        |
      | Sauce Labs Bike Light           | 2        |
      | Sauce Labs Fleece Jacket        | 2        |





