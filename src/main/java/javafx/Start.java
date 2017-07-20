package javafx;

import chess.command.CellViewClickListener;
import chess.command.PieceViewClickListener;
import chess.controller.DialogController;
import chess.controller.GameController;
import chess.controller.impl.GameBuilder;
import chess.domain.game.PlayerType;
import chess.repository.CellLayoutRepository;
import chess.view.layout.LayoutRepositoryBuilder;
import javafx.application.Application;
import javafx.controller.DialogControllerImpl;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.view.image.CellImageWrapperRepository;
import javafx.view.image.PieceImageWrapperRepository;
import javafx.view.layout.CellPaneFactory;
import javafx.view.menu.Options;

import java.io.File;
import java.util.stream.Collectors;

/**
 * Created by nikitap4.92@gmail.com
 * 22.04.17.
 */
public class Start extends Application{

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {


        File dir = new File( System.getProperty("user.dir") + File.separator + "pic");

        Image image = new Image("file:///" + dir.getAbsolutePath() + File.separator + "board.png");
        ImageView board = new ImageView();
        board.setImage(image);
        board.setY(25);


        CellViewClickListener cellViewClickListener = new CellViewClickListener();
        PieceViewClickListener pieceViewClickListener = new PieceViewClickListener();


        LayoutRepositoryBuilder builder = new LayoutRepositoryBuilder();
        CellLayoutRepository repository = builder
                .setCellHeight(80)
                .setCellWidth(80)
                .setOffsetX(82)
                .setOffsetY(-82)
                .setZeroX(40)
                .setZeroY(639)
                .setFactory(new CellPaneFactory(cellViewClickListener, pieceViewClickListener))
                .build();

        DialogController dialogController = new DialogControllerImpl();

        GameController gameController = new GameBuilder()
                .setCellImageRepository(new CellImageWrapperRepository())
                .setPieceImageRepository(new PieceImageWrapperRepository())
                .setCellLayoutRepository(repository)
                .setCellViewClickListener(cellViewClickListener)
                .setPieceViewClickListener(pieceViewClickListener)
                .setDialogController(dialogController)
                .build();

        dialogController.setGameController(gameController);
        gameController.newGame(PlayerType.HUMAN, PlayerType.AI);

        Pane pane = new Pane(board);
        Options options = new Options(dialogController,  gameController);
        pane.getChildren().addAll(repository.all().stream().map(n -> (Pane) n).collect(Collectors.toList()));
        pane.getChildren().add(options);


        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }




}
