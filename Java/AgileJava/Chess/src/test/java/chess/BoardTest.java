package chess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pieces.Color;
import pieces.Piece;
import pieces.Type;
import util.StringUtil;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static util.StringUtil.NEW_LINE;

public class BoardTest {
    private static final String boardRepresentationTest_5_4 = util.System.isWindows() ?
            """ 
            .KR.....\r
            P.PB....\r
            .P..Q...\r
            ........\r
            .....nq.\r
            .....p..\r
            ......p.\r
            ....rk..\r
            """
            :
            """
            .KR.....
            P.PB....
            .P..Q...
            ........
            .....nq.
            .....p..
            ......p.
            ....rk..
            """;

    private Board board;
    @BeforeEach
    void setUp() {
        board = new Board();
    }
    @Test
    void create() {
        assertEquals(Board.RANK_COUNT, board.rankCount());
        assertEquals(0, board.pieceCount());

        Piece.resetPiecesCount(0, 0);
        board.initialize();
        Rank rank2 = board.getRank(Board.WHITE_SECOND_RANK_INDEX_ON_BOARD);
        assertEquals("pppppppp", rank2.toString());
        assertEquals(
                "PPPPPPPP",
                board.getRank(Board.BLACK_SECOND_RANK_INDEX_ON_BOARD).toString());

        assertEquals(32, board.pieceCount());
        assertEquals(16, Piece.getBlackPiecesCount());
        assertEquals(16, Piece.getWhitePiecesCount());

        board = new Board(boardRepresentationTest_5_4);
        assertEquals(boardRepresentationTest_5_4, Printer.print(board));
    }
    @Test
    void createByRepresentationWithDecoration() {
        final var representation = """
                . K R . . . . . 8
                P . P B . . . . 7
                . P . . Q . . . 6
                . . . . . . . . 5
                . . . . . n q . 4
                . . . . . p . p 3
                . . . . . p p . 2
                . . . . r k . . 1
                a b c d e f g h
                """;
        final var printExpected = util.System.isWindows() ?
            """
            .KR.....\r
            P.PB....\r
            .P..Q...\r
            ........\r
            .....nq.\r
            .....p.p\r
            .....pp.\r
            ....rk..\r
            """
                :
            """
            .KR.....
            P.PB....
            .P..Q...
            ........
            .....nq.
            .....p.p
            .....pp.
            ....rk..
            """;
        board = new Board(representation);
        assertEquals(printExpected, Printer.print(board));
    }

    @Test
    void countPiece() {
        board = new Board(boardRepresentationTest_5_4);
        var blackPawnCountExpected = 3;
        assertEquals(blackPawnCountExpected, board.count(new Piece(Color.Black, Type.Pawn)));
    }
    @Test
    void retrievePiece() {
        board.initialize();
        var blackRook = new chess.Location("a8");
        var whiteQueen = new chess.Location("e1");
        assertEquals(new Piece(Color.Black, Type.Rook), board.getPieceBy(blackRook));
        assertEquals(new Piece(Color.White, Type.King), board.getPieceBy(whiteQueen));
    }

    @Test
    void placePiece() {
        record PlacePieceCheck(Piece piece, Location location){};
        var blackKing = new Piece(Color.Black, Type.King);
        var blackRook = new Piece(Color.Black, Type.Rook);
        var whiteKing = new Piece(Color.White, Type.King);
        PlacePieceCheck[] checks = {
            new PlacePieceCheck(blackKing, new Location("b6")),
            new PlacePieceCheck(blackRook, new Location("b5")),
            new PlacePieceCheck(whiteKing, new Location("c4")),
        };
        int pieceCount = 0;
        assertEquals(pieceCount, board.pieceCount());
        for (var check: checks) {
            board.placePiece(check.piece(), check.location());
            ++pieceCount;
            assertEquals(pieceCount, board.pieceCount());
            assertEquals(check.piece(), board.getPieceBy(check.location()));
        }

        final var boardRepresentation =
            "........" + NEW_LINE +
            "........" + NEW_LINE +
            ".K......" + NEW_LINE +
            ".R......" + NEW_LINE +
            "..k....." + NEW_LINE +
            "........" + NEW_LINE +
            "........" + NEW_LINE +
            "........" + NEW_LINE;
        assertEquals(boardRepresentation, Printer.print(board));
    }
    @Test
    void countPoints() {
        var representation = """
                . K R . . . . . 8
                P . P B . . . . 7
                . P . . Q . . . 6
                . . . . . . . . 5
                . . . . . n q . 4
                . . . . . p . p 3
                . . . . . p p . 2
                . . . . r k . . 1
                a b c d e f g h
                """;
        board = new Board(representation);
        final Column column0 = board.getColumn(0);
        final Column columnExpected = new Column("......P.");
        assertEquals(columnExpected, column0);

        final var blackKing = board.getPieceBy(new Location("b8"));
        assertEquals("K", blackKing.toString());
        assertEquals(0.0, blackKing.strength());
        final var blackBishop = board.getPieceBy(new Location("d7"));
        assertEquals("B", blackBishop.toString());
        assertEquals(0.0,blackBishop.strength());

        final var blackPoints = 20.0;
        final var whitePoints = 19.5;
        assertEquals(whitePoints, board.getStrength(Color.White));
        assertEquals(blackPoints, board.getStrength(Color.Black));

        assertEquals(0.0, blackKing.strength());
        assertEquals(3.0,blackBishop.strength());

        // for getPieces
        final var piecesWhite = board.getPieces(Color.White);
        assertEquals(8, piecesWhite.size());
        final var piecesBlack = board.getPieces(Color.Black);
        assertEquals(7, piecesBlack.size());

        isSorted(piecesWhite);
        isSorted(piecesBlack);
    }
    private void isSorted(List<Piece> pieces) {
        final var size =  pieces.size();
        for(int i = 0; i<size-1; ++i) {
            final var piece1 = pieces.get(i);
            final var piece2 = pieces.get(i+1);
            final var strength1 = piece1.strength();
            final var strength2 = piece2.strength();
            assertTrue(
                strength1 >= strength2,
                    STR."Expect \{strength1} of \{piece1} >=  \{strength2} of \{piece2}"
            );
        }
    }

}