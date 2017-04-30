package chess.controller.analyzer;

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

import static chess.domain.movement.MovementType.KILL;
import static chess.domain.movement.MovementType.MOVE;

/**
 * Created by nikitap4.92@gmail.com
 * 24.04.17.
 */
abstract class PieceMovementAnalyzer {

    private PieceController pieceController;

    PieceMovementAnalyzer(PieceController pieceController) {
        this.pieceController = pieceController;
    }

    abstract void addMovements(Set<Movement> empty, Piece piece);


    Set<Movement> analyze(Piece piece){
        Set<Movement> movements = new HashSet<>();
        addMovements(movements, piece);
        return movements;
    }

    void crossBoardMovements(Set<Movement> movements, Piece piece, Function<OrderStruct, Boolean> direction){

        OrderStruct struct = OrderStruct.of(piece);
        while (direction.apply(struct)) {
            boolean hasMoreMovements = addOneStepMovement(movements, piece, struct);
            if (!hasMoreMovements){
                break;
            }
        }

    }

    void addMovement(Set<Movement> movements, Piece piece, Function<OrderStruct, Boolean> direction){
        OrderStruct struct = OrderStruct.of(piece);
        if(direction.apply(struct)){
            addOneStepMovement(movements, piece, struct);
        }
    }

    private boolean addOneStepMovement(Set<Movement> movements, Piece piece, OrderStruct struct){
        Cell to = byStruct(struct);
        Optional<Piece> other = pieceController.byCell(to);
        if (!other.isPresent()) {
            movements.add(new Movement(piece, to, MOVE));
            return true;
        } else {
            if (other.get().getColor() != piece.getColor()) {
                movements.add(new Movement(piece, to, KILL));
            }
            return false;
        }
    }

    Cell byStruct(OrderStruct struct){
        return Cell.of(Char.get(struct.charOrder), Digit.get(struct.digitOrder));
    }



}
