package chess.controller.analyzer;

import chess.controller.PieceController;
import chess.domain.movement.Movement;
import chess.domain.piece.Piece;
import chess.repository.PieceRepository;

import java.util.Set;

/**
 * Created by nikitap4.92@gmail.com
 * 24.04.17.
 */
class BishopMovementAnalyzer extends PieceMovementAnalyzer {

    BishopMovementAnalyzer(PieceRepository pieceRepository) {
        super(pieceRepository);
    }

    void addMovements(Set<Movement> movements, Piece piece){
        crossBoardMovements(movements, piece, Directions::leftUp);
        crossBoardMovements(movements, piece, Directions::rightUp);
        crossBoardMovements(movements, piece, Directions::rightDown);
        crossBoardMovements(movements, piece, Directions::leftDown);
    }

}
