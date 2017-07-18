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
class KnightMovementAnalyzer extends PieceMovementAnalyzer {

    KnightMovementAnalyzer(PieceRepository pieceRepository) {
        super(pieceRepository);
    }

    @Override
    void addMovements(Set<Movement> empty, Piece piece) {
        addMovement(empty, piece, Directions::northEastEast);
        addMovement(empty, piece, Directions::northNorthEast);
        addMovement(empty, piece, Directions::northNorthWest);
        addMovement(empty, piece, Directions::northWestWest);
        addMovement(empty, piece, Directions::southEastEast);
        addMovement(empty, piece, Directions::southWestWest);
        addMovement(empty, piece, Directions::southSouthEast);
        addMovement(empty, piece, Directions::southSouthWest);
    }
}
