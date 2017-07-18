package chess.controller;

import chess.domain.piece.PieceColor;

/**
 * Created by nikitap4.92@gmail.com
 * 22.04.17.
 */
public interface TurnController {

    void nextTurn();

    PieceColor whoseIsTurn();

    void setCheckmateController(CheckmateController checkmateController);
}
