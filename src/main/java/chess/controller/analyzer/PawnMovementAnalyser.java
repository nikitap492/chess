package chess.controller.analyzer;

import chess.controller.PieceController;
import chess.domain.cell.Cell;
import chess.domain.cell.Digit;
import chess.domain.movement.Movement;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceColor;
import chess.repository.MovementRepository;
import chess.repository.PieceRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import static chess.domain.cell.Digit.FIVE;
import static chess.domain.cell.Digit.FOUR;
import static chess.domain.movement.MovementType.*;
import static chess.domain.piece.PieceColor.BLACK;
import static chess.domain.piece.PieceColor.WHITE;
import static chess.domain.piece.PieceType.PAWN;

/**
 * Created by nikitap4.92@gmail.com
 * 23.04.17.
 */
class PawnMovementAnalyser extends PieceMovementAnalyzer {


    private final PieceRepository pieceRepository;
    private final MovementRepository repository;

    PawnMovementAnalyser(PieceRepository pieceRepository, MovementRepository repository) {
        super(pieceRepository);
        this.pieceRepository = pieceRepository;
        this.repository = repository;
    }

    @Override
    void addMovements(Set<Movement> empty, Piece piece) {
        boolean isBlocked = oneStep(empty, piece);
        if (!isBlocked) {
            doubleStep(empty, piece);
        }
        kill(empty, piece);
        enPassant(empty, piece);
    }

    private boolean oneStep(Set<Movement> movements, Piece piece) {
        PieceColor color = piece.getColor();
        int order = piece.getCell().getDigit().getOrder();
        switch (color) {
            case BLACK:
                if (order > 0) {
                Digit digit = Digit.get(--order);
                return addStep(movements, piece, digit);
            }
            break;
            case WHITE:
                if (order < 7) {
                    Digit digit = Digit.get(++order);
                    return addStep(movements, piece, digit);
                }
        }
        return true;
    }

    private void doubleStep(Set<Movement> movements, Piece piece) {
        boolean moved = piece.isMoved();
        PieceColor color = piece.getColor();
        if (!moved) {
            Digit digit = null;
            switch (color) {
                case BLACK:
                    digit = FIVE;
                    break;
                case WHITE:
                    digit = FOUR;
                    break;
            }
            addStep(movements, piece, digit);
        }
    }


    private void kill(Set<Movement> movements, Piece piece) {
        Set<Movement> kills = new HashSet<>();
        PieceColor color = piece.getColor();
        switch (color) {
            case BLACK:
                addMovement(kills, piece, Directions::leftDown);
                addMovement(kills, piece, Directions::rightDown);
                break;
            case WHITE:
                addMovement(kills, piece, Directions::leftUp);
                addMovement(kills, piece, Directions::rightUp);
                break;
        }
        kills.stream()
                .filter(m -> m.getType() == KILL)
                .forEach(movements::add);
    }

    private boolean addStep(Set<Movement> movements, Piece piece, Digit digit) {
        Cell cell = Cell.of(piece.getCell().getChar(), digit);
        Optional<Piece> optional = pieceRepository.byCell(cell);
        if (!optional.isPresent()) {
            movements.add(new Movement(piece, cell, MOVE));
            return false;
        }
        return true;
    }


    private void enPassant(Set<Movement> empty, Piece piece) {
        PieceColor color = piece.getColor();
        Cell cell = piece.getCell();
        Digit digit = cell.getDigit();
        if (digit == FIVE && color == WHITE) {
            enPassantProcess(empty, piece, Directions::left, Directions:: leftUp);
            enPassantProcess(empty, piece, Directions::right, Directions:: rightUp);
        }
        if (digit == FOUR && color == BLACK) {
            enPassantProcess(empty, piece, Directions::left, Directions:: leftDown);
            enPassantProcess(empty, piece, Directions::right, Directions:: rightDown);
        }

    }

    private void enPassantProcess(Set<Movement> empty, Piece piece, Function<OrderStruct, Boolean> enemyDirection
            , Function<OrderStruct, Boolean> destinationDirection ){
        PieceColor color = piece.getColor();
        Optional<Piece> enemy = piece(piece, enemyDirection);
        if (enemy.isPresent() && isCorrectPawn(enemy.get(), color) && isPawnNotCovered(piece, destinationDirection)) {
            Cell to = destinationEnPassant(piece, destinationDirection);
            empty.add(new Movement(piece, to, EN_PASSANT));
        }

    }

    private Optional<Piece> piece(Piece piece, Function<OrderStruct, Boolean> direction) {
        OrderStruct struct = OrderStruct.of(piece);
        Optional<Piece> other = Optional.empty();
        if (direction.apply(struct)) {
            Cell c = byStruct(struct);
            other = pieceRepository.byCell(c);
        }
        return other;
    }

    private boolean isCorrectPawn(Piece piece, PieceColor color) {
        Movement last = repository.last();
        if (!piece.getCell().equals(last.getTo())) return false;

        int orderTo = last.getTo().getDigit().getOrder();
        int orderFrom = last.getFrom().getDigit().getOrder();
        return piece.getColor() != color && piece.getType() == PAWN && Math.abs(orderTo - orderFrom) == 2;
    }

    private boolean isPawnNotCovered(Piece piece, Function<OrderStruct, Boolean> direction) {
        return !piece(piece, direction).isPresent();
    }

    private Cell destinationEnPassant(Piece piece, Function<OrderStruct, Boolean> direction){
        OrderStruct struct = OrderStruct.of(piece);
        direction.apply(struct);
        return byStruct(struct);
    }


}
