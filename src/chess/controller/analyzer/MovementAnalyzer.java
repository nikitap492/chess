package chess.controller.analyzer;

import chess.controller.*;
import chess.domain.cell.Cell;
import chess.domain.cell.Digit;
import chess.domain.movement.Movement;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static chess.domain.cell.Char.*;
import static chess.domain.movement.MovementType.*;


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
    private final CellController cellController;

    public MovementAnalyzer(PieceController pieceController, CheckmateController checkmateController, TurnController turnController, CellController cellController) {
        this.cellController = cellController;
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
        if (movement.getType() == KILL){
            pieceController.kill(piece);
        }
        if (movement.getType() == CASTLING){
            doCastling(movement);
        }
        pieceController.move(piece, movement.getTo());
        cellController.clear();
        turnController.nextTurn();
        boolean check = checkmateController.isCheck();
        if (check){
            pieceController.check();
        }
    }

    private void doCastling(Movement movement){
        Cell cell = movement.getPiece().getCell();
        Digit digit = cell.getDigit();
        Piece rook;
        Cell to = movement.getTo();
        if (to.getChar() == C){
            rook = pieceController.byCell(new Cell(A, digit))
                    .orElseThrow(RuntimeException::new);
            to = Cell.of(D, digit);
        }else {
            rook= pieceController.byCell(new Cell(H, digit))
                    .orElseThrow(RuntimeException::new);
            to = Cell.of(F, digit);
        }
        rookCastlingMovement(rook, to);

    }

    private void rookCastlingMovement(Piece rook, Cell to){
        movements.add(new Movement(rook, to, MOVE));
        pieceController.move(rook, to);
    }

}
