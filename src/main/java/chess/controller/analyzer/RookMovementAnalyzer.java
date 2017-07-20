package chess.controller.analyzer;

import chess.domain.movement.Movement;
import chess.domain.piece.Piece;

import java.util.Set;

/**
 * Created by nikitap4.92@gmail.com
 * 24.04.17.
 */
class RookMovementAnalyzer extends PieceMovementAnalyzer {

    RookMovementAnalyzer(MovementAnalyzeGround analyzeGround) {
        super(analyzeGround);
    }

    @Override
    void addMovements(Set<Movement> empty, Piece piece) {
        crossBoardMovements(empty, piece, Directions::up);
        crossBoardMovements(empty, piece, Directions::left);
        crossBoardMovements(empty, piece, Directions::right);
        crossBoardMovements(empty, piece, Directions::down);
    }



}