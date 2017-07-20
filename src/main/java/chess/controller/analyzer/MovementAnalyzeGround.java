package chess.controller.analyzer;

import chess.domain.cell.Cell;
import chess.domain.movement.Movement;
import chess.domain.piece.Piece;
import chess.repository.PieceRepository;

import java.util.*;

/**
 * @author Poshivalov N.A.
 * @since 19.07.2017.
 */
class MovementAnalyzeGround {

    private final PieceRepository pieceRepository;
    private Deque<Map<Cell, Piece>> experimental;

    MovementAnalyzeGround(PieceRepository pieceRepository) {
        this.pieceRepository = pieceRepository;
        this.experimental = new LinkedList<>(Arrays.asList(pieceRepository.pieces()));
    }

    Optional<Piece> byCell(Cell cell){
        return Optional.ofNullable(experimental.getLast().get(cell));
    }

    Map<Cell, Piece> pieces(){
        return experimental.getLast();
    }

    void newAnalyze(){
        this.experimental = new LinkedList<>(Arrays.asList(pieceRepository.pieces()));
    }

    void replace(Movement movement) {
        Map<Cell, Piece> arrangement = new HashMap<>(experimental.getLast());
        movement.getKilled().ifPresent(piece -> arrangement.remove(piece.getCell()));
        arrangement.remove(movement.getFrom());
        arrangement.put(movement.getTo(), movement.getPiece());
        experimental.addLast(arrangement);
    }

    void stepBack() {
        experimental.removeLast();
    }
}
