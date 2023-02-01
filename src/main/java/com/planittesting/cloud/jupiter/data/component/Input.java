package com.planittesting.cloud.jupiter.data.component;

import org.openqa.selenium.By;

public class Input {

    public static final By ERROR_MESSAGE = By.xpath("//span[contains(@ui-if,'$error')]");

    private static By inputWithName(String name) {
        String input = String.format("//label[contains(text(),'%s')]/following-sibling::*//input", name);
        String textarea = String.format("//label[contains(text(),'%s')]/following-sibling::*//textarea", name);
        return By.xpath(String.format("%s|%s", input, textarea));
    }

    public static final By FORENAME = inputWithName("Forename");
    public static final By SURNAME = inputWithName("Surname");
    public static final By EMAIL = inputWithName("Email");
    public static final By TELEPHONE = inputWithName("Telephone");
    public static final By MESSAGE = inputWithName("Message");
}
