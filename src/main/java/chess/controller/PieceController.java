package chess.controller;

import chess.command.ClickSubscriber;
import chess.domain.cell.Cell;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceType;
import chess.repository.PieceRepository;
import chess.view.PieceView;

import java.util.Optional;

/**
 * @author Poshivalov Nikita
 * @since 20.04.2017.
 */
public interface PieceController extends ClickSubscriber<PieceView> {

    void setCellController(CellController cellController);

    void setMovementController(MovementController movementController);

    void setCheckmateController(CheckmateController checkmateController);

    void move(Piece piece, Cell cell);

    void kill(Piece piece);

    void create(Piece piece);

    void transform(Piece piece, PieceType pieceType);

    void check();

    void arrangePieces();

    PieceRepository repository();

    Optional<Piece> selected();
}
