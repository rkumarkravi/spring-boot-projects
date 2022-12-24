package com.rk.learnity.enums;

public enum ResponseMessages {
    SU("Data fetched successfully!"),
    FFR("Failed to fetch Response!");

    private String value;

    ResponseMessages(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
