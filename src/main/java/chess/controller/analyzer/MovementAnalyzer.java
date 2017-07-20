package chess.controller.analyzer;

import chess.controller.TurnController;
import chess.domain.cell.Cell;
import chess.domain.movement.Movement;
import chess.domain.movement.MovementType;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceType;
import chess.repository.MovementRepository;
import chess.repository.PieceRepository;

import java.util.*;
import java.util.stream.Stream;

import static chess.domain.movement.MovementType.KILL;
import static chess.domain.piece.PieceType.KING;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;


/**
 * Created by nikitap4.92@gmail.com
 * 22.04.17.
 */
public class MovementAnalyzer {


    private final BishopMovementAnalyzer bishopMovementAnalyzer;
    private final RookMovementAnalyzer rookMovementAnalyzer;
    private final QueenMovementAnalyzer queenMovementAnalyzer;
    private final KingMovementAnalyzer kingMovementAnalyzer;
    private final PawnMovementAnalyser pawnMovementAnalyzer;
    private final KnightMovementAnalyzer knightMovementAnalyzer;
    private final TurnController turnController;
    private final PieceRepository pieceRepository;
    private final MovementAnalyzeGround analyzeGround;

    public MovementAnalyzer(TurnController turnController, PieceRepository pieceRepository, MovementRepository movements) {
        this.pieceRepository = pieceRepository;
        this.analyzeGround = new MovementAnalyzeGround(pieceRepository);
        this.bishopMovementAnalyzer = new BishopMovementAnalyzer(analyzeGround);
        this.rookMovementAnalyzer = new RookMovementAnalyzer(analyzeGround);
        this.queenMovementAnalyzer = new QueenMovementAnalyzer(analyzeGround);
        this.kingMovementAnalyzer = new KingMovementAnalyzer(analyzeGround);
        //todo think later about movements
        this.pawnMovementAnalyzer = new PawnMovementAnalyser(analyzeGround, movements);
        this.knightMovementAnalyzer = new KnightMovementAnalyzer(analyzeGround);
        this.turnController = turnController;
    }

    public Set<Movement> possible(Piece piece) {
        analyzeGround.newAnalyze();
        return all(piece).stream()
                .filter(this::isNonCheck)
                .collect(toSet());
    }


    public void clear() {
        analyzeGround.newAnalyze();
    }


    public boolean hasAnyMovements() {
        List<Movement> movements = analyzeGround.pieces().values().stream()
                .filter(this::currentTurnColor)
                .flatMap(piece -> all(piece).stream())
                .collect(toList());
        return movements.stream().anyMatch(this::isNonCheck);
    }

    Movement aiMovement() {
        //todo
        return analyzeGround.pieces().values().stream()
                .filter(piece -> piece.getColor() == turnController.whoseIsTurn())
                .flatMap(piece -> all(piece).stream())
                .filter(this::isNonCheck)
                .max((m1, m2) -> relativeValue(m1) - relativeValue(m2) - maxEnemyMovementValue(m1) + maxEnemyMovementValue(m2)).orElse(null);
    }


    private int maxEnemyMovementValue(Movement movement) {
        Map<Cell, Piece> pieces = analyzeGround.pieces();
        emulateMove(movement);
        turnController.nextTurn();
        int value = analyzeGround.pieces().values().stream()
                .filter(piece -> piece.getColor() == turnController.whoseIsTurn())
                .flatMap(piece -> all(piece).stream())
                .mapToInt(MovementAnalyzer::relativeValue)
                .max().orElse(Integer.MAX_VALUE);
        emulationStepBack();
        if (!analyzeGround.pieces().equals(pieces)) throw new RuntimeException();

        return value;
    }

    private static int relativeValue(Movement movement) {
        return movement.getKilled()
                .map(Piece::getType)
                .map(pieceType -> {
                    switch (pieceType) {
                        case PAWN:
                            return 1;
                        case KNIGHT:
                            return 3;
                        case BISHOP:
                            return 3;
                        case ROOK:
                            return 5;
                        case QUEEN:
                            return 9;
                        case KING:
                            return 999;
                    }
                    return 0;
                }).orElse(0);
    }


    public Optional<Movement> hasMovementsToKillKing() {
        return analyzeGround.pieces().values().stream()
                .filter(this::nextTurnColor)
                .flatMap(this::pieceMovements)
                .filter(this::isPossibleToKillTheKing)
                .findAny();
    }

    public Optional<Movement> hasMovementToSaveKing() {
        return new HashMap<>(analyzeGround.pieces()).values().stream()
                .filter(this::currentTurnColor)
                .flatMap(this::pieceMovements)
                .filter(this::isNonCheck)
                .findAny();
    }

    private void emulationStepBack() {
        analyzeGround.stepBack();
    }

    private boolean isPossibleToKillTheKing(Movement movement) {
        MovementType type = movement.getType();
        if (type != KILL) {
            return false;
        }
        Optional<Piece> piece = analyzeGround.byCell(movement.getTo());
        return piece.isPresent() && piece.get().getType() == KING;
    }


    private boolean currentTurnColor(Piece piece) {
        return turnController.whoseIsTurn() == piece.getColor();
    }


    private boolean nextTurnColor(Piece piece) {
        return turnController.whoseIsTurn() != piece.getColor();
    }

    private Stream<Movement> pieceMovements(Piece piece) {
        return all(piece).stream();
    }

    private void emulateMove(Movement movement) {
        analyzeGround.replace(movement);
    }

    private boolean isNonCheck(Movement movement) {
        emulateMove(movement);
        Optional<Movement> any = hasMovementsToKillKing();
        emulationStepBack();
        return !any.isPresent();
    }


    private Set<Movement> all(Piece piece) {
        PieceType type = piece.getType();
        switch (type) {
            case PAWN:
                return pawnMovementAnalyzer.analyze(piece);
            case KNIGHT:
                return knightMovementAnalyzer.analyze(piece);
            case BISHOP:
                return bishopMovementAnalyzer.analyze(piece);
            case ROOK:
                return rookMovementAnalyzer.analyze(piece);
            case QUEEN:
                return queenMovementAnalyzer.analyze(piece);
            case KING:
                return kingMovementAnalyzer.analyze(piece);
        }
        return Collections.emptySet();
    }


}
