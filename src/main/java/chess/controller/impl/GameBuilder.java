package chess.controller.impl;

import chess.command.CellViewClickListener;
import chess.command.PieceViewClickListener;
import chess.controller.*;
import chess.domain.game.GameResult;
import chess.domain.game.PlayerType;
import chess.domain.piece.PieceColor;
import chess.repository.CellImageRepository;
import chess.repository.CellLayoutRepository;
import chess.repository.PieceImageRepository;
import chess.view.display.CellDisplay;
import chess.view.display.DefaultCellDisplay;
import chess.view.display.DefaultPieceDisplay;
import chess.view.display.PieceDisplay;

import static chess.domain.game.PlayerType.AI;
import static chess.domain.piece.PieceColor.BLACK;
import static chess.domain.piece.PieceColor.WHITE;

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
        private final AiController aiController;
        private PlayerType white;
        private PlayerType black;

        DefaultGameController(PieceImageRepository pieceImageRepository, CellImageRepository cellImageRepository,
                                     CellLayoutRepository cellLayoutRepository, CellViewClickListener cellViewClickListener,
                                     PieceViewClickListener pieceViewClickListener, DialogController dialogController) {

            PieceDisplay pieceDisplay = new DefaultPieceDisplay(pieceImageRepository, cellLayoutRepository);

            this.turnController = new DefaultTurnController();
            this.pieceController =  new DefaultPieceController(pieceDisplay, turnController);


            pieceViewClickListener.addSubscriber(pieceController);

            CellDisplay cellDisplay = new DefaultCellDisplay(cellImageRepository, cellLayoutRepository);
            CellController cellController = new DefaultCellController(cellDisplay);
            pieceController.setCellController(cellController);

            cellViewClickListener.addSubscriber(cellController);

            CheckmateController checkmateController = new DefaultCheckmateController(pieceController, turnController, this);
            this.movementController = new DefaultMovementController(this, pieceController, checkmateController, turnController, cellController, dialogController);
            checkmateController.setMovementAnalyze(movementController.analyzer());


            this.aiController = new DefaultAiController(movementController);
            cellController.setMovementController(movementController);
            pieceController.setMovementController(movementController);
            pieceController.setCheckmateController(checkmateController);
            turnController.setCheckmateController(checkmateController);
            cellController.setPieceController(pieceController);
        }

        @Override
        public void newGame(PlayerType white, PlayerType black) {
            pieceController.arrangePieces();
            if(turnController.whoseIsTurn() == BLACK){
                turnController.nextTurn();
            }
            this.white = white;
            this.black = black;
        }



        @Override
        public void exit() {
            System.exit(0);
        }

        @Override
        public void undo() {
            movementController.undo();
        }

        @Override
        public void over(GameResult result) {
            switch (result){
                case DRAW:
                    dialogController.gameOver("Draw");
                    break;
                case WHITE_WON:
                    dialogController.gameOver("Game over! White won");
                    break;
                case BLACK_WON:
                    dialogController.gameOver("Game over! Black won");
                    break;
            }
        }

        @Override
        public void nextTurn() {
            PieceColor color = turnController.whoseIsTurn();
            if (color == WHITE && white == AI){
                aiController.doMove();
            }
            if (color == BLACK && black == AI){
                aiController.doMove();
            }
        }
    }


}
