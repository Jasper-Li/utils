package chess;

/**
 * Location on Board, represented by 2 characters: column rank,
 * @param column, 'a' - 'h'
 * @param rank, 1 - 8
 */
public record Location(int column, int rank) {
    public Location(int column, int rank) {
        this.column = column; // starts from 0
        this.rank = rank;     // starts from 1
    }
    public Location(String representation){
        this(Character.toLowerCase(representation.charAt(0)) - 'a',
            Character.digit(representation.charAt(1), 10)
        );
    }
}
