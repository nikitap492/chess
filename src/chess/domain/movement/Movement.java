package chess.domain.movement;

import chess.domain.cell.Cell;
import chess.domain.piece.Piece;

/**
 * @author Poshivalov Nikita
 * @since 20.04.2017.
 */
public class Movement {

    private final Piece piece;
    private MovementType type;
    private final Cell to;
    private final Cell from;

    public Movement(Piece piece, Cell to, MovementType type) {
        this.type = type;
        this.to = to;
        this.piece = piece;
        this.from = piece.getCell();
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
}
