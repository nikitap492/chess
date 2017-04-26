package chess.controller;

import chess.command.Subscriber;
import chess.domain.cell.Cell;
import chess.view.CellView;
import chess.view.display.CellDisplay;

/**
 * Created by nikitap4.92@gmail.com
 * 21.04.17.
 */
public interface CellController extends Subscriber<CellView>{

    void setCellDisplay(CellDisplay cellDisplay);

    void setMovementController(MovementController movementController);

    void select(Cell cell);

    void treat(Cell cell);

    void free(Cell cell);

    void check(Cell cell);

    void clear();
}
