package chess.view.layout;

import chess.domain.cell.Cell;
import chess.view.Dimension;
import chess.view.Position;

/**
 * Created by nikitap4.92@gmail.com
 * 22.04.17.
 */
interface CellLayoutFactory {

    CellLayout create(Cell cell, Position position, Dimension dimension);

}
