package chess.controller.analyzer;

import chess.controller.CheckmateController;
import chess.controller.PieceController;
import chess.domain.movement.Movement;
import chess.domain.piece.Piece;

import java.util.Set;

/**
 * Created by nikitap4.92@gmail.com
 * 24.04.17.
 */
class KingMovementAnalyzer extends PieceMovementAnalyzer {

    KingMovementAnalyzer(PieceController pieceController, CheckmateController checkmateController) {
        super(pieceController, checkmateController);
    }

    @Override
    void addMovements(Set<Movement> empty, Piece piece) {
        addMovement(empty, piece, Directions::up);
        addMovement(empty, piece, Directions::left);
        addMovement(empty, piece, Directions::leftUp);
        addMovement(empty, piece, Directions::leftDown);
        addMovement(empty, piece, Directions::rightUp);
        addMovement(empty, piece, Directions::rightDown);
        addMovement(empty, piece, Directions::right);
        addMovement(empty, piece, Directions::down);
    }

}
