package ru.massalskiy.MySecondTestAppSpringBoot.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Systems {
    ERP("Enterprise Resource Planning"),
    CRM("Custom Relationship Management"),
    WMS("Warehouse Management System");
    private final String name;
    Systems(String name) {
        this.name = name;
    }
    @JsonValue
    public String getName() {
        return name;
    }
    @Override
    public String toString() {
        return name;
    }
}
