import java.util.stream.DoubleStream;

public class Calculator {
    static double add(double... operands) {
        return DoubleStream.of(operands)
                .sum();
    }

}
