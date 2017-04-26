package chess.controller.impl;

import chess.command.Click;
import chess.controller.CellController;
import chess.controller.MovementController;
import chess.domain.cell.Cell;
import chess.view.CellView;
import chess.view.display.CellDisplay;

import static chess.domain.cell.CellSelection.*;

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
    public void setCellDisplay(CellDisplay cellDisplay) {
        this.cellDisplay = cellDisplay;
    }

    @Override
    public void setMovementController(MovementController movementController) {
        this.movementController = movementController;
    }

    @Override
    public void select(Cell cell) {
        cell.setSelection(SELECT);
        cellDisplay.put(cell);
    }

    @Override
    public void treat(Cell cell) {
        cell.setSelection(TREAT);
        cellDisplay.put(cell);
    }

    @Override
    public void free(Cell cell) {
        cell.setSelection(FREE);
        cellDisplay.put(cell);
    }

    @Override
    public void check(Cell cell) {
        cell.setSelection(CHECK);
        cellDisplay.put(cell);
    }

    @Override
    public void clear() {
        cellDisplay.clear();
    }


    @Override
    public void update(Click<CellView> click) {
        Cell cell = click.target().cell();
        movementController.moveToCell(cell);
        clear();
    }
}
