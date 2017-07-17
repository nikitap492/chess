package javafx.view.factory;

import chess.command.ClickListener;
import chess.domain.piece.Piece;
import chess.view.PieceView;
import chess.view.factory.PieceViewFactory;
import chess.view.image.PieceImage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by nikitap4.92@gmail.com
 * 23.04.17.
 */
public class PieceViewWrapperFactory extends PieceViewFactory {

    public PieceViewWrapperFactory(ClickListener<PieceView> clickListener) {
        super(clickListener);
    }

    @Override
    public PieceView create() {
        return new PieceViewWrapper();
    }

    private class PieceViewWrapper extends ImageView implements PieceView {
        private Piece piece;
        private ClickListener<PieceView> clickListener;


        PieceViewWrapper() {
            setOnMouseClicked((mouseEvent -> onClick()));
        }

        @Override
        public void setImage(PieceImage image) {
            super.setImage((Image) image);
            this.clickListener = getClickListener();
        }

        @Override
        public void onClick() {
            clickListener.click(() -> this);
        }

        @Override
        public void setPiece(Piece piece) {
            this.piece = piece;
        }

        @Override
        public Piece piece() {
            return piece;
        }
    }

}
