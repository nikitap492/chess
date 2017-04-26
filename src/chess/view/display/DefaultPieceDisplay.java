package chess.view.display;

import chess.domain.piece.Piece;
import chess.view.image.PieceImage;
import chess.view.image.PieceImageRepository;
import chess.view.layout.CellLayout;
import chess.view.layout.CellLayoutRepository;

/**
 * Created by nikitap4.92@gmail.com
 * 21.04.17.
 */
public class DefaultPieceDisplay implements PieceDisplay {

    private PieceImageRepository imageRepository;
    private CellLayoutRepository layoutRepository;

    public DefaultPieceDisplay(PieceImageRepository imageRepository, CellLayoutRepository layoutRepository) {
        this.imageRepository = imageRepository;
        this.layoutRepository = layoutRepository;
    }

    @Override
    public void put(Piece piece) {
        CellLayout container = layoutRepository.get(piece.getCell());
        PieceImage pieceImage = imageRepository.get(piece);
        container.putPieceImage(piece, pieceImage);
    }

    @Override
    public void remove(Piece piece) {
        CellLayout container = layoutRepository.get(piece.getCell());
        container.clearPieceImage();
    }
}
