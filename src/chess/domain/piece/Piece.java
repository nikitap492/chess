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
    private boolean alive = true;
    private boolean moved = false;

    public Piece(PieceType type, PieceColor color) {
        this.type = type;
        this.color = color;
        this.alive = true;
    }

    public Piece(Piece piece){
        this.cell = piece.cell;
        this.color = piece.color;
        this.type = piece.type;
        this.alive = piece.alive;
        this.moved = piece.moved;
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

    public boolean isMoved() {
        return moved;
    }

    public void setMoved(boolean moved) {
        this.moved = moved;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Piece piece = (Piece) o;

        if (type != piece.type) return false;
        if (color != piece.color) return false;
        return cell != null ? cell.equals(piece.cell) : piece.cell == null;

    }

    @Override
    public int hashCode() {
        int result = type.hashCode();
        result = 31 * result + color.hashCode();
        result = 31 * result + (cell != null ? cell.hashCode() : 0);
        return result;
    }
}
