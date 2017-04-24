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
class BishopMovementAnalyzer {
    
    
    private PieceController pieceController;
    private CheckmateController checkmateController;

    BishopMovementAnalyzer(PieceController pieceController, CheckmateController checkmateController) {
        this.pieceController = pieceController;
        this.checkmateController = checkmateController;
    }

    Set<Movement> analyze(Piece piece){
        Set<Movement> movements = new HashSet<>();
        addMovements(movements, piece);
        return movements.stream()
                .filter(n -> checkmateController.isNonCheck(n , piece.getColor()))
                .collect(Collectors.toSet());
    }

    private void addMovements(Set<Movement> movements, Piece piece){
        movements.addAll(movements(piece, BishopMovementAnalyzer::leftUp));
        movements.addAll(movements(piece, BishopMovementAnalyzer::rightUp));
        movements.addAll(movements(piece, BishopMovementAnalyzer::rightDown));
        movements.addAll(movements(piece, BishopMovementAnalyzer::leftDown));
    }

    private Set<Movement> movements(Piece piece, Function<OrderStruct, Boolean> direction){
        Set<Movement> movements = new HashSet<>();
        Cell from = piece.getCell();
        int charOrder = from.getChar().getOrder();
        int digitOrder = from.getDigit().getOrder();
        OrderStruct struct = new OrderStruct(charOrder, digitOrder);

        while (direction.apply(struct)) {
            Cell to = new Cell(Char.get(struct.charOrder), Digit.get(struct.digitOrder));
            Optional<Piece> other = pieceController.byCell(to);
            if (!other.isPresent()) {
                movements.add(new Movement(MOVE, to, from));
            } else {
                if (other.get().getColor() != piece.getColor()) {
                    movements.add(new Movement(KILL, to, from));
                }
                break;
            }
        }
        return movements;
    }

    static boolean leftUp(OrderStruct struct){
            return  ++struct.digitOrder <= 8 && --struct.charOrder >= 0;
    }

    static boolean rightUp(OrderStruct struct){
        return  ++struct.digitOrder <= 8 && ++struct.charOrder <= 8;
    }

    static boolean rightDown(OrderStruct struct){
        return  --struct.charOrder >= 0  && ++struct.digitOrder <= 8;
    }

    static boolean leftDown(OrderStruct struct){
        return  --struct.digitOrder >= 0  && --struct.charOrder >= 0;
    }
    


    

}
