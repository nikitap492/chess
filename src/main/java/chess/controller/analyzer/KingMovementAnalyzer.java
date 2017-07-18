package chess.controller.analyzer;

import chess.controller.PieceController;
import chess.domain.cell.Cell;
import chess.domain.cell.Char;
import chess.domain.cell.Digit;
import chess.domain.movement.Movement;
import chess.domain.piece.Piece;
import chess.repository.PieceRepository;

import java.util.Optional;
import java.util.Set;

import static chess.domain.cell.Char.*;
import static chess.domain.movement.MovementType.CASTLING;

/**
 * Created by nikitap4.92@gmail.com
 * 24.04.17.
 */
class KingMovementAnalyzer extends PieceMovementAnalyzer {

    private final PieceRepository pieceRepository;

    KingMovementAnalyzer(PieceRepository pieceRepository) {
        super(pieceRepository);
        this.pieceRepository = pieceRepository;
    }

    @Override
    void addMovements(Set<Movement> empty, Piece piece) {
        addMovement(empty, piece, Directions::up);
        addMovement(empty, piece, Directions::left);
        addMovement(empty, piece, Directions::leftUp);
        addMovement(empty, piece, Directions::leftDown);
        addMovement(empty, piece, Directions::rightUp);
        addMovement(empty, piece, Directions::rightDown);
        addMovement(empty, piece, Directions::right);
        addMovement(empty, piece, Directions::down);
        rightCastling(empty, piece);
        leftCastling(empty, piece);
    }

    private void rightCastling(Set<Movement> movements, Piece piece) {
        if (!piece.isMoved()) {
            Cell cell = piece.getCell();
            Char aChar = cell.getChar();
            Digit digit = cell.getDigit();
            int i = aChar.getOrder() + 1;
            while (H.getOrder() > i) {
                Optional<Piece> optional = pieceRepository.byCell(Cell.of(get(i), digit));
                if (optional.isPresent()) {
                    return;
                }
                i++;
            }
            Optional<Piece> rook = pieceRepository.byCell(Cell.of(H, digit));
            if (rook.isPresent() && !rook.get().isMoved()) {
                movements.add(new Movement(piece, Cell.of(G, digit), CASTLING));
            }
        }
    }


    private void leftCastling(Set<Movement> movements, Piece piece) {
        if (!piece.isMoved()) {
            Cell cell = piece.getCell();
            Char aChar = cell.getChar();
            Digit digit = cell.getDigit();
            int i = aChar.getOrder() - 1;
            while (A.getOrder() < i) {
                Optional<Piece> optional = pieceRepository.byCell(Cell.of(get(i), digit));
                if (optional.isPresent()) {
                    return;
                }
                i--;
            }
            Optional<Piece> rook = pieceRepository.byCell(Cell.of(A, digit));
            if (rook.isPresent() && !rook.get().isMoved()) {
                movements.add(new Movement(piece, Cell.of(C, digit), CASTLING));
            }
        }
    }

}
