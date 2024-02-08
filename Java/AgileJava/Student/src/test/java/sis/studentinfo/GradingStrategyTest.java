package sis.studentinfo;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GradingStrategyTest {

    @Test
    void getGradeFor() {
        record GradePointChecks(GradingStrategy strategy, GradePointCheck[] checks){}
        GradePointCheck[] gradePointsHonours  = {
                new GradePointCheck(Grade.A, 5),
                new GradePointCheck(Grade.B, 4),
                new GradePointCheck(Grade.C, 3),
                new GradePointCheck(Grade.D, 2),
                new GradePointCheck(Grade.F, 0)
        };
        GradePointCheck[] gradePointsElite  = {
                new GradePointCheck(Grade.A, 4),
                new GradePointCheck(Grade.B, 4),
                new GradePointCheck(Grade.C, 4),
                new GradePointCheck(Grade.D, 4),
                new GradePointCheck(Grade.F, 3)
        };
        GradePointCheck[] gradePointsRegular  = {
                new GradePointCheck(Grade.A, 4),
                new GradePointCheck(Grade.B, 3),
                new GradePointCheck(Grade.C, 2),
                new GradePointCheck(Grade.D, 1),
                new GradePointCheck(Grade.F, 0)
        };
        // Array list for each
        GradePointChecks[] checks = {
            new GradePointChecks(new GradingStrategyRegular(), gradePointsRegular),
            new GradePointChecks(new GradingStrategyElite(), gradePointsElite),
            new GradePointChecks(new GradingStrategyHonours(), gradePointsHonours)
        };
        for (var entry: checks) {
            var strategy = entry.strategy();
            var gradePointsChecks = entry.checks();
            for (var check : gradePointsChecks) {
                var grade =check.grade();
                var gradePointExpected = check.gradePointExpected();
                assertEquals(gradePointExpected, strategy.getGradeFor(grade));
            }
        }
    }
}