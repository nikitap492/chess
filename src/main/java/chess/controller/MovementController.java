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


    //todo Is it necessary method?
    void moveToCell(Cell cell);

    void undo();

    void clear();

    void doMove(Movement movement);

    Set<Movement> forPiece(Piece piece);

    MovementAnalyzer analyzer();
}
