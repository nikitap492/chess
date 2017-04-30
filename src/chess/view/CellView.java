package chess.view;


import chess.domain.cell.Cell;
import chess.view.image.CellImage;

/**
 * Created by nikitap4.92@gmail.com
 * 21.04.17.
 */
public interface CellView extends View<CellImage>{

    Cell cell();

}
