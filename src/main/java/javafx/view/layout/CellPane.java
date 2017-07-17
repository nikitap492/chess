package javafx.view.layout;

import chess.command.PieceViewClickListener;
import chess.domain.cell.CellSelection;
import chess.domain.piece.Piece;
import chess.view.CellView;
import chess.view.Dimension;
import chess.view.PieceView;
import chess.view.Position;
import chess.view.image.CellImage;
import chess.view.image.PieceImage;
import chess.view.layout.CellLayout;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import static chess.domain.cell.CellSelection.*;

/**
 * Created by nikitap4.92@gmail.com
 * 23.04.17.
 */
public class CellPane extends Pane implements CellLayout {

    private Dimension dimension;
    private CellView cellView;
    private PieceView pieceView;

    @Override
    public void addPieceView(PieceView pieceView) {
        this.pieceView = pieceView;
        getChildren().add((Node) pieceView);
        ImageView pieceViewJavaFx = (ImageView) this.pieceView;
        pieceViewJavaFx.setFitWidth(dimension.getWidth());
        pieceViewJavaFx.setFitHeight(dimension.getHeight());
    }

    @Override
    public void addCellView(CellView cellView) {
        this.cellView = cellView;
        ImageView cellViewJavaFx = (ImageView) this.cellView;
        cellViewJavaFx.setFitWidth(dimension.getWidth());
        cellViewJavaFx.setFitHeight(dimension.getHeight());
        getChildren().add((Node) cellView);
    }

    @Override
    public void putCellImage(CellImage cellImage) {
        cellView.setImage(cellImage);
    }

    @Override
    public void clearCellImage() {
        cellView.setImage(null);
    }

    @Override
    public void putPieceImage(Piece piece, PieceImage pieceImage) {
        pieceView.setPiece(piece);
        pieceView.setImage(pieceImage);
    }

    @Override
    public void clearPieceImage() {
        pieceView.setImage(null);
    }

    @Override
    public void setDimension(Dimension dimension) {
        this.dimension = dimension;
    }

    @Override
    public void setPosition(Position position) {
        setLayoutX(position.getX());
        setLayoutY(position.getY());
    }
}









