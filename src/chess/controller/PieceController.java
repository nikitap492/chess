package chess.controller;

import chess.command.Subscriber;
import chess.domain.cell.Cell;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceType;
import chess.view.PieceView;
import chess.view.display.PieceDisplay;

import java.util.Optional;

/**
 * @author Poshivalov Nikita
 * @since 20.04.2017.
 */
public interface PieceController extends Subscriber<PieceView> {

    void arrangePieces();

    void setPieceDisplay(PieceDisplay pieceDisplay);

    void setCellController(CellController cellController);

    void setMovementController(MovementController movementController);

    void setSelectController(SelectController selectController);

    void move(Piece piece, Cell cell);

    void kill(Piece piece);

    void create(Piece piece);

    void transform(Piece piece, PieceType pieceType);

    Optional<Piece> byCell(Cell cell);

}
