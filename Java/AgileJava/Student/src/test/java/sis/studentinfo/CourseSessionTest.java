package sis.studentinfo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import static java.lang.System.out;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CourseSessionTest {
    private CourseSession session;
    private LocalDate startDate;

    @BeforeEach
    void setUp() {
        startDate = LocalDate.of(2003, 1, 6);
        session = new CourseSession("ENGL", "101", startDate);
    }
    @Test
    void comparable() {
        var sessionC = new CourseSession("CMSC", "200", startDate);
        var sessionE = new CourseSession("ENGL", "101", startDate);
        var sessionE2 = new CourseSession("ENGL", "201", startDate);
        assertEquals(0, session.compareTo(sessionE));

        assertTrue( 0 < "E".compareTo("C"));
        assertTrue(0 < session.compareTo(sessionC));
        assertTrue(sessionC.compareTo(session) < 0);
        assertTrue(session.compareTo(sessionE2) < 0);

    }

    @Test
    void count() {
        final var initialCount = 1;
        CourseSession.resetCount(initialCount);
        new CourseSession("ENGL", "101", startDate);
        assertEquals(initialCount + 1 , CourseSession.getCount());
        new CourseSession("ENGL", "101", startDate);
        assertEquals(initialCount + 2, CourseSession.getCount());
        new CourseSession("ENGL", "101", startDate);
        assertEquals(initialCount + 3, CourseSession.getCount());
    }
    @Test
    void create() {
        assertEquals("ENGL", session.getDepartment());
        assertEquals("101", session.getNumber());

        assertEquals(0, session.getNumberOfStudent());
    }
    @Test
    void enrollStudent() {
        final var sesstionCredits = 3;
        session.setCredits(sesstionCredits);

        var student1 = new Student("Cain Devoe");
        session.enroll(student1);
        assertEquals(1, session.getNumberOfStudent());
        assertEquals(student1, session.get(0));
        assertEquals(sesstionCredits, student1.getCredits());

        var student2 = new Student("Coralee, Devaghn");
        final var oldCreditsBase = 10;
        student2.addCredits(oldCreditsBase);
        session.enroll(student2);
        assertEquals(2, session.getNumberOfStudent());
        var newCredits2 = student2.getCredits();
        assertEquals(sesstionCredits, newCredits2 - oldCreditsBase);

        assertEquals(student1, session.get(0));
        assertEquals(student2, session.get(1));
    }
    @Test
    void courseDate() {
        assertEquals(startDate, session.getStartDate());

        final var endDate = LocalDate.of(2003, 4, 25);
        assertEquals(endDate, session.getEndDate());
    }

    /*
    @AfterEach
    void tearDown() {
    }
    */

}
