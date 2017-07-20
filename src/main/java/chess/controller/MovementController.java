package chess.controller;

import chess.controller.analyzer.MovementAnalyzer;
import chess.domain.cell.Cell;
import chess.domain.movement.Movement;
import chess.domain.piece.Piece;

import java.util.Set;

/**
 * @author Poshivalov Nikita
 * @since 20.04.2017.
 */
public interface MovementController {

    void undo();

    void doMove(Movement movement);

    Set<Movement> forPiece(Piece piece);

    MovementAnalyzer analyzer();

    void doMove(Piece piece, Cell cell);
}
