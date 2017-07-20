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
public class MovementAnalyzeGround {

    private final PieceRepository pieceRepository;
    private Queue<Map<Cell, Piece>> experimental;

    public MovementAnalyzeGround(PieceRepository pieceRepository) {
        this.pieceRepository = pieceRepository;
        this.experimental = new LinkedList<>(Arrays.asList(pieceRepository.pieces()));
    }

    public Optional<Piece> byCell(Cell cell){
        return Optional.ofNullable(experimental.element().get(cell));
    }

    public Map<Cell, Piece> pieces(){
        return experimental.element();
    }

    public void newAnalyze(){
        this.experimental = new LinkedList<>(Arrays.asList(pieceRepository.pieces()));
    }

    public void replace(Movement movement) {
        Map<Cell, Piece> arrangement = new HashMap<>(experimental.element());
        movement.getKilled().ifPresent(piece -> arrangement.remove(piece.getCell()));
        arrangement.remove(movement.getFrom());
        arrangement.put(movement.getTo(), movement.getPiece());
        experimental.add(arrangement);
    }

    public void stepBack() {
        experimental.remove();
    }
}
