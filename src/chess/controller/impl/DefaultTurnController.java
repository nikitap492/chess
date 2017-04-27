package chess.controller.impl;

import chess.controller.TurnController;
import chess.domain.piece.PieceColor;

import static chess.domain.piece.PieceColor.BLACK;
import static chess.domain.piece.PieceColor.WHITE;

/**
 * Created by nikitap4.92@gmail.com
 * 22.04.17.
 */
public class DefaultTurnController implements TurnController {

    private PieceColor color = WHITE;

    @Override
    public void nextTurn() {
        if(color == WHITE){
            color = BLACK;
        }else color = WHITE;
    }

    @Override
    public PieceColor whoseIsTurn() {
        return color;
    }
}
