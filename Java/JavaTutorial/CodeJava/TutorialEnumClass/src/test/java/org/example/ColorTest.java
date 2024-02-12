package org.example;

import static org.junit.jupiter.api.Assertions.*;

class ColorTest {

    @org.junit.jupiter.api.Test
    void getValueOfColor() {
        record Check(Color color, int value) {};
        Check[] checks  = {
            new Check(Color.Red, 1),
            new Check(Color.Green, 2),
            new Check(Color.Blue, 3),
        };
        for (var c : checks){
            assertEquals(c.value, c.color.getValue());
        }
    }
    @org.junit.jupiter.api.Test
    void getValueOfColorString() {
        record Check(ColorString color, String value) {};
        Check[] checks  = {
                new Check(ColorString.Red, "red"),
                new Check(ColorString.Green, "green"),
                new Check(ColorString.Blue, "blue"),
        };
        for (var c : checks){
            assertEquals(c.value, c.color.getValue());
        }
    }
}