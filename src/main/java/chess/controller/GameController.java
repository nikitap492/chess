package chess.controller;

import chess.domain.GameResult;

/**
 * Created by nikitap4.92@gmail.com
 * 29.04.17.
 */
public interface GameController{

    void newGame();

    void exit();

    void undo();

    void over(GameResult draw);
}
