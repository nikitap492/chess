package chess.view.layout;

import chess.domain.cell.Cell;
import chess.repository.CellLayoutRepository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by nikitap4.92@gmail.com
 * 22.04.17.
 */
class DefaultCellLayoutRepository implements CellLayoutRepository {

    private Map<Cell, CellLayout> cellLayoutMap = new HashMap<>();

    @Override
    public void put(Cell cell, CellLayout layout) {
        cellLayoutMap.put(cell, layout);
    }

    @Override
    public CellLayout get(Cell cell) {
        return cellLayoutMap.get(cell);
    }

    @Override
    public Collection<CellLayout> all() {
        return cellLayoutMap.values();
    }
}
