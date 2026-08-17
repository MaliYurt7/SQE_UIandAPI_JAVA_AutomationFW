package com.sauceUI.step_def.ProductsPage;

import com.sauceUI.step_def.BasePage;
import com.sauceUI.utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class ProductsPage extends BasePage {


    @FindBy(css = "[data-test='title']")
    public WebElement getpageName;


    public WebElement getProduct(String productName) {
        String formatted = productName.toLowerCase().replace(" ", "-");
        String product = "add-to-cart-" + formatted;
        return Driver.get().findElement(By.id(product));
    }

    public WebElement getProductPrice(String productName) {
        String formatted = productName.toLowerCase().replace(" ", "-");
        String productPrice = "//button[@id='remove-" + formatted + "'" + "]" + "/../div[@class='inventory_item_price']";
        return Driver.get().findElement(By.xpath(productPrice));

    }

    @FindBy(css = ".shopping_cart_link span")
    public WebElement getShoppingCardLink;


    @FindBy(xpath = "//button[text()='Add to cart']")
    public List<WebElement> getAddToCard;

    @FindBy(xpath = "//img[@src='/assets/sl-404-Cq1a9k9X.jpg']")
    public List<WebElement> getImgProductPictures;


    public WebElement getButtonIdAttributeCheck(String productName) {

        String getProductBtn = "//div[text()='" + productName + "']/ancestor::div[@class='inventory_item_description']//button";
        return Driver.get().findElement(By.xpath(getProductBtn));

    }


}
