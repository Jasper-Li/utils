package sis.studentinfo;

public class GradingStrategyBasic {
    static int basicGradePointsFor(Grade grade){
        return switch (grade) {
            case Grade.A -> 4;
            case Grade.B -> 3;
            case Grade.C -> 2;
            case Grade.D -> 1;
            default ->  0;
        };
    }
}
