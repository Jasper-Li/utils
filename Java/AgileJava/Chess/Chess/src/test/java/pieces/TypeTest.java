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

}