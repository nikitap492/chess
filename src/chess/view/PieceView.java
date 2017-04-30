package chess.view;

import chess.domain.piece.Piece;
import chess.view.image.PieceImage;

/**
 * Created by nikitap4.92@gmail.com
 * 21.04.17.
 */
public interface PieceView extends View<PieceImage> {

    Piece piece();

    void setPiece(Piece piece);
}
