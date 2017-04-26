package chess.controller;

import chess.domain.cell.Cell;
import chess.domain.piece.Piece;

/**
 * Created by nikitap4.92@gmail.com
 * 22.04.17.
 */
public interface SelectController {

    void setTurnController(TurnController  turnController);

    boolean selectIsPossible(Cell cell);

    boolean selectIsPossible(Piece piece);


}
