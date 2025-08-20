package com.example.ReportPortal.ReportPortalTests.API.models;

import lombok.Data;

@Data
public class Dashboard {
    public String description;
    public String name;

    public Dashboard(String description, String name) {
        this.description = description;
        this.name = name;
    }

    public Dashboard(String description) {
        this.description = description;
    }
}
