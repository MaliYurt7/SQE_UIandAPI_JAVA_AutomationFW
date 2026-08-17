package com.sauceUI.step_def.ProductsPage;

import com.sauceUI.utilities.GenericUtils;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;

import java.util.List;
import java.util.Map;

public class ProductsStepDef {


    GenericUtils utils = new GenericUtils();
    ProductsFlow productsFlow = new ProductsFlow();
    public double totalPrice;


    @When("user adds the following products to cart")
    public void user_adds_the_following_products_to_cart(DataTable dataTable) {
        List<Map<String, String>> items = dataTable.asMaps(String.class, String.class);
        productsFlow.addTheProductInTheCart(items);
    }

    @When("user should see number {int} on  the shopping card link")
    public void user_should_see_number_on_the_shopping_card_link(Integer numberOfTheProducts) {
        productsFlow.getNumberOfTheProductsOnTheCartImage(numberOfTheProducts);
    }

    @Then("click the shopping cart link")
    public void click_the_shopping_cart_link() {
        productsFlow.clickShoppingCartLink();
    }

    @Then("page should navigate to {string} page")
    public void page_should_navigate_to_page(String moduleName) {
        productsFlow.validateThePageName(moduleName);
    }


    @Then("user should see {int} {string} button")
    public void user_should_see_button(Integer AddToCardBtnNumber, String addToCart) {
        productsFlow.validateTheAddToCardBtnNumber(AddToCardBtnNumber);
    }

    @Then("user should see the different product pictures")
    public void user_should_see_the_different_product_pictures() {
        productsFlow.getProducpictures();
    }


    @When("user adds the following product to cart")
    public void user_adds_the_following_product_to_cart(io.cucumber.datatable.DataTable dataTable) {
        List<Map<String, String>> item = dataTable.asMaps(String.class, String.class);
        productsFlow.tryToAddTheProductInTheCart(item);
    }

}
