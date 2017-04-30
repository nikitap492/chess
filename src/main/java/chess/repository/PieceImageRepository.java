package chess.repository;

import chess.domain.piece.Piece;
import chess.view.image.PieceImage;

/**
 * Created by nikitap4.92@gmail.com
 * 21.04.17.
 */
public interface PieceImageRepository {

    PieceImage get(Piece piece);
}
