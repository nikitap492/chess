package javafx.view.menu;

import chess.controller.DialogController;
import chess.controller.GameController;
import chess.view.menu.CloseOption;
import chess.view.menu.NewGameOption;
import chess.view.menu.Option;
import chess.view.menu.UndoOption;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

/**
 * Created by nikitap4.92@gmail.com
 * 30.04.17.
 */
public class Options extends MenuBar {

    public Options(DialogController dialogController, GameController gameController){
        Option closeOption = new CloseOption(dialogController);
        Option undoOption = new UndoOption(gameController);
        Option newGameOption = new NewGameOption(dialogController);

        Menu menu = new Menu("Game");
        MenuItem closeItem = new MenuItem("Close");
        closeItem.setOnAction(action -> closeOption.onClick());
        MenuItem undoItem = new MenuItem("Undo");
        undoItem.setOnAction(action -> undoOption.onClick());
        MenuItem newGameItem = new MenuItem("New Game");
        newGameItem.setOnAction(action -> newGameOption.onClick());

        menu.getItems().addAll(newGameItem, undoItem, closeItem);
        getMenus().addAll(menu);
    }


}
