package chess.controller.analyzer;

import chess.controller.*;
import chess.domain.cell.Cell;
import chess.domain.cell.Char;
import chess.domain.cell.Digit;
import chess.domain.movement.Movement;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceType;
import chess.repository.InMemoryMovementRepository;
import chess.repository.MovementRepository;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static chess.domain.cell.Char.*;
import static chess.domain.cell.Digit.EIGHT;
import static chess.domain.cell.Digit.ONE;
import static chess.domain.movement.MovementType.*;
import static chess.domain.piece.PieceType.PAWN;


/**
 * Created by nikitap4.92@gmail.com
 * 22.04.17.
 */
public class MovementAnalyzer implements MovementController {

    private MovementRepository movements;
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
    private final DialogController dialogController;

    public MovementAnalyzer(PieceController pieceController, CheckmateController checkmateController, TurnController turnController, CellController cellController, DialogController dialogController) {
        this.cellController = cellController;
        this.dialogController = dialogController;
        this.movements = new InMemoryMovementRepository();
        this.bishopMovementAnalyzer = new BishopMovementAnalyzer(pieceController);
        this.rookMovementAnalyzer = new RookMovementAnalyzer(pieceController);
        this.queenMovementAnalyzer = new QueenMovementAnalyzer(pieceController);
        this.kingMovementAnalyzer = new KingMovementAnalyzer(pieceController);
        this.pawnMovementAnalyzer = new PawnMovementAnalyser(pieceController, movements);
        this.knightMovementAnalyzer = new KnightMovementAnalyzer(pieceController);
        this.pieceController = pieceController;
        this.turnController = turnController;
        this.checkmateController = checkmateController;
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

    @Override
    public void undo() {
        Optional<Movement> undo = movements.undo();
        undo.ifPresent(last-> {
            pieceController.move(last.getPiece(), last.getFrom());
            cellController.clear();
            turnController.nextTurn();
            Optional<Piece> killed = last.getKilled();
            killed.ifPresent(pieceController::create);
        });

        boolean check = checkmateController.isCheck();
        if (check){
            pieceController.check();
        }
    }

    @Override
    public void clear() {
        movements.clear();
    }

    private void doMove(Movement movement) {
        movements.add(movement);
        switch (movement.getType()){
            case KILL:
                pieceController.kill(piece);
                movement.setKilled(pieceController.byCell(movement.getTo()).orElseThrow(RuntimeException::new));
                break;
            case CASTLING:
                doCastling(movement);
                break;
            case EN_PASSANT:
                killPawn(movement);
                break;
        }

       checkTransformForPawn(movement);

        pieceController.move(piece, movement.getTo());
        cellController.clear();
        turnController.nextTurn();
    }

    private void checkTransformForPawn(Movement movement){
        Digit digit = movement.getTo().getDigit();
        if (movement.getPiece().getType() == PAWN && (digit == ONE || digit == EIGHT)){
            PieceType pieceType = dialogController.transformDialog();
            pieceController.transform(piece, pieceType);
        }
    }

    private void killPawn(Movement movement) {
        Char aChar = movement.getTo().getChar();
        Digit digit = movement.getFrom().getDigit();
        Piece pawn = pieceController.byCell(Cell.of(aChar, digit)).orElseThrow(RuntimeException::new);
        pieceController.kill(pawn);
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
