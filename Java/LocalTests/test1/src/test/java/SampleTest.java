import org.junit.jupiter.api.Test;
import static java.lang.System.out;

public class SampleTest {
    enum Days{
        Day1,
        Day2,
        Day3,
    }
    void testDay(Days days)  {
        int v = 0;
        switch(days) {
            case Day1:  v=1;break;

        }
        out.println(STR."v: \{v}");
    }
    @Test
    void testSwitch() {
        testDay(Days.Day1);
    }
}
