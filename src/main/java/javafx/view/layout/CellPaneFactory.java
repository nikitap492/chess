package javafx.view.layout;

import chess.command.CellViewClickListener;
import chess.command.PieceViewClickListener;
import chess.view.factory.CellViewFactory;
import chess.view.factory.PieceViewFactory;
import chess.view.layout.CellLayout;
import chess.view.layout.CellLayoutAbstractFactory;
import javafx.view.factory.CellViewWrapperFactory;
import javafx.view.factory.PieceViewWrapperFactory;
import javafx.view.image.PieceImageWrapperRepository;

/**
 * Created by nikitap4.92@gmail.com
 * 23.04.17.
 */
public class CellPaneFactory extends CellLayoutAbstractFactory {


    public CellPaneFactory(CellViewClickListener cellViewClickListener, PieceViewClickListener pieceViewClickListener) {
        super(new CellViewWrapperFactory(cellViewClickListener), new PieceViewWrapperFactory(pieceViewClickListener));
    }



    @Override
    public CellLayout empty() {
        return new CellPane();
    }
}
