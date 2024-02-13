package chess;

import org.junit.jupiter.api.Test;
import pieces.Color;
import pieces.Type;

import java.util.Map;
import java.util.EnumMap;

import static org.junit.jupiter.api.Assertions.*;

class ColumnTest {
    @Test
    void getPieces() {
        final var column = new Column("......P.");
        final EnumMap<Type, Integer> onePawn = new EnumMap<>(Map.of(
               Type.PAWN, 1
        ));
        final var emptyPieces = new EnumMap<Type, Integer>(Type.class) ;
        assertEquals(onePawn, column.getTypeCount(Color.BLACK));
        assertEquals(emptyPieces, column.getTypeCount(Color.WHITE));

        final var blank = column.getPiece(0);
        assertEquals(".", blank.toString());
        final var blackPawn = column.getPiece(6);
        assertEquals("P", blackPawn.toString());
        assertEquals(0, blackPawn.strength());

        assertEquals(1.0, column.getStrength(onePawn, Color.BLACK));
        assertEquals(1.0, blackPawn.strength());

        assertEquals(1.0, column.getStrength(Color.BLACK));
        assertEquals(0.0, column.getStrength(Color.WHITE));
    }
}