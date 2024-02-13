package chess;

import java.net.InetAddress;
import java.util.Map;

public enum ColumnIndex {
    A(0),
    B(1),
    C(2),
    D(3),
    E(4),
    F(5),
    G(6),
    H(7),
    INVALID(-1);

    private int internalIndex;
    ColumnIndex(int internalIndex) {
        this.internalIndex = internalIndex;
    }
    boolean isValid() {
        return this != INVALID;
    }
    public int getInternalIndex() {
        return internalIndex;
    }
    public ColumnIndex increment() {
        return switch(this){
            case A -> B;
            case B -> C;
            case C -> D;
            case D -> E;
            case E -> F;
            case F -> G;
            case G -> H;
            case H, INVALID -> INVALID;
            default -> INVALID;
        };
    }

    private static Map<Character, ColumnIndex> representationToObject = null;
    static ColumnIndex of(Character representation) {
        if(representationToObject == null){
            initRepresentationToObject();
        }
        return representationToObject.getOrDefault(
            Character.toLowerCase(representation),
            INVALID
        );
    }
    private static void initRepresentationToObject() {
        representationToObject = Map.of(
            'a', A,
            'b', B,
            'c', C,
            'd', D,
            'e', E,
            'f', F,
            'g', G,
            'h', H
        );
    }
}
