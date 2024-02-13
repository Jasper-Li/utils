package chess;

/**
 * Location on Board, represented by 2 characters: column rank,
 * @param column, 'a' - 'h'
 * @param rank, 1 - 8
 */
public record Location(ColumnIndex column, RankIndex rank) {
//    public Location(int column, int rank) {
//        this.column = column; // starts from 0
//        this.rank = rank;     // starts from 1
//    }
    public Location(String representation){
        this(ColumnIndex.of(Character.toLowerCase(representation.charAt(0))),
            RankIndex.of(representation.charAt(1))
        );
    }
    public boolean isValid() {
        return column != ColumnIndex.INVALID && rank != RankIndex.INVALID;
    }
}
