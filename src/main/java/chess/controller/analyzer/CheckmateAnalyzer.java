package chess.controller.analyzer;

import chess.controller.CheckmateController;
import chess.controller.MovementController;
import chess.controller.PieceController;
import chess.controller.TurnController;
import chess.domain.cell.Cell;
import chess.domain.movement.Movement;
import chess.domain.movement.MovementType;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceType;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static chess.domain.movement.MovementType.KILL;
import static chess.domain.piece.PieceType.KING;

/**
 * Created by nikitap4.92@gmail.com
 * 24.04.17.
 */
public class CheckmateAnalyzer implements CheckmateController {


    private final PieceController pieceController;
    private MovementController movementController;
    private final TurnController turnController;

    public CheckmateAnalyzer(PieceController pieceController, TurnController turnController) {
        this.pieceController = pieceController;
        this.turnController = turnController;
    }

    @Override
    public void setMovementController(MovementController movementController) {
        this.movementController = movementController;
    }

    @Override
    public boolean isNonCheck(Movement movement) {
        Piece piece = movement.getPiece();
        Map<Cell, Piece> pieces = pieceController.pieces();

        Piece target = pieces.get(movement.getTo());

        pieces.remove(piece.getCell());

        Piece copy = new Piece(piece);
        copy.setCell(movement.getTo());
        pieces.put(copy.getCell(), copy);


        Optional<Movement> any = hasMovementsToKill();

        pieces.remove(copy.getCell());
        pieces.put(piece.getCell(), piece);
        if(target != null){
            pieces.put(target.getCell(), target);
        }

        return !any.isPresent();
    }

    @Override
    public boolean isCheck(){
        return hasMovementsToKill().isPresent();
    }

    @Override
    public boolean isCheckmate() {
        return !hasMovementToSave().isPresent();
    }

    @Override
    public boolean isDraw() {
        return !hasAnyMovements();
    }

    private boolean hasAnyMovements() {
        return pieceController.pieces().values().stream()
                .filter(this::nextTurnColor)
                .flatMap(piece -> movementController.possible(piece).stream())
                .findFirst()
                .isPresent();
    }


    private boolean isPossibleToKillTheKing(Movement movement) {
        MovementType type = movement.getType();
        if (type != KILL) {
            return false;
        }
        Optional<Piece> piece = pieceController.byCell(movement.getTo());
        return piece.isPresent() && piece.get().getType() == KING;
    }

    private Optional<Movement> hasMovementsToKill(){
        return pieceController.pieces().values().stream()
                .filter(this::nextTurnColor)
                .flatMap(this::pieceMovements)
                .filter(this::isPossibleToKillTheKing)
                .findAny();
    }

    private Optional<Movement> hasMovementToSave(){
        return new HashMap<>(pieceController.pieces()).values().stream()
                .filter(p -> p.getColor() == turnController.whoseIsTurn())
                .flatMap(this::pieceMovements)
                .filter(this::isNonCheck)
                .findAny();
    }


    private boolean nextTurnColor(Piece piece){
        return turnController.whoseIsTurn() != piece.getColor();
    }

    private Stream<Movement> pieceMovements(Piece piece){
        return movementController.all(piece).stream();
    }

}
