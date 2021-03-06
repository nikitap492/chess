package chess.repository;

import chess.domain.cell.CellSelection;
import chess.view.image.CellImage;

/**
 * Created by nikitap4.92@gmail.com
 * 21.04.17.
 */
public interface CellImageRepository {

    CellImage findBySelection(CellSelection selection);
}
