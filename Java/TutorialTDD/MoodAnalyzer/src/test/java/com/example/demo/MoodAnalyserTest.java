package com.example.demo;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;


class MoodAnalyserTest {
    @Test
    void testMoodAnalysis() {
        MoodAnalyser moodAnalyser = new MoodAnalyser();
        String mood = moodAnalyser.analyseMood("This is a bad day");
        assertThat(mood, CoreMatchers.is("SAD"));
    }

    @Test
    void testHappyMood() {
        MoodAnalyser moodAnalyser = new MoodAnalyser();
        String mood = moodAnalyser.analyseMood("This is a happy day");
        assertThat(mood, CoreMatchers.is("HAPPY"));
    }
}