package chess.controller;

import chess.domain.game.GameResult;
import chess.domain.game.PlayerType;

/**
 * Created by nikitap4.92@gmail.com
 * 29.04.17.
 */
public interface GameController{

    void newGame(PlayerType white, PlayerType black);

    void exit();

    void undo();

    void over(GameResult draw);

    void nextTurn();
}
