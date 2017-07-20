package javafx.controller;

import chess.controller.DialogController;
import chess.controller.GameController;
import chess.domain.game.PlayerType;
import chess.domain.piece.PieceType;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

import java.util.Optional;

import static chess.domain.piece.PieceType.*;

/**
 * Created by nikitap4.92@gmail.com
 * 28.04.17.
 */
public class DialogControllerImpl implements DialogController {

    private GameController gameController;

    @Override
    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    @Override
    public PieceType transformDialog() {
        Alert transform = new Alert(Alert.AlertType.CONFIRMATION);
        transform.setTitle("Choose a piece");
        transform.setHeaderText("Select piece type for transformation");
        transform.setContentText("Piece type");

        ButtonType queen = new ButtonType("Queen");
        ButtonType knight = new ButtonType("Knight");
        ButtonType rook = new ButtonType("Rook");
        ButtonType bishop = new ButtonType("Bishop");

        transform.getButtonTypes().setAll(queen, knight, rook, bishop);
        Optional<ButtonType> result = transform.showAndWait();
        if (result.get() == queen) {
            return QUEEN;
        } else if (result.get() == knight) {
            return KNIGHT;
        } else if (result.get() == rook) {
            return ROOK;
        } else {
            return BISHOP;
        }
    }

    @Override
    public void gameOver(String title) {
        newGameDialog(title);
    }

    @Override
    public void exit() {
        Alert checkmate = new Alert(Alert.AlertType.CONFIRMATION);
        checkmate.setTitle("Exit");
        checkmate.setHeaderText("Do you want to exit?");
        checkmate.setContentText("");
        ButtonType yes = new ButtonType("Yes");
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        checkmate.getButtonTypes().setAll(yes, no);
        Optional<ButtonType> result = checkmate.showAndWait();
        if (result.get() == yes) {
            gameController.exit();
        }
    }

    @Override
    public void newGame() {
        newGameDialog("New Game");
    }




    private void newGameDialog(String title){
        Alert newGame = new Alert(Alert.AlertType.CONFIRMATION);
        newGame.setTitle(title);
        newGame.setHeaderText("Do you want to start new game?");
        newGame.setContentText("");
        ButtonType yes = new ButtonType("Yes");
        ButtonType no = new ButtonType("No");
        newGame.getButtonTypes().setAll(yes, no);
        Optional<ButtonType> result = newGame.showAndWait();
        if (result.get() == yes) {
            gameController.newGame(PlayerType.HUMAN, PlayerType.AI);
        }
        if (result.get() == no) {
            gameController.exit();
        }
    }


}
