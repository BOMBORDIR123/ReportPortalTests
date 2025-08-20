# Report Portal UI & API Tests

Набор UI и API тестов для работы с Report Portal.

## Требования

- Java 17+
- Maven 3.8+
- Google Chrome
- ChromeDriver (автоматически скачивается через WebDriverManager)
- Allure Commandline (для генерации отчетов)

## Конфигурация

Настройки Report Portal задаются в файле `src/main/resources/reportportal.properties`:

## Properties
```
rp.endpoint.api = https://demo.reportportal.io/api/v1/default_personal
rp.endpoint.ui = https://demo.reportportal.io/ui/#login/
rp.api.key = "KEY"
rp.launch = Java launch
rp.project = default_personal

rp.enable = true
rp.description = My awesome launch
rp.attributes = key:value; value;
rp.convertimage = true
rp.mode = DEFAULT
rp.skipped.issue = true
rp.batch.size.logs = 20
```

```
rp.api.key — API ключ (вписать свой ключ)
rp.endpoint.api — API адрес для API тестов
rp.endpoint.ui — API адрес для UI тестов
```

## Запуск тестов
1. Запуск всех тестов
```mvn clean test```

2. Генерация и просмотр Allure-отчета
```mvn allure:serve```

Можно объединить команду для запуска тестов и сразу просмотра отчета:
```mvn clean test allure:serve```

3. Запуск отдельных тестов

UI тесты:

Запуск конкретного UI теста:
```mvn -Dtest=com/example/demo/ReportPortalTests/UI/WidgetTest test```

API тесты:

Запуск конкретного API теста: 
```mvn -Dtest=com/example/demo/ReportPortalTests/API/DashboardTest test```

Можно запускать отдельные методы тестов через Maven:
```mvn -Dtest=DashboardTest#createNewDashboard test```

## Структура проекта
```src/test/java/com/example/demo/ReportPortalTests/UI``` — UI тесты Selenide

```src/test/java/com/example/demo/ReportPortalTests/API``` — API тесты с RestAssured

```src/test/resources/reportportal.properties``` — конфигурация Report Portal

## Структура проекта
```src
 └─ test
     └─ java
         └─ com.example.ReportPortal.ReportPortalTests
             ├─ API
             │   ├─ models
             │   │   ├─ Dashboard.java
             │   │   └─ DashboardResponse.java
             │   └─ DashboardTest.java
             ├─ UI
             │   ├─ Pages
             │   │   └─ AuthPage.java
             │   └─ WidgetTest.java
             └─ utils
                 ├─ BaseTest.java
                 ├─ Config.java
                 └─ Specification.java
resources
 └─ reportportal.properties
```

## Особенности
UI тесты используют Selenide и интеграцию с Allure.

API тесты используют RestAssured и интеграцию с Allure и Report Portal.

WebDriver автоматически конфигурируется через WebDriverManager.
