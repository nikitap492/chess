package chess.view.factory;

import chess.domain.piece.PieceColor;
import chess.domain.piece.PieceType;
import chess.view.PieceView;

/**
 * Created by nikitap4.92@gmail.com
 * 22.04.17.
 */
public interface PieceViewFactory {

    PieceView create(PieceType type, PieceColor color);
}
