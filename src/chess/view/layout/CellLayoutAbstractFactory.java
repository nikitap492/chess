package chess.view.layout;

import chess.domain.cell.Cell;
import chess.view.CellView;
import chess.view.Dimension;
import chess.view.PieceView;
import chess.view.Position;
import chess.view.factory.CellViewFactory;
import chess.view.factory.PieceViewFactory;

/**
 * Created by nikitap4.92@gmail.com
 * 23.04.17.
 */
public abstract class CellLayoutAbstractFactory implements CellLayoutFactory {

    private CellViewFactory cellViewFactory;
    private PieceViewFactory pieceViewFactory;

    public CellLayoutAbstractFactory(CellViewFactory cellViewFactory, PieceViewFactory pieceViewFactory) {
        this.cellViewFactory = cellViewFactory;
        this.pieceViewFactory = pieceViewFactory;
    }

    @Override
    public CellLayout create(Cell cell, Position position, Dimension dimension) {
        CellLayout cellLayout = empty();

        cellLayout.setDimension(dimension);
        cellLayout.setPosition(position);

        CellView cellView = cellViewFactory.create();
        cellLayout.addCellView(cellView);

        PieceView pieceView = pieceViewFactory.create();

        cellLayout.addPieceView(pieceView);
        return cellLayout;
    }

    public abstract CellLayout empty();

}
