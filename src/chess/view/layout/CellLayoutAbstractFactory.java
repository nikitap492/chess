package chess.view.layout;

import chess.domain.cell.Cell;
import chess.domain.piece.PieceColor;
import chess.domain.piece.PieceType;
import chess.view.CellView;
import chess.view.Dimension;
import chess.view.PieceView;
import chess.view.Position;
import chess.view.image.CellLayoutFactory;
import chess.view.image.CellViewFactory;
import chess.view.image.PieceViewFactory;

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
        CellView cellView = cellViewFactory.create();
        cellLayout.addCellView(cellView);
        if(hasPiece(cell)){
            PieceColor color = getColor(cell);
            PieceType type = getType(cell);
            PieceView pieceView = pieceViewFactory.create(type, color);
            cellLayout.addPieceView(pieceView);
        }

        cellLayout.setDimension(dimension);
        cellLayout.setPosition(position);

        return cellLayout;
    }

    public abstract CellLayout empty();

}
