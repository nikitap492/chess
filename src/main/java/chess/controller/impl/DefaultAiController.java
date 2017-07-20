package chess.controller.impl;

import chess.controller.AiController;
import chess.controller.MovementController;
import chess.controller.analyzer.MovementValueAnalyzer;
import chess.domain.movement.Movement;

/**
 * @author Poshivalov N.A.
 * @since 18.07.2017.
 */
public class DefaultAiController implements AiController {

    private final MovementController movementController;
    private final MovementValueAnalyzer valueAnalyzer;

    public DefaultAiController(MovementController movementController) {
        this.movementController = movementController;
        this.valueAnalyzer = new MovementValueAnalyzer(movementController);
    }

    @Override
    public void doMove() {
        Movement movement = valueAnalyzer.lowValue();
        movementController.doMove(movement);
    }
}
