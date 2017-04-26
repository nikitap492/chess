package chess.view.image;

import chess.domain.piece.Piece;

/**
 * Created by nikitap4.92@gmail.com
 * 21.04.17.
 */
public interface PieceImageRepository {

    PieceImage get(Piece piece);
}
