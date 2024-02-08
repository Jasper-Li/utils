package chess;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import chess.Location;

class LocationTest {
    @Test
    void create() {
        record LocationCheck(String repr, int column, int rank){}
        final LocationCheck[] checks = {
            new LocationCheck("a8", 0, 8),
            new LocationCheck("a1", 0, 1),
            new LocationCheck("h8", 7, 8),
            new LocationCheck("e1", 4, 1),
        };
        for (final var check : checks) {
            final var locationExpect = new Location(check.column, check.rank);
            assertEquals(check.column, locationExpect.column());
            assertEquals(check.rank, locationExpect.rank());
            assertEquals(locationExpect, new Location(check.repr));
        }
    }
}