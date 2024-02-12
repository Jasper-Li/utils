package org.example;

public enum Color {
    Red(1),
    Green(2),
    Blue(3);

    private int value;
    Color(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
