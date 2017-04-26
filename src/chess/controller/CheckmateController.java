package chess.controller;

import chess.domain.movement.Movement;
import chess.domain.piece.PieceColor;

/**
 * @author Poshivalov Nikita
 * @since 20.04.2017.
 */
public interface CheckmateController {

    void check();

    boolean isCheck();

    boolean isNonCheck(Movement movement, PieceColor color);

    boolean isCheckmate();
}
