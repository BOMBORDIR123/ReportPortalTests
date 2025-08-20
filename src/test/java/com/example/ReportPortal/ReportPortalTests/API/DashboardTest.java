package com.example.ReportPortal.ReportPortalTests.API;

import com.example.ReportPortal.ReportPortalTests.API.models.Dashboard;
import com.example.ReportPortal.ReportPortalTests.API.models.DashboardResponse;
import com.example.ReportPortal.ReportPortalTests.utils.Config;
import com.example.ReportPortal.ReportPortalTests.utils.Specification;
import io.qameta.allure.Attachment;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Набор API-тестов для проверки функциональности Dashboard в Report Portal.
 */
public class DashboardTest {
    String API_KEY = Config.get("rp.api.key");
    String BASE_URL = Config.get("rp.endpoint.api");
    ContentType CONTENT_TYPE = ContentType.JSON;

    /**
     * Проверяет возможность создать новый Dashboard через API.
     * <p>
     * Шаги:
     * <ul>
     *     <li>Выполнить POST-запрос на создание нового Dashboard с корректными параметрами.</li>
     *     <li>Проверить, что ответ содержит код 201 и ID созданного Dashboard.</li>
     *     <li>Выполнить GET-запрос списка Dashboard.</li>
     *     <li>Убедиться, что ID созданного Dashboard присутствует в списке.</li>
     * </ul>
     * Ожидаемый результат: Dashboard успешно создаётся и доступен в списке.
     */
    @Test
    @Feature("API")
    @DisplayName("Создание нового Dashboard")
    @Description("Тест проверяет создание нового Dashboard")
    public void createNewDashboard() {
        AtomicLong createdDashboardId = new AtomicLong();

        step("Выполнить POST-запрос на создание Dashboard", () -> {
            Dashboard dashboard = new Dashboard(
                    "stringNewBoard",
                    "stringNewBoard"
            );

            Response response = given()
                    .spec(Specification.requestSpecification(BASE_URL, CONTENT_TYPE, API_KEY))
                    .body(dashboard)
                    .when().post("/dashboard")
                    .then().log().all()
                    .statusCode(201)
                    .extract().response();
            attachResponse("POST /dashboards response", response);

            createdDashboardId.set(response.jsonPath().getLong("id"));
        });

        step("Проверить, что Dashboard присутствует в списке", () -> {
            Response response = given()
                    .spec(Specification.requestSpecification(BASE_URL, CONTENT_TYPE, API_KEY))
                    .when().get("/dashboard")
                    .then().log().all()
                    .statusCode(200)
                    .extract().response();
            attachResponse("GET /dashboards response", response);

            DashboardResponse dto = response.as(DashboardResponse.class);

            List<Long> dashboardIds = dto.getContent().stream()
                    .map(DashboardResponse.DashboardContent::getId).toList();

            assertTrue(
                    dashboardIds.contains(createdDashboardId.get()),
                    "Созданный Dashboard не найден в списке"
            );
        });
    }

    /**
     * Проверяет, что Dashboard не создаётся, если не передан обязательный параметр {@code name}.
     * <p>
     * Шаги:
     * <ul>
     *     <li>Выполнить POST-запрос на создание Dashboard без параметра name.</li>
     *     <li>Проверить, что ответ содержит код 400.</li>
     *     <li>Выполнить GET-запрос списка Dashboard.</li>
     *     <li>Убедиться, что Dashboard с таким description отсутствует.</li>
     * </ul>
     * Ожидаемый результат: Dashboard не создаётся и не появляется в списке.
     */
    @Test
    @Feature("API")
    @DisplayName("Создание нового Dashboard с недостаточными параметрами")
    @Description("Тест проверяет, что Dashboard не создаётся при отсутствии обязательного параметра")
    public void unCreateNewDashboard() {
        String description = "stringError" + System.currentTimeMillis();

        step("Попытка создать Dashboard без name", () -> {
            Dashboard dashboard = new Dashboard(
                    description
            );

            Response response = given()
                    .spec(Specification.requestSpecification(BASE_URL, CONTENT_TYPE, API_KEY))
                    .body(dashboard)
                    .when().post("/dashboard")
                    .then().log().all()
                    .statusCode(400)
                    .extract().response();
            attachResponse("POST /dashboards response", response);
        });

        step("Проверить, что Dashboard присутствует в списке", () -> {
            Response response = given()
                    .spec(Specification.requestSpecification(BASE_URL, CONTENT_TYPE, API_KEY))
                    .when().get("/dashboard")
                    .then().log().all()
                    .statusCode(200)
                    .extract().response();
            attachResponse("GET /dashboards response", response);

            DashboardResponse dto = response.as(DashboardResponse.class);

            List<String> dashboardDescriptions = dto.getContent()
                    .stream()
                    .map(DashboardResponse.DashboardContent::getDescription)
                    .toList();

            assertFalse(
                    dashboardDescriptions.contains(description),
                    "Dashboard с неполными параметрами был создан"
            );
        });
    }

    /**
     * Прикрепляет ответ API в Allure-отчёт.
     *
     * @param name название вложения
     * @param response объект ответа API
     * @return тело ответа в виде массива байтов
     */
    @Attachment(value = "{name}", type = "application/json")
    public byte[] attachResponse(String name, Response response) {
        return response.asByteArray();
    }
}
