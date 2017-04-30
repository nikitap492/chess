package chess.view.menu;

import chess.controller.GameController;

/**
 * Created by nikitap4.92@gmail.com
 * 30.04.17.
 */
public class UndoOption implements Option {

    private final GameController gameController;

    public UndoOption(GameController gameController) {
        this.gameController = gameController;
    }

    @Override
    public void onClick(){
        gameController.undo();
    }

}