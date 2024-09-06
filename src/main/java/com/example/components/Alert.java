package com.example.components;

import org.openqa.selenium.By;

public class Alert {

    public static class Info {
        public static final By BODY = By.cssSelector(".alert-info");
    }

    public static class Error {
        public static final By BODY = By.cssSelector(".alert-error");
    }
}
