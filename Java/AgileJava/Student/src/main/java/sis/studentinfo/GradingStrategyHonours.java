package sis.studentinfo;

public class GradingStrategyHonours implements GradingStrategy {
    @Override
    public int getGradeFor(Grade grade) {
        return switch (grade) {
            case Grade.A -> 5;
            case Grade.B -> 4;
            case Grade.C -> 3;
            case Grade.D -> 2;
            default ->  0;
        };
    }
}
