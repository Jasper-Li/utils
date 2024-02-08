package chess;

import pieces.Type;

import java.util.ArrayList;
import java.util.List;

public class BackRankArrangement implements  Arrangement{
    @Override
    public List<Type> names() {
        return new ArrayList<>(List.of(
            Type.Rook, Type.Knight, Type.Bishop, Type.Queen,
            Type.King, Type.Bishop, Type.Knight, Type.Rook
        ));
    }
}
