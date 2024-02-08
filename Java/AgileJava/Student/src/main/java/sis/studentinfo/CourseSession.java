package sis.studentinfo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * A representation for a single session of a specific university course.
 */
public class CourseSession implements Comparable<CourseSession> {
    // auto clean start
    private  static int count;
    private String department;
    private String number;
    private int credits;
    private List<Student> students = new ArrayList<Student>();
    private LocalDate startDate;

    public static void resetCount(int count) {CourseSession.count = count;}
    public CourseSession(String department, String number, LocalDate startDate) {
        this.department = department;
        this.number = number;
        this.startDate = startDate;
        ++CourseSession.count;
    }

    @Override
    public int compareTo(CourseSession o) {
        var result = department.compareTo(o.department);
        if (result != 0) return result;
        return number.compareTo(o.number);
    }
    public static int getCount() {
        return CourseSession.count;
    }

    public String getDepartment() {
        return department;
    }

    public String getNumber() {
        return number;
    }

    int getNumberOfStudent() {
        return students.size();
    }

    Student get(int index) {
        return students.get(index);
    }

    LocalDate getEndDate() {
        final int weeks = 16;
        final int daysFromFridayToMonday = 3;
        final int numberOfDays = weeks * 7 - daysFromFridayToMonday;
        return startDate.plusDays(numberOfDays);
    }

    LocalDate getStartDate() {
        return startDate;
    }
    public List<Student> getAllStudents() {
        return students;
    }

    public void enroll(Student student) {
        student.addCredits(this.credits);
        students.add(student);
    }
    /**
     * @param credits should be positive.
     */
    public void setCredits(int credits) {
        this.credits = credits;
    }

}
