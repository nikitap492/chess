package chess.controller.impl;

import chess.controller.*;
import chess.controller.analyzer.MovementAnalyzer;
import chess.domain.cell.Cell;
import chess.domain.cell.Char;
import chess.domain.cell.Digit;
import chess.domain.movement.Movement;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceType;
import chess.repository.InMemoryMovementRepository;
import chess.repository.MovementRepository;
import chess.repository.PieceRepository;

import java.util.Optional;
import java.util.Set;

import static chess.domain.cell.Char.*;
import static chess.domain.cell.Digit.EIGHT;
import static chess.domain.cell.Digit.ONE;
import static chess.domain.movement.MovementType.MOVE;
import static chess.domain.piece.PieceType.PAWN;

/**
 * @author Poshivalov N.A.
 * @since 20.07.2017.
 */
public class DefaultMovementController implements MovementController {
    private MovementRepository movements;
    private final PieceController pieceController;
    private final TurnController turnController;
    private final CheckmateController checkmateController;
    private final CellController cellController;
    private final DialogController dialogController;
    private final PieceRepository pieceRepository;
    private final GameController gameController;
    private final MovementAnalyzer movementAnalyzer;

    public DefaultMovementController(GameController gameController, PieceController pieceController, CheckmateController checkmateController,
                                     TurnController turnController, CellController cellController, DialogController dialogController) {
        this.cellController = cellController;
        this.dialogController = dialogController;
        this.pieceRepository = pieceController.repository();
        this.movements = new InMemoryMovementRepository();
        this.movementAnalyzer = new MovementAnalyzer(turnController, pieceRepository, movements);
        this.pieceController = pieceController;
        this.turnController = turnController;
        this.checkmateController = checkmateController;
        this.gameController = gameController;
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

        checkmateController.nextTurn();
    }

    @Override
    public void doMove(Movement movement) {
        movements.add(movement);
        switch (movement.getType()){
            case KILL:
                pieceController.kill(movement.getPiece());
                break;
            case CASTLING:
                doCastling(movement);
                break;
            case EN_PASSANT:
                killPawn(movement);
                break;
        }

        checkTransformForPawn(movement);

        pieceController.move(movement.getPiece(), movement.getTo());
        cellController.clear();
        turnController.nextTurn();
        gameController.nextTurn();
    }

    @Override
    public Set<Movement> forPiece(Piece piece) {
        return movementAnalyzer.possible(piece);
    }

    @Override
    public MovementAnalyzer analyzer() {
        return movementAnalyzer;
    }

    @Override
    public void doMove(Piece piece, Cell cell) {
        movementAnalyzer.possible(piece).stream()
                .filter(movement -> movement.getTo().equals(cell))
                .findFirst().ifPresent(this::doMove);
    }

    private void checkTransformForPawn(Movement movement){
        Digit digit = movement.getTo().getDigit();
        if (movement.getPiece().getType() == PAWN && (digit == ONE || digit == EIGHT)){
            PieceType pieceType = dialogController.transformDialog();
            pieceController.transform(movement.getPiece(), pieceType);
        }
    }

    private void killPawn(Movement movement) {
        Char aChar = movement.getTo().getChar();
        Digit digit = movement.getFrom().getDigit();
        Piece pawn = pieceRepository.byCell(Cell.of(aChar, digit)).orElseThrow(RuntimeException::new);
        pieceController.kill(pawn);
    }

    private void doCastling(Movement movement){
        Cell cell = movement.getPiece().getCell();
        Digit digit = cell.getDigit();
        Piece rook;
        Cell to = movement.getTo();
        if (to.getChar() == C){
            rook = pieceRepository.byCell(new Cell(A, digit))
                    .orElseThrow(RuntimeException::new);
            to = Cell.of(D, digit);
        }else {
            rook= pieceRepository.byCell(new Cell(H, digit))
                    .orElseThrow(RuntimeException::new);
            to = Cell.of(F, digit);
        }
        rookCastlingMovement(rook, to);

    }

    private void rookCastlingMovement(Piece rook, Cell to){
        movements.add(new Movement(rook, to, MOVE, null));
        pieceController.move(rook, to);
    }

}
