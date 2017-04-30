package chess.view.factory;

import chess.command.ClickListener;
import chess.view.PieceView;

/**
 * Created by nikitap4.92@gmail.com
 * 22.04.17.
 */
public abstract class PieceViewFactory {

    private ClickListener<PieceView> clickListener;

    public PieceViewFactory(ClickListener<PieceView> clickListener) {
        this.clickListener = clickListener;
    }

    protected ClickListener<PieceView> getClickListener() {
        return clickListener;
    }

    public abstract PieceView create();
}