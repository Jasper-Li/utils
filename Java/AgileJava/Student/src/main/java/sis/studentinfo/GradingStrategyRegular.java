package sis.studentinfo;

public class GradingStrategyRegular extends GradingStrategyBasic implements  GradingStrategy{
    @Override
    public int getGradePointsFor(Grade grade) {
        return basicGradePointsFor(grade);
    }
}
