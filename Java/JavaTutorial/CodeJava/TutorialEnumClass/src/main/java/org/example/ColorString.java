package org.example;

public enum ColorString {
    Red("red"),
    Green("green"),
    Blue("blue");

    private String value;
    ColorString(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
