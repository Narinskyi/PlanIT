package com.example.components;

import org.openqa.selenium.By;
import org.openqa.selenium.support.pagefactory.ByChained;

public class NavigationBar {
    private static final By NAVBAR = By.cssSelector(".navbar");
    private static final String NAVBAR_BUTTON = "//a[contains(text(),'%s')]";

    private static By buttonWithName(String name) {
        return new ByChained(NAVBAR, By.xpath(String.format(NAVBAR_BUTTON, name)));
    }

    public static final By HOME = buttonWithName("Home");
    public static final By SHOP = buttonWithName("Shop");
    public static final By CONTACT = buttonWithName("Contact");
    public static final By LOGIN = buttonWithName("Login");
    public static final By CART = buttonWithName("Cart");
}
