package chess.view.display;

import chess.domain.cell.Cell;

/**
 * Created by nikitap4.92@gmail.com
 * 21.04.17.
 */
public interface CellDisplay {

    void put(Cell cell);

    void remove(Cell cell);

    void clear();
}
