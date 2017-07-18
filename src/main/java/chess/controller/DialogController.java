package chess.controller;

import chess.domain.piece.PieceType;

/**
 * Created by nikitap4.92@gmail.com
 * 28.04.17.
 */
public interface DialogController {

    void setGameController(GameController gameController);

    PieceType transformDialog();

    void exit();

    void newGame();

    void gameOver(String title);
}
