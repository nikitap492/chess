package chess.domain.piece;

import chess.domain.cell.Cell;

/**
 * @author Poshivalov Nikita
 * @since 14.04.2017.
 */
public class Piece {

    private PieceType type;
    private PieceColor color;
    private Cell cell;
    private boolean alive;

    public Piece(PieceType type, PieceColor color) {
        this.type = type;
        this.color = color;
        this.alive = true;
    }

    public PieceType getType() {
        return type;
    }

    public PieceColor getColor() {
        return color;
    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void setType(PieceType type) {
        this.type = type;
    }

}
