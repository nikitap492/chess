package chess.controller;

import chess.command.Subscriber;
import chess.domain.cell.Cell;
import chess.domain.cell.CellSelection;
import chess.view.CellView;

/**
 * Created by nikitap4.92@gmail.com
 * 21.04.17.
 */
public interface CellController extends Subscriber<CellView>{

    void setMovementController(MovementController movementController);

    void clear();

    void display(Cell cell, CellSelection type);
}
