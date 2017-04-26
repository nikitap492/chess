package chess.controller.analyzer;

import chess.controller.CheckmateController;
import chess.controller.MovementController;
import chess.controller.PieceController;
import chess.controller.TurnController;
import chess.domain.cell.Cell;
import chess.domain.movement.Movement;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static chess.domain.movement.MovementType.CHECK;
import static chess.domain.movement.MovementType.KILL;


/**
 * Created by nikitap4.92@gmail.com
 * 22.04.17.
 */
public class MovementAnalyzer implements MovementController {

    private List<Movement> movements;
    private Set<Movement> possibleMovements;
    private Piece piece;
    private final BishopMovementAnalyzer bishopMovementAnalyzer;
    private final RookMovementAnalyzer rookMovementAnalyzer;
    private final QueenMovementAnalyzer queenMovementAnalyzer;
    private final KingMovementAnalyzer kingMovementAnalyzer;
    private final PawnMovementAnalyser pawnMovementAnalyzer;
    private final KnightMovementAnalyzer knightMovementAnalyzer;
    private final PieceController pieceController;
    private final TurnController turnController;
    private final CheckmateController checkmateController;

    public MovementAnalyzer(PieceController pieceController, CheckmateController checkmateController, TurnController turnController) {
        bishopMovementAnalyzer = new BishopMovementAnalyzer(pieceController, checkmateController);
        rookMovementAnalyzer = new RookMovementAnalyzer(pieceController, checkmateController);
        queenMovementAnalyzer = new QueenMovementAnalyzer(pieceController, checkmateController);
        kingMovementAnalyzer = new KingMovementAnalyzer(pieceController, checkmateController);
        pawnMovementAnalyzer = new PawnMovementAnalyser(pieceController, checkmateController);
        knightMovementAnalyzer = new KnightMovementAnalyzer(pieceController, checkmateController);
        this.pieceController = pieceController;
        this.turnController = turnController;
        this.checkmateController = checkmateController;
        movements = new ArrayList<>();
    }

    @Override
    public Set<Movement> possible(Piece piece) {
        this.piece = piece;
        possibleMovements =  all(piece).stream()
                .filter(checkmateController::isNonCheck)
                .collect(Collectors.toSet());
        return possibleMovements;
    }

    @Override
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

    @Override
    public void moveToCell(Cell cell) {
        Optional<Movement> any = possibleMovements.stream()
                .filter(movement -> movement.getTo().equals(cell))
                .findFirst();
        any.ifPresent(this::doMove);
    }

    private void doMove(Movement movement) {
        movements.add(movement);
        if(movement.getType() == CHECK){
            checkmateController.check();
        }
        if (movement.getType() == KILL){
            pieceController.kill(piece);
        }
        pieceController.move(piece, movement.getTo());
        turnController.nextTurn();
    }

}
