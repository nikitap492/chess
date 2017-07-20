package chess.domain.movement;

import chess.domain.cell.Cell;
import chess.domain.piece.Piece;

import java.util.Optional;

/**
 * @author Poshivalov Nikita
 * @since 20.04.2017.
 */
public class Movement {

    private final Piece piece;
    private MovementType type;
    private final Cell to;
    private final Cell from;
    private Piece killed;

    public Movement(Piece piece, Cell to, MovementType type) {
        this(piece, to, type, null);
    }

    public Movement(Piece piece, Cell to, MovementType type, Piece killed) {
        this.type = type;
        this.to = to;
        this.piece = piece;
        this.from = piece.getCell();
        this.killed = killed;
    }

    public void setType(MovementType type) {
        this.type = type;
    }

    public MovementType getType() {
        return type;
    }

    public Cell getTo() {
        return to;
    }

    public Piece getPiece() {
        return piece;
    }

    public Cell getFrom() {
        return from;
    }

    public Optional<Piece> getKilled() {
        return Optional.ofNullable(killed);
    }

    public void setKilled(Piece killed) {
        this.killed = killed;
    }
}
