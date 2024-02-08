package sis.studentinfo;

public class GradingStrategyRegular implements  GradingStrategy{
    @Override
    public int getGradeFor(Grade grade) {
        return switch (grade) {
            case Grade.A -> 4;
            case Grade.B -> 3;
            case Grade.C -> 2;
            case Grade.D -> 1;
            default ->  0;
        };
    }
}
