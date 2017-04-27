package chess.controller.impl;

import chess.command.Click;
import chess.controller.CellController;
import chess.controller.MovementController;
import chess.domain.cell.Cell;
import chess.domain.cell.CellSelection;
import chess.view.CellView;
import chess.view.display.CellDisplay;

/**
 * Created by nikitap4.92@gmail.com
 * 21.04.17.
 */
public class DefaultCellController implements CellController {
    private CellDisplay cellDisplay;
    private MovementController movementController;

    public DefaultCellController(CellDisplay cellDisplay) {
        this.cellDisplay = cellDisplay;
    }


    @Override
    public void setMovementController(MovementController movementController) {
        this.movementController = movementController;
    }

    @Override
    public void clear() {
        cellDisplay.clear();
    }

    @Override
    public void display(Cell cell, CellSelection type) {
        cell.setSelection(type);
        cellDisplay.put(cell);
    }


    @Override
    public void update(Click<CellView> click) {
        Cell cell = click.target().cell();
        movementController.moveToCell(cell);
    }
}
