package chess.controller.impl;

import chess.command.Click;
import chess.controller.*;
import chess.domain.cell.Cell;
import chess.domain.cell.Char;
import chess.domain.cell.Digit;
import chess.domain.movement.Movement;
import chess.domain.movement.MovementType;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceColor;
import chess.domain.piece.PieceType;
import chess.view.PieceView;
import chess.view.display.PieceDisplay;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static chess.domain.cell.CellSelection.*;
import static chess.domain.cell.Digit.*;
import static chess.domain.piece.PieceType.*;

/**
 * Created by nikitap4.92@gmail.com
 * 21.04.17.
 */
public class DefaultPieceController implements PieceController {

    private PieceDisplay pieceDisplay;
    private CellController cellController;
    private MovementController movementController;
    private SelectController selectController;

    private Map<Cell, Piece> pieces;
    private TurnController turnController;


    public DefaultPieceController(PieceDisplay pieceDisplay) {
        this.pieceDisplay = pieceDisplay;
    }

    @Override
    public void arrangePieces() {
        pieces = new HashMap<>();
        for (Char c : Char.values()) {
            for (Digit d : Digit.values()) {
                Cell cell = new Cell(c, d);
                if (hasPiece(cell)) {
                    PieceType type = getType(cell);
                    PieceColor color = getColor(cell);
                    Piece piece = new Piece(type, color);
                    piece.setCell(cell);
                    create(piece);
                }
            }
        }
    }

    @Override
    public void setCellController(CellController cellController) {
        this.cellController = cellController;
    }

    @Override
    public void setMovementController(MovementController controller) {
        this.movementController = controller;
    }

    @Override
    public void setSelectController(SelectController selectController) {
        this.selectController = selectController;
    }

    @Override
    public void setTurnController(TurnController turnController){
        this.turnController = turnController;
    }

    @Override
    public void move(Piece piece, Cell cell) {
        pieceDisplay.remove(piece);
        pieces.remove(piece.getCell());
        piece.setCell(cell);
        pieceDisplay.put(piece);
        pieces.put(cell, piece);
    }

    @Override
    public void kill(Piece piece) {
        piece.setAlive(false);
        pieceDisplay.remove(piece);
        pieces.remove(piece.getCell());
    }

    @Override
    public void create(Piece piece) {
        pieceDisplay.put(piece);
        pieces.put(piece.getCell(), piece);
    }

    @Override
    public void transform(Piece piece, PieceType type) {
        piece.setType(type);
        pieceDisplay.put(piece);
    }

    @Override
    public Optional<Piece> byCell(Cell cell) {
        return Optional.ofNullable(pieces.get(cell));
    }

    @Override
    public Map<Cell, Piece> pieces() {
        return pieces;
    }

    @Override
    public void check() {
        PieceColor pieceColor = turnController.whoseIsTurn();
        Piece king = pieces.values()
                .stream()
                .filter(p -> p.getColor() == pieceColor && p.getType() == KING)
                .findAny().orElseThrow(RuntimeException::new);
        cellController.display(king.getCell(), CHECK);
    }

    @Override
    public void update(Click<PieceView> t) {
        cellController.clear();
        Piece piece = t.target().piece();

        boolean isPossible = selectController.selectIsPossible(piece);

        if (isPossible) {
            cellController.display(piece.getCell(), SELECT);

            Set<Movement> movements = movementController.possible(piece);
            for (Movement movement : movements) {
                Cell to = movement.getTo();
                MovementType type = movement.getType();
                switch (type){
                    case MOVE:
                        cellController.display(to, FREE);
                        break;
                    case KILL:
                        cellController.display(to, TREAT);
                        break;
                    case CASTLING:
                        cellController.display(to, CASTLING);
                        break;
                }
            }
        }
    }

    //For initialization

    private boolean hasPiece(Cell cell) {
        Digit digit = cell.getDigit();
        return digit == ONE || digit == TWO || digit == EIGHT || digit == SEVEN;
    }

    private PieceColor getColor(Cell cell) {
        if (cell.getDigit() == ONE || cell.getDigit() == TWO) {
            return PieceColor.WHITE;
        } else return PieceColor.BLACK;
    }

    private PieceType getType(Cell cell) {
        Digit digit = cell.getDigit();
        if (digit == TWO || digit == SEVEN) {
            return PAWN;
        } else {
            switch (cell.getChar()) {
                case A:
                case H:
                    return ROOK;
                case B:
                case G:
                    return KNIGHT;
                case C:
                case F:
                    return BISHOP;
                case D:
                    return QUEEN;
                case E:
                    return KING;
            }
        }
        return null;
    }

}
