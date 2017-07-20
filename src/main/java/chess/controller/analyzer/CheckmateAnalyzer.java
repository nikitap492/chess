package chess.controller.analyzer;

import chess.controller.*;
import chess.domain.game.GameResult;
import chess.domain.movement.Movement;
import chess.domain.movement.MovementType;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceColor;
import chess.repository.PieceRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static chess.domain.movement.MovementType.KILL;
import static chess.domain.piece.PieceType.KING;
import static java.util.stream.Collectors.toList;

/**
 * Created by nikitap4.92@gmail.com
 * 24.04.17.
 */
public class CheckmateAnalyzer implements CheckmateController {


    private final PieceRepository pieceRepository;
    private final PieceController pieceController;
    private MovementController movementController;
    private final TurnController turnController;
    private final GameController gameController;

    public CheckmateAnalyzer(PieceController pieceController, TurnController turnController, GameController gameController) {
        this.pieceRepository = pieceController.repository();
        this.turnController = turnController;
        this.gameController = gameController;
        this.pieceController = pieceController;
    }

    @Override
    public void setMovementController(MovementController movementController) {
        this.movementController = movementController;
    }

    @Override
    public boolean isNonCheck(Movement movement) {
        movementController.emulateMove(movement);
        Optional<Movement> any = hasMovementsToKill();
        movementController.emulationStepBack();
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
        gameController.nextTurn();
    }

    private boolean isCheckmate() {
        return !hasMovementToSave().isPresent();
    }


    private boolean isDraw() {
        return !hasAnyMovements();
    }

    private boolean hasAnyMovements() {
        List<Movement> movements = movementController.getAnalyzeGround().pieces().values().stream()
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
        Optional<Piece> piece = pieceRepository.byCell(movement.getTo());
        return piece.isPresent() && piece.get().getType() == KING;
    }

    private Optional<Movement> hasMovementsToKill() {
        return movementController.getAnalyzeGround().pieces().values().stream()
                .filter(this::nextTurnColor)
                .flatMap(this::pieceMovements)
                .filter(this::isPossibleToKillTheKing)
                .findAny();
    }

    private Optional<Movement> hasMovementToSave() {
        return new HashMap<>(movementController.getAnalyzeGround().pieces()).values().stream()
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
