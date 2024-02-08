package pieces;

public enum Type {
    King,
    Queen,
    Rook,
    Bishop,
    Pawn,
    Knight,
    NoPiece;

    public static final char KING_REPRESENTATION = 'k';
    public static final char QUEEN_REPRESENTATION = 'q';
    public static final char ROOK_REPRESENTATION = 'r';
    public static final char BISHOP_REPRESENTATION = 'b';
    public static final char KNIGHT_REPRESENTATION = 'n';
    public static final char PAWN_REPRESENTATION = 'p';
    public static Type valueOf(char representation) {
        return switch (Character.toLowerCase(representation)) {
            case KING_REPRESENTATION -> Type.King;
            case QUEEN_REPRESENTATION -> Type.Queen;
            case ROOK_REPRESENTATION -> Type.Rook;
            case BISHOP_REPRESENTATION -> Type.Bishop;
            case KNIGHT_REPRESENTATION -> Type.Knight;
            case PAWN_REPRESENTATION -> Type.Pawn;
            default -> Type.NoPiece;
        };
    }
    public char toChar() {
        return switch (this){
            case King, Queen, Rook, Bishop, Pawn -> Character.toLowerCase(this.name().charAt(0));
            case Knight -> 'n';
            default -> '.';
        };
    }
}

