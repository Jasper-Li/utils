package sis.studentinfo;

public class GradingStrategyHonours extends GradingStrategyBasic implements GradingStrategy {
    @Override
    public int getGradePointsFor(Grade grade) {
        var basicGradePoints = basicGradePointsFor(grade);
        return basicGradePoints > 0 ? basicGradePoints + 1 : 0;
    }
}
