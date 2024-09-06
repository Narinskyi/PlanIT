package com.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.support.pagefactory.ByChained;

public class CartPage {
    public static final By ITEMS_TOTAL_COUNT = By.cssSelector(".cart-count");

    public static final By ITEMS_TOTAL_PRICE = By.cssSelector(".total");

    public static class Item {
        private static final String ITEM_ROW = "//td[contains(text(),'%s')]/parent::tr";

        public static By quantityOf(String itemName) {
            By itemRowXpath = By.xpath(String.format(ITEM_ROW, itemName));
            return new ByChained(itemRowXpath, By.cssSelector("input"));
        }

        public static By priceOf(String itemName) {
            By itemRowXpath = By.xpath(String.format(ITEM_ROW, itemName));
            return new ByChained(itemRowXpath, By.xpath("td[2]"));
        }

        public static By subtotalOf(String itemName) {
            By itemRowXpath = By.xpath(String.format(ITEM_ROW, itemName));
            return new ByChained(itemRowXpath, By.xpath("td[4]"));
        }
    }
}
