package chess.controller;

import chess.domain.cell.Cell;
import chess.domain.movement.Movement;
import chess.domain.piece.Piece;

import java.util.Set;
import java.util.stream.Stream;

/**
 * @author Poshivalov Nikita
 * @since 20.04.2017.
 */
public interface MovementController {

    Set<Movement> possible(Piece piece);

    Set<Movement> all(Piece piece);

    void moveToCell(Cell cell);

    void undo();

    void clear();

    void emulateMove(Movement movement);

    void doMove(Movement movement);

    Stream<Movement> possible();

    void emulationStepBack();




}
