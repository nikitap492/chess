package chess.controller;

import chess.domain.movement.Movement;

/**
 * @author Poshivalov Nikita
 * @since 20.04.2017.
 */
public interface CheckmateController {

    void setMovementController(MovementController movementController);

    boolean isNonCheck(Movement movement);

    boolean isCheck();

    boolean isCheckmate();

    boolean isDraw();
}
