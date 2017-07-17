package javafx.view.factory;

import chess.command.Click;
import chess.command.ClickListener;
import chess.command.Clickable;
import chess.domain.cell.Cell;
import chess.view.CellView;
import chess.view.factory.CellViewFactory;
import chess.view.image.CellImage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by nikitap4.92@gmail.com
 * 23.04.17.
 */
public class CellViewWrapperFactory extends CellViewFactory {


    public CellViewWrapperFactory(ClickListener<CellView> clickListener) {
        super(clickListener);
    }

    @Override
    public CellView create(Cell cell) {
        return new CellViewWrapper(cell);
    }

    private class CellViewWrapper extends ImageView implements CellView{
        private Cell cell;
        private ClickListener<CellView> clickListener;

        CellViewWrapper(Cell cell) {
            this.cell = cell;
            this.clickListener = getClickListener();
            setOnMouseClicked(mouseEvent -> onClick());
        }

        @Override
        public Cell cell() {
            return cell;
        }

        @Override
        public void setImage(CellImage image) {
            super.setImage((Image) image);
        }

        @Override
        public void onClick() {
            clickListener.click(() -> this);
        }
    }
}
