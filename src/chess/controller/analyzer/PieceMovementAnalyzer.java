package chess.controller.analyzer;

import chess.controller.CheckmateController;
import chess.controller.PieceController;
import chess.domain.cell.Cell;
import chess.domain.cell.Char;
import chess.domain.cell.Digit;
import chess.domain.movement.Movement;
import chess.domain.piece.Piece;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static chess.domain.movement.MovementType.KILL;
import static chess.domain.movement.MovementType.MOVE;

/**
 * Created by nikitap4.92@gmail.com
 * 24.04.17.
 */
abstract class PieceMovementAnalyzer {

    private PieceController pieceController;
    private CheckmateController checkmateController;

    PieceMovementAnalyzer(PieceController pieceController, CheckmateController checkmateController) {
        this.pieceController = pieceController;
        this.checkmateController = checkmateController;
    }

    abstract void addMovements(Set<Movement> empty, Piece piece);


    Set<Movement> analyze(Piece piece){
        Set<Movement> movements = new HashSet<>();
        addMovements(movements, piece);
        return movements.stream()
                .filter(n -> checkmateController.isNonCheck(n , piece.getColor()))
                .collect(Collectors.toSet());
    }

    void crossBoardMovements(Set<Movement> movements, Piece piece, Function<OrderStruct, Boolean> direction){

        OrderStruct struct = struct(piece);
        while (direction.apply(struct)) {
            boolean hasMoreMovements = addOneStepMovement(movements, piece, struct);
            if (!hasMoreMovements){
                break;
            }
        }

    }

    void addMovement(Set<Movement> movements, Piece piece, Function<OrderStruct, Boolean> direction){
        OrderStruct struct = struct(piece);
        if(direction.apply(struct)){
            addOneStepMovement(movements, piece, struct);
        }
    }

    private boolean addOneStepMovement(Set<Movement> movements, Piece piece, OrderStruct struct){
        Cell from = piece.getCell();
        Cell to = new Cell(Char.get(struct.charOrder), Digit.get(struct.digitOrder));
        Optional<Piece> other = pieceController.byCell(to);
        if (!other.isPresent()) {
            movements.add(new Movement(MOVE, to, from));
            return true;
        } else {
            if (other.get().getColor() != piece.getColor()) {
                movements.add(new Movement(KILL, to, from));
            }
            return false;
        }
    }

    private OrderStruct struct(Piece piece){
        Cell from = piece.getCell();
        int charOrder = from.getChar().getOrder();
        int digitOrder = from.getDigit().getOrder();
        return new OrderStruct(charOrder, digitOrder);
    }




}
