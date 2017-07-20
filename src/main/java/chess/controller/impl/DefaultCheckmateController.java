package chess.controller.impl;

import chess.controller.CheckmateController;
import chess.controller.GameController;
import chess.controller.PieceController;
import chess.controller.TurnController;
import chess.controller.analyzer.MovementAnalyzer;
import chess.domain.game.GameResult;
import chess.domain.piece.PieceColor;

/**
 * @author Poshivalov N.A.
 * @since 20.07.2017.
 */
public class DefaultCheckmateController implements CheckmateController{

    private final PieceController pieceController;
    private final TurnController turnController;
    private final GameController gameController;
    private MovementAnalyzer movementAnalyzer;

    public DefaultCheckmateController(PieceController pieceController, TurnController turnController, GameController gameController) {
        this.turnController = turnController;
        this.gameController = gameController;
        this.pieceController = pieceController;
    }


    private boolean isCheck() {
        return movementAnalyzer.hasMovementsToKillKing().isPresent();
    }

    private boolean isCheckmate() {
        return !movementAnalyzer.hasMovementToSaveKing().isPresent();
    }


    private boolean isDraw() {
        return !movementAnalyzer.hasAnyMovements();
    }

    @Override
    public void nextTurn() {
        if (isCheck()) {
            pieceController.check();
            if (isCheckmate()) {
                if (turnController.whoseIsTurn() == PieceColor.WHITE)
                    gameController.over(GameResult.BLACK_WON);
                else gameController.over(GameResult.WHITE_WON);
            }
        }else if (isDraw()) {
            gameController.over(GameResult.DRAW);
        }
        gameController.nextTurn();
    }

    @Override
    public void setMovementAnalyze(MovementAnalyzer analyzer) {
        this.movementAnalyzer = analyzer;
    }
}
