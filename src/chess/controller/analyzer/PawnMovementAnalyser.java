package chess.controller.analyzer;

import chess.controller.CheckmateController;
import chess.controller.PieceController;
import chess.domain.cell.Cell;
import chess.domain.cell.Digit;
import chess.domain.movement.Movement;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceColor;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static chess.domain.cell.Digit.FIVE;
import static chess.domain.cell.Digit.FOUR;
import static chess.domain.movement.MovementType.KILL;
import static chess.domain.movement.MovementType.MOVE;

/**
 * Created by nikitap4.92@gmail.com
 * 23.04.17.
 */
class PawnMovementAnalyser extends PieceMovementAnalyzer {


    private final PieceController pieceController;

    PawnMovementAnalyser(PieceController pieceController, CheckmateController checkmateController) {
        super(pieceController, checkmateController);
        this.pieceController = pieceController;
    }

    @Override
    void addMovements(Set<Movement> empty, Piece piece) {
        boolean isBlocked = oneStep(empty, piece);
        if(!isBlocked){
            doubleStep(empty, piece);
        }
        kill(empty, piece);
    }

    private boolean oneStep(Set<Movement> movements, Piece piece) {
        PieceColor color = piece.getColor();
        int order = piece.getCell().getDigit().getOrder();
        switch (color) {
            case BLACK:
                if(order > 0){
                    Digit digit = Digit.get(--order);
                    return addStep(movements, piece, digit);
                }
                break;
            case WHITE:
                if(order < 7){
                    Digit digit = Digit.get(++order);
                    return addStep(movements, piece, digit);
                }
                break;
        }
        return true;
    }

    private void doubleStep(Set<Movement> movements, Piece piece) {
        boolean moved = piece.isMoved();
        PieceColor color = piece.getColor();
        if (!moved) {
            Digit digit = null;
            switch (color) {
                case BLACK:
                    digit = FIVE;
                    break;
                case WHITE:
                    digit = FOUR;
                    break;
            }
            addStep(movements, piece, digit);
        }
    }


    private void kill(Set<Movement> movements, Piece piece) {
        Set<Movement> kills = new HashSet<>();
        PieceColor color = piece.getColor();
        switch (color) {
            case BLACK:
                addMovement(kills, piece, Directions::leftDown);
                addMovement(kills, piece, Directions::rightDown);
                break;
            case WHITE:
                addMovement(kills, piece, Directions::leftUp);
                addMovement(kills, piece, Directions::rightUp);
                break;
        }
        kills.stream()
                .filter(n -> n.getType() == KILL)
                .forEach(movements::add);
    }

    private boolean addStep(Set<Movement> movements, Piece piece, Digit digit){
        Cell cell = new Cell(piece.getCell().getChar(), digit);
        Optional<Piece> optional = pieceController.byCell(cell);
        if (!optional.isPresent()) {
            movements.add(new Movement(piece, cell, MOVE));
            return false;
        }
        return true;
    }

}
