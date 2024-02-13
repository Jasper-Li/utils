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
               Type.Pawn, 1
        ));
        final var emptyPieces = new EnumMap<Type, Integer>(Type.class) ;
        assertEquals(onePawn, column.getTypeCount(Color.Black));
        assertEquals(emptyPieces, column.getTypeCount(Color.White));

        final var blank = column.getPiece(0);
        assertEquals(".", blank.toString());
        final var blackPawn = column.getPiece(6);
        assertEquals("P", blackPawn.toString());
        assertEquals(0, blackPawn.strength());

        assertEquals(1.0, column.getStrength(onePawn, Color.Black));
        assertEquals(1.0, blackPawn.strength());

        assertEquals(1.0, column.getStrength(Color.Black));
        assertEquals(0.0, column.getStrength(Color.White));
    }
}