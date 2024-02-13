package pieces;

public class Piece implements Comparable<Piece>{
    private static int whitePiecesCount = 0;
    private static int blackPiecesCount = 0;
    private final Color color;
    private final Type type;
    private double strength = 0;
    public static void resetPiecesCount(int white, int black) {
        whitePiecesCount = white;
        blackPiecesCount = black;
    }
    public static int getWhitePiecesCount() {
        return whitePiecesCount;
    }
    public static int getBlackPiecesCount() {
        return blackPiecesCount;
    }

    /**
     * blank piece. Type.NoPiece
     */
    public Piece(){
        this(Color.None, Type.NoPiece);
    }

    public Piece(Character representation) {
        type = Type.valueOf(representation);
        if (type == Type.NoPiece){
            color = Color.None;
        } else if (Character.isUpperCase(representation)) {
            color = Color.Black;
        } else {
            color = Color.White;
        }
        updateStaticWhiteBlackCount();
    }
    public Piece(Color color, Type type) {
        this.color = color;
        this.type = type;
        updateStaticWhiteBlackCount();
    }
    private void updateStaticWhiteBlackCount(){
        switch (color){
            case Color.White -> ++whitePiecesCount;
            case Color.Black -> ++blackPiecesCount;
        };
    }
    @Override
    public boolean equals(Object o){
        return o instanceof Piece other &&
            color == other.color && type == other.type;
    }
    public boolean isBlack() {
        return color == Color.Black;
    }
    public boolean isWhite() {
        return color == Color.White;
    }
    public boolean isEmpty() {
        return type == Type.NoPiece;
    }
    public Color color() {return color;}
    public Type type() {return type;}
    public String toString() {
        return Character.toString(toChar());
    }
    public char toChar() {
        if (type == Type.NoPiece) return '.';
        final var name = type.getRepresentation();
        return color == Color.Black?
                Character.toUpperCase(name) : name;
    }

    public double strength() {
        return strength;
    }

    public Piece setStrength(double strength) {
        this.strength = strength;
        return this;
    }

    @Override
    public int compareTo(Piece o) {
        return Double.compare(strength(), o.strength());
    }
}