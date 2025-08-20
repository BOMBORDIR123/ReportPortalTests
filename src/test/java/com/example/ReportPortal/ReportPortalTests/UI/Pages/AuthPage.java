package com.example.ReportPortal.ReportPortalTests.UI.Pages;

import com.codeborne.selenide.SelenideElement;
import com.example.ReportPortal.ReportPortalTests.utils.Config;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.*;

/**
 * Страница авторизации в Report Portal.
 * Содержит методы для логина пользователя.
 */
public class AuthPage {
    private final SelenideElement loginInput = $("[name='login']");
    private final SelenideElement passwordInput = $("[name='password']");
    private final SelenideElement loginButton = $$("button").findBy(exactText("Login"));
    private final String BASE_URL = Config.get("rp.endpoint.ui");

    /**
     * Выполняет авторизацию в Report Portal.
     *
     * @param username логин пользователя
     * @param password пароль пользователя
     */
    public void login(String username, String password) {
        open(BASE_URL);
        loginInput.setValue(username);
        passwordInput.setValue(password);
        loginButton.click();
    }
}