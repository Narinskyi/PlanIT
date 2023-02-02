package com.planittesting.cloud.jupiter.data.page;

import org.openqa.selenium.By;
import org.openqa.selenium.support.pagefactory.ByChained;

public class ShopPage {
    public static class Item {
        private static final String ITEM_CONTAINER = "//h4[contains(text(), '%s')]/parent::div";

        public static By buyButtonFor(String itemName) {
            By itemContainerXpath = By.xpath(String.format(ITEM_CONTAINER, itemName));
            return new ByChained(itemContainerXpath, By.cssSelector("a"));
        }

        public static By priceOf(String itemName) {
            By itemContainerXpath = By.xpath(String.format(ITEM_CONTAINER, itemName));
            return new ByChained(itemContainerXpath, By.cssSelector("span"));
        }
    }
}
