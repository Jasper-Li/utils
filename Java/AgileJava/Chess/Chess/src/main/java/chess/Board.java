package chess;

import pieces.Color;
import pieces.Piece;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Board {
    public static final int RANK_COUNT = 8;
    public static final int COLUMN_COUNT = 8;
    public static final int WHITE_BACK_RANK_INDEX_ON_BOARD = 1;
    public static final int WHITE_SECOND_RANK_INDEX_ON_BOARD = 2;
    public static final int BLACK_SECOND_RANK_INDEX_ON_BOARD = 7;
    public static final int BLACK_BACK_RANK_INDEX_ON_BOARD = 8;
    private final List<Rank> ranks = new ArrayList<Rank>(RANK_COUNT);
    public Board() {
        for (int i = 0; i < RANK_COUNT; ++i) {
            ranks.add(new Rank());
        }
    }

    public Board(String boardRepresentation){
        String[] lines = boardRepresentation.split("\\r?\n");
        var count = lines.length;
        var valid_count = Math.min(count, RANK_COUNT);
        for(int i = valid_count - 1; i >=0; --i) {
            ranks.add(new Rank(lines[i]));
        }
    }
    public Board initialize() {
        for(int i = 0; i < RANK_COUNT; ++i) {
            var boardIndex = i + 1;
            switch (boardIndex) {
                case WHITE_BACK_RANK_INDEX_ON_BOARD -> {
                   ranks.set(i, new Rank(Color.White, new BackRankArrangement()));
                }
                case WHITE_SECOND_RANK_INDEX_ON_BOARD -> {
                    ranks.set(i, new Rank(Color.White, new SecondRankArrangement()));
                }
                case BLACK_SECOND_RANK_INDEX_ON_BOARD -> {
                    ranks.set(i, new Rank(Color.Black, new SecondRankArrangement()));
                }
                case BLACK_BACK_RANK_INDEX_ON_BOARD -> {
                    ranks.set(i, new Rank(Color.Black, new BackRankArrangement()));
                }
                default -> ranks.set(i, new Rank());
            };
        }
        return this;
    }
    public int rankCount() {
        return ranks.size();
    }
    public int pieceCount() {
        int count = 0;
        for (var rank : ranks) {
            count += rank.countValidPieces();
        }
        return count;
    }
    public Rank getRank(int boardIndex) {
        final var listIndex = boardIndex - 1;
        return ranks.get(listIndex);
    }

    public int count(Piece piece) {
        int count = 0;
        for (var rank : ranks) {
            count += rank.count(piece);
        }
        return count;
    }

    public Piece getPieceBy(chess.Location location) {
        final int column = location.column();
        final int rank = location.rank();
        return getRank(rank).getPiece(column);
    }

    public void placePiece(Piece piece, Location location) {
        final int column = location.column();
        final int rank = location.rank();
        getRank(rank).placePiece(piece, column);
    }

    /**
     * @param index: count from 0.
     */
    public Column getColumn(int index) {
        List<Piece> pieces = new ArrayList<Piece>();
        for (var rank : ranks) {
            pieces.add(rank.getPiece(index));
        }
        return new Column(pieces);
    }

    public double getStrength(Color color) {
        double points = 0;
        for(int i =0 ; i< COLUMN_COUNT; ++i)  {
            final var column = getColumn(i);
            final var point = column.getStrength(color);
            points += point;
        }
        return points;
    }
    public List<Piece> getPieces(Color color){
        List<Piece> pieces = new LinkedList<>();
        for (final var rank : ranks)  {
            final var some = rank.getPieces(color);
            pieces.addAll(some);
        }
        pieces.sort(Collections.reverseOrder());
        return pieces;
    }
}