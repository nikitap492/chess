package javafx.view.image;

import chess.domain.cell.CellSelection;
import chess.view.image.CellImage;
import javafx.beans.NamedArg;
import javafx.scene.image.Image;

/**
 * Created by nikitap4.92@gmail.com
 * 23.04.17.
 */
public class CellImageWrapper extends AbstractImage implements CellImage {

    public CellImageWrapper(CellSelection selection) {
        super(getSelection(selection));
    }


    private static String getSelection(CellSelection selection){
        return selection.name().toLowerCase();
    }
}
