package org.example;
import static java.lang.StringTemplate.STR;
import static java.lang.System.out;
import static java.util.Calendar.*;

public class Main {
    public static void main(String[] args) {
        var v = "hello";
        var i = 0;
        switch (v) {
            case "hello":
                ++i;
                break;
            default:
                break;
        }
        out.println(STR."v: \{i}");
    }
    static void switchStatement(int day) {

        int len = 0;
        switch (day) {
            case MONDAY:
            case FRIDAY:
            case SUNDAY:
                len = 6;
                break;
            case TUESDAY:
                len = 7;
                break;
            case THURSDAY:
            case SATURDAY:
                len = 8;
                break;
            case WEDNESDAY:
                len = 9;
                break;
        }
    }
}