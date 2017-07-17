package javafx.view.image;

import chess.domain.piece.Piece;
import chess.domain.piece.PieceColor;
import chess.domain.piece.PieceType;
import chess.view.image.PieceImage;

/**
 * Created by nikitap4.92@gmail.com
 * 22.04.17.
 */
public class PieceImageWrapper extends AbstractImage implements PieceImage {

    public PieceImageWrapper(Piece piece) {
        super(getColorPrefix(piece.getColor()) + getTypePrefix(piece.getType()));
    }

    private static String getColorPrefix(PieceColor color){
        switch (color){
            case BLACK:
                return "b";
            case WHITE:
                return "w";
        }
        return null;
    }

    private static String getTypePrefix(PieceType type){
        return type.name().toLowerCase();
    }
}
