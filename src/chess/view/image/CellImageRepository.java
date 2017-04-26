package chess.view.image;

import chess.domain.cell.CellSelection;

/**
 * Created by nikitap4.92@gmail.com
 * 21.04.17.
 */
public interface CellImageRepository {

    CellImage findBySelection(CellSelection selection);
}
