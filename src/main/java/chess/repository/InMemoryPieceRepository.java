package chess.repository;

import chess.domain.cell.Cell;
import chess.domain.piece.Piece;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Poshivalov N.A.
 * @since 18.07.2017.
 */
public class InMemoryPieceRepository implements PieceRepository {

    private Map<Cell, Piece> pieces = new HashMap<>();

    @Override
    public Optional<Piece> byCell(Cell cell) {
        return Optional.ofNullable(pieces.get(cell));
    }

    @Override
    public Map<Cell, Piece> pieces() {
        return pieces;
    }

    @Override
    public void replace(Piece piece, Cell to) {
        pieces.remove(piece.getCell());
        piece.setCell(to);
        piece.setMoved(true);
        pieces.put(to, piece);
    }

    @Override
    public void remove(Piece piece) {
        pieces.remove(piece.getCell());
    }

    @Override
    public void create(Piece piece) {
        pieces.put(piece.getCell(), piece);
    }
}
