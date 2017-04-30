package chess.view.factory;

import chess.command.ClickListener;
import chess.domain.cell.Cell;
import chess.view.CellView;

/**
 * Created by nikitap4.92@gmail.com
 * 22.04.17.
 */
public abstract class CellViewFactory {

    private ClickListener<CellView> clickListener;

    public CellViewFactory(ClickListener<CellView> clickListener) {
        this.clickListener = clickListener;
    }


    public abstract CellView create(Cell cell);

    protected ClickListener<CellView> getClickListener() {
        return clickListener;
    }
}
