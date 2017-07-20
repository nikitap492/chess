package chess.controller.analyzer;

import chess.domain.movement.Movement;

/**
 * @author Poshivalov N.A.
 * @since 18.07.2017.
 */
public class MovementValueAnalyzer {
    private final MovementAnalyzer analyzer;

    public MovementValueAnalyzer(MovementAnalyzer analyzer) {
        this.analyzer = analyzer;
    }



    public Movement lowValue(){
        return analyzer.aiMovement();
    }



    
}
