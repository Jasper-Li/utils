package chess;

import org.junit.jupiter.api.Test;
import pieces.Color;
import pieces.Type;
import pieces.Piece;

import static org.junit.jupiter.api.Assertions.*;

class RankTest {
    private void checkPieceStaticCount(int black, int white) {
        assertEquals(black, Piece.getBlackPiecesCount());
        assertEquals(white, Piece.getWhitePiecesCount());
    }
    @Test
    void create() {
        Piece.resetPiecesCount(0, 0);
        checkPieceStaticCount(0, 0);
        final var blank = new Rank();
        assertEquals(0, blank.countValidPieces());
        checkPieceStaticCount(0, 0);

        final var whitePawn = new Piece(Color.WHITE, Type.PAWN);
        checkPieceStaticCount(0, 1);
        final var blackPawn = new Piece(Color.BLACK, Type.PAWN);
        checkPieceStaticCount(1, 1);
    }
    @Test
    void createByArrangement() {
        final var backRank = new Rank(Color.BLACK, new BackRankArrangement());
        final var secondRank = new Rank(Color.WHITE, new SecondRankArrangement());

        assertEquals("RNBQKBNR", backRank.toString());
        assertEquals("pppppppp", secondRank.toString());
    }
    @Test
    void createByRepresentation() {
        final var emptyRankRepresentation =  "........";
        final var emptyRank = new Rank(emptyRankRepresentation);
        assertEquals(0, emptyRank.countValidPieces());
        assertEquals(emptyRankRepresentation, emptyRank.toString());

        final var rank1Representation = ".KR.....";
        final var rank1 = new Rank(rank1Representation);
        assertEquals(2, rank1.countValidPieces());
        assertEquals(new Piece(Color.BLACK, Type.KING), rank1.getPiece(1));
        assertEquals(new Piece(Color.BLACK, Type.ROOK), rank1.getPiece(2));
        final var rank2Representation = ". K R . . . . . \r";
        final var rank2  = new Rank(rank2Representation);
        assertEquals(rank1, rank2);
        final var rank3Representation = ". K R . . . . . 4";
        assertEquals(rank1, new Rank(rank3Representation));
    }

    @Test
    void count() {
        record CountCheck(Color color, Type type, int countExpected) {}
        final var rank = new Rank(Color.BLACK, new BackRankArrangement());
        CountCheck[] checks = {
            new CountCheck(Color.BLACK, Type.KING, 1),
            new CountCheck(Color.BLACK, Type.QUEEN, 1),
            new CountCheck(Color.BLACK, Type.ROOK, 2),
            new CountCheck(Color.BLACK, Type.KNIGHT, 2),
            new CountCheck(Color.BLACK, Type.BISHOP, 2),
            new CountCheck(Color.BLACK, Type.PAWN, 0),
            new CountCheck(Color.WHITE, Type.KNIGHT, 0)
        };
        for (final var check : checks) {
            final var piece = new Piece(check.color(), check.type());
            assertEquals(check.countExpected(), rank.count(piece), "Failed to check " + piece.toString());
        }
    }

    @Test
    void placePiece() {
        final var rank = new Rank();
        final Piece[] pieces = {
            new Piece(Color.BLACK, Type.KING),
            new Piece(Color.WHITE, Type.QUEEN),
        };
        var count = 0;
        assertEquals(count, rank.countValidPieces());
        var columnIdx = 1;
        for (final var p : pieces) {
            rank.placePiece(p, columnIdx);
            ++count;
            assertEquals(count, rank.countValidPieces());
            assertEquals(p, rank.getPiece(columnIdx));
            ++columnIdx;
        }
        --columnIdx;
        rank.placePiece(pieces[0], columnIdx);
        assertEquals(count, rank.countValidPieces());
        assertEquals(pieces[0], rank.getPiece(columnIdx));
    }

    @Test
    void getPieces() {
        final var rank = new Rank(". K R . . . p . 8");
        final var piecesBlack = rank.getPieces(Color.BLACK);
        assertEquals(2, piecesBlack.size());
        final var piecesWhite = rank.getPieces(Color.WHITE);
        assertEquals(1, piecesWhite.size());
    }
}