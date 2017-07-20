package chess.controller.impl;

import chess.controller.CheckmateController;
import chess.controller.TurnController;
import chess.domain.piece.PieceColor;

import static chess.domain.piece.PieceColor.BLACK;
import static chess.domain.piece.PieceColor.WHITE;

/**
 * Created by nikitap4.92@gmail.com
 * 22.04.17.
 */
class DefaultTurnController implements TurnController {

    private CheckmateController checkmateController;
    private PieceColor color = WHITE;

    @Override
    public void nextTurn() {
        color = whoseIsNext();
    }

    @Override
    public PieceColor whoseIsTurn() {
        return color;
    }

    @Override
    public void setCheckmateController(CheckmateController checkmateController) {
        this.checkmateController = checkmateController;
    }

    private PieceColor whoseIsNext() {
        if(color == WHITE){
            return BLACK;
        }
        return WHITE;
    }
}
