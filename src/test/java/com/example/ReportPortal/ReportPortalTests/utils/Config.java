package com.example.ReportPortal.ReportPortalTests.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Утилитарный класс для загрузки и доступа к конфигурации Report Portal из файла
 * <code>reportportal.properties</code> в ресурсах проекта.
 * <p>
 * Все свойства загружаются при инициализации класса. Если файл не найден
 * или возникает ошибка чтения, выбрасывается RuntimeException.
 * </p>
 * Пример использования:
 * <pre>
 * String apiKey = Config.get("rp.api.key");
 * String endpoint = Config.get("rp.endpoint.api");
 * </pre>
 */
public class Config {
    private static final Properties props = new Properties();

    static {
        try (InputStream input = Config.class.getClassLoader().getResourceAsStream("reportportal.properties")) {
            if (input != null) {
                props.load(input);
            } else {
                throw new RuntimeException("Не найден reportportal.properties");
            }
        } catch (IOException e) {
            throw new RuntimeException("Ошибка чтения reportportal.properties", e);
        }
    }

    public static String get(String key) {
        return props.getProperty(key);
    }
}