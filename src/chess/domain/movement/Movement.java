package chess.domain.movement;

import chess.domain.cell.Cell;

/**
 * @author Poshivalov Nikita
 * @since 20.04.2017.
 */
public class Movement {

    private MovementType type;
    private Cell to;
    private Cell from;

    public Movement(MovementType type, Cell to, Cell from) {
        this.type = type;
        this.to = to;
        this.from = from;
    }

    public MovementType getType() {
        return type;
    }

    public Cell getTo() {
        return to;
    }

    public Cell getFrom() {
        return from;
    }
}
