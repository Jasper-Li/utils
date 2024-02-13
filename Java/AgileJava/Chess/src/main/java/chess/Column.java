package chess;

import pieces.Color;
import pieces.Piece;
import pieces.Pieces;
import pieces.Type;

import java.util.EnumMap;
import java.util.List;

public class Column {
    private final List<Piece> pieces;

    Column(List<Piece> pieces) {
        this.pieces = pieces;
    }

    public Column(String representation) {
        pieces  = Pieces.createFrom(representation);
    }


    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Column that) {
            return pieces.equals(that.pieces);
        }
        return false;
    }

    public EnumMap<Type, Integer> getTypeCount(Color color) {
        EnumMap<Type, Integer> map = new EnumMap<>(Type.class);
        for(var piece : pieces) {
            if (piece.isEmpty() || piece.color() != color) continue;
            final var type = piece.type();
            var count = map.getOrDefault(type, 0);
            ++count;
            map.put(type, count);
        }
        return map;
    }

    public static double getPoint(EnumMap<Type, Integer> piecesCount, Type type){
        return switch (type) {
            case Queen -> 9;
            case Rook -> 5;
            case Bishop -> 3;
            case Knight -> 2.5;
            case Pawn -> {
                final var count = piecesCount.getOrDefault(type, 0);
                yield  count > 1 ? 0.5 : 1;
            }
            default -> 0;
        };
    }
    public double getStrength(EnumMap<Type, Integer> piecesCount, Color color) {
        var points = 0.0;
        for(var piece : this.pieces) {
            if (piece.isEmpty() || piece.color() != color)  continue;
            final var point = getPoint(piecesCount, piece.type());
            piece.setStrength(point);
            points += point;
        }
        return points;
    }
    public double getStrength(Color color) {
        return getStrength(getTypeCount(color), color);
    }
    /**
     *
     * @param index, count from 0.
     * @return piece at index.
     */
    Piece getPiece(int index) {
        return pieces.get(index);
    }
}
