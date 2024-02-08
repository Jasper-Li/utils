package com.example.demo;

public class MoodAnalyser {
    public String analyseMood(String msg) {
        return msg.contains("bad") ?
            "SAD": "HAPPY";
    }
}
