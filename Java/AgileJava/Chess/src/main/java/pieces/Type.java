package pieces;

import java.util.Map;
import static pieces.Type.Representation.*;

public enum Type {
    King(KING),
    Queen(QUEEN),
    Rook(ROOK),
    Bishop(BISHOP),
    Pawn(PAWN),
    Knight(KNIGHT),
    NoPiece(NO_PIECE);

    public static abstract class Representation {
        public static final char KING = 'k';
        public static final char QUEEN = 'q';
        public static final char ROOK = 'r';
        public static final char BISHOP = 'b';
        public static final char KNIGHT = 'n';
        public static final char PAWN = 'p';
        public static final char NO_PIECE = '.';
    }
    private static Map<Type, Double> typeToPoint = null;
    private static Map<Character, Type> representationToType = null;
    private final char representation;
    Type(char representation){
        this.representation = representation;
    }

    public static Type valueOf(char representation) {
        final var lowerCase = Character.toLowerCase(representation);
        if(representationToType == null){
            initRepresentationToType();
        }
        return representationToType.getOrDefault(lowerCase, NoPiece);
    }
    public char getRepresentation() {
        return representation;
    }
    public double getPoint() {
        if(typeToPoint == null) {
            initializeTypeToPoint();
        }
        return typeToPoint.getOrDefault(this, 0.0);
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
    private static void initRepresentationToType() {
        representationToType = Map.of(
                KING, King,
                QUEEN, Queen,
                ROOK, Rook,
                BISHOP, Bishop,
                KNIGHT, Knight,
                PAWN, Pawn
        );
    }
}

