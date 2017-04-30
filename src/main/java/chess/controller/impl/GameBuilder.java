package chess.controller.impl;

import chess.command.CellViewClickListener;
import chess.command.PieceViewClickListener;
import chess.controller.*;
import chess.controller.analyzer.CheckmateAnalyzer;
import chess.controller.analyzer.MovementAnalyzer;
import chess.repository.CellImageRepository;
import chess.repository.CellLayoutRepository;
import chess.repository.PieceImageRepository;
import chess.view.display.CellDisplay;
import chess.view.display.DefaultCellDisplay;
import chess.view.display.DefaultPieceDisplay;
import chess.view.display.PieceDisplay;

import static chess.domain.piece.PieceColor.BLACK;

/**
 * Created by nikitap4.92@gmail.com
 * 29.04.17.
 */
public class GameBuilder {
    private PieceImageRepository pieceImageRepository;
    private CellImageRepository cellImageRepository;
    private CellLayoutRepository cellLayoutRepository;
    private CellViewClickListener cellViewClickListener;
    private PieceViewClickListener pieceViewClickListener;
    private DialogController dialogController;

    public GameBuilder setPieceImageRepository(PieceImageRepository pieceImageRepository) {
        this.pieceImageRepository = pieceImageRepository;
        return this;
    }

    public GameBuilder setCellImageRepository(CellImageRepository cellImageRepository) {
        this.cellImageRepository = cellImageRepository;
        return this;
    }

    public GameBuilder setCellLayoutRepository(CellLayoutRepository cellLayoutRepository) {
        this.cellLayoutRepository = cellLayoutRepository;
        return this;
    }

    public GameBuilder setCellViewClickListener(CellViewClickListener cellViewClickListener) {
        this.cellViewClickListener = cellViewClickListener;
        return this;
    }

    public GameBuilder setPieceViewClickListener(PieceViewClickListener pieceViewClickListener) {
        this.pieceViewClickListener = pieceViewClickListener;
        return this;
    }

    public GameBuilder setDialogController(DialogController dialogController) {
        this.dialogController = dialogController;
        return this;
    }

    public GameController build() {
        return new DefaultGameController(pieceImageRepository, cellImageRepository, cellLayoutRepository, cellViewClickListener, pieceViewClickListener, dialogController);
    }


    private class DefaultGameController implements GameController {
        private final PieceController pieceController;
        private final MovementController movementController;
        private final TurnController turnController;

        DefaultGameController(PieceImageRepository pieceImageRepository, CellImageRepository cellImageRepository,
                                     CellLayoutRepository cellLayoutRepository, CellViewClickListener cellViewClickListener,
                                     PieceViewClickListener pieceViewClickListener, DialogController dialogController) {

            PieceDisplay pieceDisplay = new DefaultPieceDisplay(pieceImageRepository, cellLayoutRepository);

            this.turnController = new DefaultTurnController();

            this.pieceController = new DefaultPieceController(pieceDisplay, turnController);

            pieceViewClickListener.addSubscriber(pieceController);

            CellDisplay cellDisplay = new DefaultCellDisplay(cellImageRepository, cellLayoutRepository);
            CellController cellController = new DefaultCellController(cellDisplay);
            pieceController.setCellController(cellController);

            cellViewClickListener.addSubscriber(cellController);

            CheckmateController checkmateController = new CheckmateAnalyzer(pieceController, turnController);
            this.movementController = new MovementAnalyzer(pieceController, checkmateController, turnController, cellController, dialogController);
            checkmateController.setMovementController(movementController);

            cellController.setMovementController(movementController);
            pieceController.setMovementController(movementController);
            pieceController.setCheckmateController(checkmateController);

        }

        @Override
        public void newGame() {
            pieceController.arrangePieces();
            movementController.clear();
            if(turnController.whoseIsTurn() == BLACK){
                turnController.nextTurn();
            }
        }

        @Override
        public void exit() {
            System.exit(0);
        }

        @Override
        public void undo() {
            movementController.undo();
        }
    }


}
