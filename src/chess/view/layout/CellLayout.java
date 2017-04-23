package chess.view.layout;

import chess.view.CellView;
import chess.view.Dimension;
import chess.view.PieceView;
import chess.view.Position;
import chess.view.image.CellImage;
import chess.view.image.PieceImage;

/**
 * Created by nikitap4.92@gmail.com
 * 21.04.17.
 */
public interface CellLayout  {

    void addPieceView(PieceView pieceView);

    void addCellView(CellView cellView);

    void putCellImage(CellImage cellImage);

    void clearCellImage();

    void putPieceImage(PieceImage pieceImage);

    void clearPieceImage();

    void setDimension(Dimension dimension);

    void setPosition(Position position);

}
