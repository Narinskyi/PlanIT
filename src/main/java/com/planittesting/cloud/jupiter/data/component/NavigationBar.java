package com.planittesting.cloud.jupiter.data.component;

import org.openqa.selenium.By;
import org.openqa.selenium.support.pagefactory.ByChained;

public class NavigationBar {
    private static final By NAVBAR = By.cssSelector(".navbar");
    private static final String NAVBAR_BUTTON = "//a[contains(text(),'%s')]";

    private static By buttonWithName(String name) {
        return new ByChained(NAVBAR, By.xpath(String.format(NAVBAR_BUTTON, name)));
    }

    private static final By HOME = buttonWithName("Home");
    private static final By SHOP = buttonWithName("Shop");
    private static final By CONTACT = buttonWithName("Contact");
    private static final By LOGIN = buttonWithName("Login");
    private static final By CART = buttonWithName("Cart");
}
