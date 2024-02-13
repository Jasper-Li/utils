package pieces;

import org.junit.jupiter.api.Test;
import static   java.lang.System.out;

import static org.junit.jupiter.api.Assertions.*;

class TypeTest {
    @Test
    void create() {
        record Check(char representation, Type type){};
        final Check[] checks = {
            new Check('k', Type.King),
            new Check('q', Type.Queen),
            new Check('r', Type.Rook),
            new Check('b', Type.Bishop),
            new Check('n', Type.Knight),
            new Check('p', Type.Pawn),
            new Check('K', Type.King),
            new Check('Q', Type.Queen),
            new Check('R', Type.Rook),
            new Check('B', Type.Bishop),
            new Check('N', Type.Knight),
            new Check('P', Type.Pawn),
        };
        for (var check : checks) { //var check is record, no need final.
            assertEquals(check.type(), Type.valueOf(check.representation()));
        }

    }

    @Test
    void getPoint() {
        record Check(Type type, Double point){};
        final Check[] checks = {
            new Check(Type.Queen, 9.0),
            new Check(Type.Rook, 5.0),
            new Check(Type.Bishop, 3.0),
            new Check(Type.Knight, 2.5),
            new Check(Type.Pawn, 1.0),
        };
        for (var check : checks) {
            assertEquals(check.point, check.type.getPoint());
        }
    }
}