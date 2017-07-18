package chess.controller.analyzer;

import chess.controller.*;
import chess.domain.GameResult;
import chess.domain.cell.Cell;
import chess.domain.movement.Movement;
import chess.domain.movement.MovementType;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceColor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static chess.domain.movement.MovementType.KILL;
import static chess.domain.piece.PieceType.KING;
import static java.util.stream.Collectors.*;

/**
 * Created by nikitap4.92@gmail.com
 * 24.04.17.
 */
public class CheckmateAnalyzer implements CheckmateController {


    private final PieceController pieceController;
    private MovementController movementController;
    private final TurnController turnController;
    private final GameController gameController;

    public CheckmateAnalyzer(PieceController pieceController, TurnController turnController, GameController gameController) {
        this.pieceController = pieceController;
        this.turnController = turnController;
        this.gameController = gameController;
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
        if (target != null) {
            pieces.put(target.getCell(), target);
        }

        return !any.isPresent();
    }

    @Override
    public boolean isCheck() {
        return hasMovementsToKill().isPresent();
    }

    @Override
    public void analyze() {
        if (isCheck()) {
            pieceController.check();
            if (isCheckmate()) {
                if (turnController.whoseIsTurn() == PieceColor.WHITE)
                    gameController.over(GameResult.BLACK_WON);
                else gameController.over(GameResult.WHITE_WON);
            }
        }else if (isDraw()) {
            gameController.over(GameResult.DRAW);
        }
    }

    private boolean isCheckmate() {
        return !hasMovementToSave().isPresent();
    }


    private boolean isDraw() {
        return !hasAnyMovements();
    }

    private boolean hasAnyMovements() {
        List<Movement> movements = pieceController.pieces().values().stream()
                .filter(this::currentTurnColor)
                .flatMap(piece -> movementController.all(piece).stream())
                .collect(toList());
        return movements.stream().anyMatch(this::isNonCheck);
    }


    private boolean isPossibleToKillTheKing(Movement movement) {
        MovementType type = movement.getType();
        if (type != KILL) {
            return false;
        }
        Optional<Piece> piece = pieceController.byCell(movement.getTo());
        return piece.isPresent() && piece.get().getType() == KING;
    }

    private Optional<Movement> hasMovementsToKill() {
        return pieceController.pieces().values().stream()
                .filter(this::nextTurnColor)
                .flatMap(this::pieceMovements)
                .filter(this::isPossibleToKillTheKing)
                .findAny();
    }

    private Optional<Movement> hasMovementToSave() {
        return new HashMap<>(pieceController.pieces()).values().stream()
                .filter(this::currentTurnColor)
                .flatMap(this::pieceMovements)
                .filter(this::isNonCheck)
                .findAny();
    }

    private boolean currentTurnColor(Piece piece) {
        return turnController.whoseIsTurn() == piece.getColor();
    }


    private boolean nextTurnColor(Piece piece) {
        return turnController.whoseIsTurn() != piece.getColor();
    }

    private Stream<Movement> pieceMovements(Piece piece) {
        return movementController.all(piece).stream();
    }

}
