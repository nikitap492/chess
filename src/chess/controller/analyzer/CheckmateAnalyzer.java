package chess.controller.analyzer;

import chess.controller.CheckmateController;
import chess.controller.MovementController;
import chess.controller.PieceController;
import chess.controller.TurnController;
import chess.domain.cell.Cell;
import chess.domain.movement.Movement;
import chess.domain.movement.MovementType;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceColor;
import chess.domain.piece.PieceType;

import java.util.Map;
import java.util.Optional;

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
        PieceColor color = piece.getColor();
        Map<Cell, Piece> pieces = pieceController.pieces();

        Piece target = pieces.get(movement.getTo());

        pieces.remove(piece.getCell());

        Piece copy = new Piece(piece);
        copy.setCell(movement.getTo());
        pieces.put(copy.getCell(), copy);

        Optional<Movement> any = pieces.values()
                .stream()
                .filter(n -> n.getColor() != color)
                .flatMap(p -> movementController.all(p).stream())
                .filter(this::isPossibleToKillTheKing)
                .findAny();

        pieces.remove(copy.getCell());
        pieces.put(piece.getCell(), piece);
        if(target != null){
            pieces.put(target.getCell(), target);
        }

        return !any.isPresent();
    }

    @Override
    public boolean isCheck(){
        PieceColor color = turnController.whoseIsTurn();
        Optional<Movement> any = pieceController.pieces()
                .values()
                .stream()
                .filter(n -> n.getColor() != color)
                .flatMap(p -> movementController.all(p).stream())
                .filter(this::isPossibleToKillTheKing)
                .findAny();
        return any.isPresent();
    }


    private boolean isPossibleToKillTheKing(Movement movement) {
        MovementType type = movement.getType();
        if (type != KILL) {
            return false;
        }
        Optional<Piece> piece = pieceController.byCell(movement.getTo());
        if (piece.isPresent()) {
            PieceType pieceType = piece.get().getType();
            if (pieceType == KING) return true;
        }
        return false;
    }


}
