package com.example.ReportPortal.ReportPortalTests.API.models;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class DashboardResponse {
    private List<DashboardContent> content;
    private Page page;

    @Data
    public static class DashboardContent {
        private String description;
        private String owner;
        private Long id;
        private String name;
        private List<Widget> widgets;
    }

    @Data
    public static class Widget {
        private String widgetName;
        private Long widgetId;
        private String widgetType;
        private WidgetSize widgetSize;
        private WidgetPosition widgetPosition;
        private Map<String, Object> widgetOptions;
    }

    @Data
    public static class WidgetSize {
        private Integer width;
        private Integer height;
    }

    @Data
    public static class WidgetPosition {
        private Integer positionX;
        private Integer positionY;
    }

    @Data
    public static class Page {
        private Long number;
        private Long size;
        private Long totalElements;
        private Long totalPages;
        private Boolean hasNext;
    }
}