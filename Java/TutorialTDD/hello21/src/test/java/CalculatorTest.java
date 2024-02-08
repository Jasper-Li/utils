import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class CalculatorTest {

    private int getSum(String a, String b) {
        try {
            return Integer.parseInt(a) / Integer.parseInt(b);
        } catch (NumberFormatException e) {
            System.out.println("Input format error" + e.getMessage());
            return 0;
        } catch (ArithmeticException e) {
            System.out.println("Not divide by 0" + e.getMessage());
            return 0;
        }
    }
    @Test
    void sumByStr() {
        assertEquals(8, getSum("16", "2"));
        assertEquals(0, getSum("abc", "2"));
        assertEquals(0, getSum("16", "0"));
    }
    @Test
    void add() {
        assertEquals(2, Calculator.add(1, 1));
    }
}