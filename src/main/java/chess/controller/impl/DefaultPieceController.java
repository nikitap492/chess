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
import chess.repository.InMemoryPieceRepository;
import chess.repository.PieceRepository;
import chess.view.PieceView;
import chess.view.display.PieceDisplay;

import java.util.Optional;
import java.util.Set;

import static chess.domain.cell.CellSelection.*;
import static chess.domain.cell.Digit.*;
import static chess.domain.piece.PieceType.*;

/**
 * Created by nikitap4.92@gmail.com
 * 21.04.17.
 */
class DefaultPieceController implements PieceController{

    private PieceDisplay pieceDisplay;
    private final PieceRepository pieceRepository = new InMemoryPieceRepository();
    private CellController cellController;
    private MovementController movementController;

    private final TurnController turnController;
    private CheckmateController checkmateController;
    private Piece selected;


    DefaultPieceController(PieceDisplay pieceDisplay, TurnController turnController) {
        this.pieceDisplay = pieceDisplay;
        this.turnController = turnController;
    }

    @Override
    public void arrangePieces() {
        cellController.clear();
        pieceDisplay.clear();
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
    public void setCheckmateController(CheckmateController checkmateController) {
        this.checkmateController = checkmateController;
    }

    @Override
    public void move(Piece piece, Cell cell) {
        pieceDisplay.remove(piece);
        pieceRepository.replace(piece, cell);
        pieceDisplay.put(piece);
    }

    @Override
    public void kill(Piece piece) {
        pieceDisplay.remove(piece);
        pieceRepository.remove(piece);
    }

    @Override
    public void create(Piece piece) {
        pieceDisplay.put(piece);
        pieceRepository.create(piece);
    }

    @Override
    public void transform(Piece piece, PieceType type) {
        piece.setType(type);
        pieceDisplay.put(piece);
    }


    @Override
    public void check() {
        PieceColor pieceColor = turnController.whoseIsTurn();
        Piece king = pieceRepository.pieces().values()
                .stream()
                .filter(p -> p.getColor() == pieceColor && p.getType() == KING)
                .findAny().orElseThrow(RuntimeException::new);
        cellController.display(king.getCell(), CHECK);
    }

    @Override
    public PieceRepository repository() {
        return pieceRepository;
    }

    @Override
    public Optional<Piece> selected() {
        return Optional.ofNullable(selected);
    }

    @Override
    public void update(Click<PieceView> t) {
        cellController.clear();
        Piece piece = t.target().piece();

        if (turnController.whoseIsTurn() == piece.getColor()) {
            cellController.display(piece.getCell(), SELECT);
            selected = piece;

            Set<Movement> movements = movementController.forPiece(piece);
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
                    case EN_PASSANT:
                        cellController.display(to, TREAT);
                        break;
                }
            }
        }
        checkmateController.nextTurn();
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
