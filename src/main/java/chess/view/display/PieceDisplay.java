package chess.view.display;

import chess.domain.piece.Piece;

/**
 * Created by nikitap4.92@gmail.com
 * 21.04.17.
 */
public interface PieceDisplay {

    void put(Piece piece);

    void remove(Piece piece);

    void clear();
}
