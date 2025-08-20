package com.example.ReportPortal.ReportPortalTests.UI;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.example.ReportPortal.ReportPortalTests.UI.Pages.AuthPage;
import com.example.ReportPortal.ReportPortalTests.utils.Config;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;

/**
 * Набор UI-тестов для работы с дашбордами и виджетами в Report Portal.
 */
public class WidgetTest {
    String BASE_URL = Config.get("rp.endpoint.ui");

    /**
     * UI-тест: авторизация, переход на дашборд, создание виджета "Task Progress" и проверка его появления.
     */
    @Test
    @Feature("UI")
    @DisplayName("Создание нового Widget")
    @Description("Тест проверяет создание виджета Task Progress на дашборде пользователя")
    public void test() {
        AuthPage auth = new AuthPage();
        String login = "default";
        String password = "1q2w3e";
        String dashboardHref = "#" + login + "_personal/dashboard";

        SelenideElement nextStepButton = $$("button").findBy(text("Next step"));

        step("Авторизация", () -> {
            auth.login(login, password);
        });

        step("Переход на дашборд и выбор проекта", () -> {
            $("a[href='" + dashboardHref + "']").click();
            $$("a").findBy(text("stringNewBoard")).click();
        });

        step("Добавление виджета Task Progress", () -> {
            $$("button").findBy(text("Add new widget")).click();
            $("input[value='bugTrend']").parent().click();
            nextStepButton.click();

            $$("label")
                    .filterBy(Condition.text("DEMO_FILTER"))
                    .first().$("span")
                    .click();
            nextStepButton.click();

            $("[placeholder='Enter widget name']").setValue("Task Progress");
            $$("button").findBy(exactText("Add")).click();
        });

        step("Проверка, что виджет появился", () -> {
            $$("[class='container']")
                    .findBy(text("Task Progress"))
                    .shouldBe(visible);
        });
    }
}
