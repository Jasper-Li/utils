package chess;

import pieces.Piece;
import util.StringUtil;

import java.util.List;

public class Printer {
    public static String print(Board board) {
        if(board.rankCount() != Board.RANK_COUNT) return "";
        StringBuilder buffer = new StringBuilder();
        for(int i = Board.RANK_COUNT; i > 0; --i) {
            var rank = board.getRank(i).toString();
            var line = StringUtil.appendNewLine(rank);
            buffer.append(line);
        }
        return buffer.toString();
    }

    public static String printByStringConcatenation(Board board) {
        if(board.rankCount() != Board.RANK_COUNT) return "";
        String result = new String();
        for(int i = Board.RANK_COUNT; i > 0; --i) {
            var rank = board.getRank(i).toString();
            var line = StringUtil.appendNewLine(rank);
            result += line;
        }
        return result;
    }

    public static void print(final List<Piece> pieces){
        for (final var piece : pieces) {
            System.out.println(STR."\{piece} strength: \{piece.strength()}");
        }
    }
}
