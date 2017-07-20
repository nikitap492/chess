package chess.controller.analyzer;

import chess.controller.TurnController;
import chess.domain.movement.Movement;
import chess.domain.movement.MovementType;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceType;
import chess.repository.MovementRepository;
import chess.repository.PieceRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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

    public MovementAnalyzer(TurnController turnController, PieceRepository pieceRepository,  MovementRepository movements) {
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

    public Set<Movement> all(Piece piece){
        PieceType type = piece.getType();
        switch (type){
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
        return null;
    }

    public void clear() {
        analyzeGround.newAnalyze();
    }

    public void emulateMove(Movement movement){
        analyzeGround.replace(movement);
    }

    public Stream<Movement> possible() {
        return pieceRepository.pieces().values().stream()
                .filter(piece -> piece.getColor() == turnController.whoseIsTurn())
                .flatMap(piece -> all(piece).stream())
                .filter(this::isNonCheck);
    }

    public void emulationStepBack() {
        analyzeGround.stepBack();
    }


    public MovementAnalyzeGround getAnalyzeGround() {
        return analyzeGround;
    }



    public boolean hasAnyMovements() {
        List<Movement> movements = analyzeGround.pieces().values().stream()
                .filter(this::currentTurnColor)
                .flatMap(piece -> all(piece).stream())
                .collect(toList());
        return movements.stream().anyMatch(this::isNonCheck);
    }


    private boolean isPossibleToKillTheKing(Movement movement) {
        MovementType type = movement.getType();
        if (type != KILL) {
            return false;
        }
        Optional<Piece> piece = analyzeGround.byCell(movement.getTo());
        return piece.isPresent() && piece.get().getType() == KING;
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

    private boolean currentTurnColor(Piece piece) {
        return turnController.whoseIsTurn() == piece.getColor();
    }


    private boolean nextTurnColor(Piece piece) {
        return turnController.whoseIsTurn() != piece.getColor();
    }

    private Stream<Movement> pieceMovements(Piece piece) {
        return all(piece).stream();
    }

    public boolean isNonCheck(Movement movement) {
        emulateMove(movement);
        Optional<Movement> any = hasMovementsToKillKing();
        emulationStepBack();
        return !any.isPresent();
    }


}
