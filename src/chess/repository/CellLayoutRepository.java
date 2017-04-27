package chess.repository;

import chess.domain.cell.Cell;
import chess.view.layout.CellLayout;

import java.util.Collection;

/**
 * Created by nikitap4.92@gmail.com
 * 21.04.17.
 */
public interface CellLayoutRepository {

    void put(Cell cell, CellLayout layout);

    CellLayout get(Cell cell);

    Collection<CellLayout> all();


}
