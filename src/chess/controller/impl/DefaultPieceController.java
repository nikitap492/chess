package chess.controller.impl;

import chess.command.Click;
import chess.controller.CellController;
import chess.controller.MovementController;
import chess.controller.PieceController;
import chess.controller.SelectController;
import chess.domain.cell.Cell;
import chess.domain.movement.Movement;
import chess.domain.movement.MovementType;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceType;
import chess.view.PieceView;
import chess.view.display.PieceDisplay;

import java.util.Set;

/**
 * Created by nikitap4.92@gmail.com
 * 21.04.17.
 */
public class DefaultPieceController implements PieceController {

    private PieceDisplay pieceDisplay;
    private CellController cellController;
    private MovementController movementController;
    private SelectController selectController;


    @Override
    public void setPieceDisplay(PieceDisplay pieceDisplay) {
        this.pieceDisplay = pieceDisplay;
    }

    @Override
    public void setCellController(CellController cellController) {
        this.cellController = cellController;
    }

    @Override
    public void setMovementController(MovementController controller) {
        this.movementController = controller;
    }

    @Override
    public void setSelectController(SelectController selectController) {
        this.selectController = selectController;
    }

    @Override
    public void move(Piece piece, Cell cell) {
        pieceDisplay.remove(piece);
        piece.setCell(cell);
        pieceDisplay.put(piece);
    }

    @Override
    public void kill(Piece piece) {
        piece.setAlive(false);
        pieceDisplay.remove(piece);
    }

    @Override
    public void create(Piece piece) {
        pieceDisplay.put(piece);
    }

    @Override
    public void transform(Piece piece, PieceType type) {
        piece.setType(type);
        pieceDisplay.put(piece);
    }

    @Override
    public void update(Click<PieceView> t) {
        cellController.clear();
        Piece piece = t.target().piece();

        boolean isPossible = selectController.selectIsPossible(piece);

        if(isPossible){
            cellController.select(piece.getCell());

            Set<Movement> movements = movementController.possibleMovements(piece);
            for (Movement movement: movements){
                Cell to = movement.getTo();
                MovementType type = movement.getType();
                switch (type){
                    case CHECK:
                        cellController.check(to);
                        break;
                    case MOVE:
                        cellController.free(to);
                        break;
                    case KILL:
                        cellController.treat(to);
                        break;
                }
            }
        }



    }
}
