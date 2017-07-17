package javafx.view.image;

import chess.domain.piece.Piece;
import chess.view.image.PieceImage;
import chess.repository.PieceImageRepository;

/**
 * Created by nikitap4.92@gmail.com
 * 23.04.17.
 */
public class PieceImageWrapperRepository implements PieceImageRepository {


    @Override
    public PieceImage get(Piece piece) {
        return new PieceImageWrapper(piece);
    }
}
