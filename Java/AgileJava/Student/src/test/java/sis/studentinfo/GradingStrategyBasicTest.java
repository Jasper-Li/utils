package sis.studentinfo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class GradingStrategyBasicTest {

    @Test
    void basicGradePointsFor() {
        GradePointsCheck[] checks = {
            new GradePointsCheck(Grade.A, 4),
            new GradePointsCheck(Grade.B, 3),
            new GradePointsCheck(Grade.C, 2),
            new GradePointsCheck(Grade.D, 1),
            new GradePointsCheck(Grade.F, 0),
        };
        for(var check : checks) {
            assertEquals(check.gradePointExpected(), GradingStrategyBasic.basicGradePointsFor(check.grade()));
        }
    }
}