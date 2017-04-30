package chess.view.menu;

import chess.controller.DialogController;

/**
 * Created by nikitap4.92@gmail.com
 * 30.04.17.
 */
public class CloseOption implements Option {


    private final DialogController dialogController;

    public CloseOption(DialogController dialogController) {
        this.dialogController = dialogController;
    }

    @Override
    public void onClick(){
        dialogController.exit();
    }


}
