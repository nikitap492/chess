package chess.controller.analyzer;

import chess.domain.movement.Movement;
import chess.domain.piece.PieceType;

import java.util.Comparator;

/**
 * @author Poshivalov N.A.
 * @since 18.07.2017.
 */
public class MovementValueAnalyzer {
    private final MovementAnalyzer analyzer;

    public MovementValueAnalyzer(MovementAnalyzer analyzer) {
        this.analyzer = analyzer;
    }

    private static int relativeValue(PieceType pieceType){
        switch (pieceType){
            case PAWN:
                return 1;
            case KNIGHT:
                return 3;
            case BISHOP:
                return 3;
            case ROOK:
                return 5;
            case QUEEN:
                return 9;
            case KING:
                return 999;
        }
        return 0;
    }

    public Movement lowValue(){
        return analyzer.possible()
                .max(new MovementComparator())
                .orElseThrow(RuntimeException::new);
    }


    private static class MovementComparator implements Comparator<Movement>{

        @Override
        public int compare(Movement o1, Movement o2) {
            int first = o1.getKilled().isPresent() ? relativeValue(o1.getKilled().get().getType()) : 0;
            int second = o2.getKilled().isPresent() ? relativeValue(o2.getKilled().get().getType()) : 0;

            return first - second;
        }
    }
    
}
