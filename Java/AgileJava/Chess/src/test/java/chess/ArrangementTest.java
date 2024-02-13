package chess;

import org.junit.jupiter.api.Test;
import pieces.Type;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ArrangementTest {

    @Test
    void names() {
        final List<Type> secondRank = new SecondRankArrangement().names();
        assertEquals(8, secondRank.size());
        for (var name : secondRank) {
            assertEquals(Type.Pawn, name);
        }

        final List<Type> backRank = new BackRankArrangement().names();
        assertEquals(8, backRank.size());
        assertEquals(Type.Rook, backRank.getFirst());
        assertEquals(Type.Rook, backRank.getLast());
    }
}