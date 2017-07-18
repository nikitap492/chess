package chess.repository;

import chess.domain.cell.Cell;
import chess.domain.piece.Piece;

import java.util.Map;
import java.util.Optional;

/**
 * @author Poshivalov N.A.
 * @since 18.07.2017.
 */
public interface PieceRepository {

    Optional<Piece> byCell(Cell cell);

    Map<Cell, Piece> pieces();

    void replace(Piece piece, Cell to);

    void remove(Piece piece);

    void create(Piece piece);
}
