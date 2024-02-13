package pieces;

import org.junit.jupiter.api.Test;

import java.time.Period;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.jupiter.api.Assertions.*;

class PieceTest {
    @Test
    void create() {
        final var blank = new Piece();
        assertTrue(blank.isEmpty());
        assertEquals(Color.None, blank.color());
        assertEquals(Type.NoPiece, blank.type());
        assertEquals(".", blank.toString());

        record CreateCheck(Type type, char representation){}
        CreateCheck[] checks = {
            new CreateCheck(Type.King, Type.Representation.KING),
            new CreateCheck(Type.Queen, Type.Representation.QUEEN),
            new CreateCheck(Type.Rook, Type.Representation.ROOK),
            new CreateCheck(Type.Bishop, Type.Representation.BISHOP),
            new CreateCheck(Type.Knight, Type.Representation.KNIGHT),
            new CreateCheck(Type.Pawn, Type.Representation.PAWN),
        };
        for (final var check : checks){
            verifyCreate(check.type(), check.representation());
        }
    }
    private void verifyCreate(Type type, Character representation){
        final var whitePiece = new Piece(Color.White, type);
        assertTrue(whitePiece.isWhite());
        assertEquals(representation.toString(), whitePiece.toString());
        assertEquals(type, whitePiece.type());
        assertFalse(whitePiece.isEmpty());

        final var blackPiece = new Piece(Color.Black, type);
        assertTrue(blackPiece.isBlack());
        assertEquals(type, blackPiece.type());
        assertEquals(Character.toUpperCase(representation), blackPiece.toString().charAt(0));
        assertFalse(blackPiece.isEmpty());
    }
    @Test
    void createWithPiecesCount() {
        Piece.resetPiecesCount(0, 0);
        assertEquals(0, Piece.getBlackPiecesCount());
        assertEquals(0, Piece.getWhitePiecesCount());

        final var blackPawn = new Piece(Color.Black, Type.Pawn);
        assertEquals(1, Piece.getBlackPiecesCount());

        final var whiteKnight = new Piece(Color.White, Type.Knight);
        assertEquals(1, Piece.getWhitePiecesCount());
    }
    @Test
    void createByRepresentation() {
        record RepresentationCreationCheck(Character repr, Color color, Type type){}
        final RepresentationCreationCheck[] checks = {
                new RepresentationCreationCheck('.', Color.None, Type.NoPiece),
                new RepresentationCreationCheck('k', Color.White, Type.King),
                new RepresentationCreationCheck('q', Color.White, Type.Queen),
                new RepresentationCreationCheck('r', Color.White, Type.Rook),
                new RepresentationCreationCheck('b', Color.White, Type.Bishop),
                new RepresentationCreationCheck('n', Color.White, Type.Knight),
                new RepresentationCreationCheck('p', Color.White, Type.Pawn),

                new RepresentationCreationCheck('K', Color.Black, Type.King),
                new RepresentationCreationCheck('Q', Color.Black, Type.Queen),
                new RepresentationCreationCheck('R', Color.Black, Type.Rook),
                new RepresentationCreationCheck('B', Color.Black, Type.Bishop),
                new RepresentationCreationCheck('N', Color.Black, Type.Knight),
                new RepresentationCreationCheck('P', Color.Black, Type.Pawn),
                // invalid
                new RepresentationCreationCheck('Z', Color.None, Type.NoPiece),
        };
        for (var check : checks) {
            final var pieceExpected = new Piece(check.color(), check.type());
            assertEquals(pieceExpected, new Piece(check.repr));
        }
    }

    @Test
    void compareTo() {
        final var p1 = new Piece().setStrength(1.0);
        final var p2 = new Piece().setStrength(2.0);
        final var status = p1.compareTo(p2);
        assertThat(status, lessThan(0));
        assertThat(p2.compareTo(p1), greaterThan(0));
    }
}