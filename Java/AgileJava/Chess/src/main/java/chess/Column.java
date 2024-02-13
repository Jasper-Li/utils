package chess;

import pieces.Color;
import pieces.Piece;
import pieces.Pieces;
import pieces.Type;
import static pieces.Type.*;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class Column {
    private final List<Piece> pieces;
    private static Map<Type, Double> typeToPoint = null;

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

    private static void initializeTypeToPoint(){
        typeToPoint = Map.of(
            Queen, 9.0,
            Rook, 5.0,
            Bishop, 3.0,
            Knight, 2.5,
            Pawn, 1.0
        );
    }
    public static double getPoint(EnumMap<Type, Integer> piecesCount, Type type){
        if(typeToPoint == null){
            initializeTypeToPoint();
        }
        var point = typeToPoint.getOrDefault(type, 0.0);
        if(type == Pawn && piecesCount.getOrDefault(type, 0) > 1) {
            point = 0.5;
        }
        return point;
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
