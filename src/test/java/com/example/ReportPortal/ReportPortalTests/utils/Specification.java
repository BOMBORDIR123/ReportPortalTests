package com.example.ReportPortal.ReportPortalTests.utils;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class Specification {
    public static RequestSpecification requestSpecification(String URL, ContentType contentType, String API_KEY) {
        return new RequestSpecBuilder()
                .setBaseUri(URL)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .setContentType(contentType)
                .build();
    }

    public static void installSpecification(RequestSpecification requestSpecification) {
        RestAssured.requestSpecification = requestSpecification;
    }
}
