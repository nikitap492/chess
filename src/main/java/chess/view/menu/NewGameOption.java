package chess.view.menu;

import chess.controller.DialogController;

/**
 * Created by nikitap4.92@gmail.com
 * 30.04.17.
 */
public class NewGameOption implements Option {

    private final DialogController dialogController;

    public NewGameOption(DialogController dialogController) {
        this.dialogController = dialogController;
    }

    @Override
    public void onClick(){
        dialogController.newGame();
    }

}
