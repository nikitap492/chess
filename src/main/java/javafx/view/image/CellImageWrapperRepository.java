package javafx.view.image;

import chess.domain.cell.CellSelection;
import chess.view.image.CellImage;
import chess.repository.CellImageRepository;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nikitap4.92@gmail.com
 * 23.04.17.
 */
public class CellImageWrapperRepository implements CellImageRepository {

    private Map<CellSelection, CellImage> map = new HashMap<>();

    public CellImageWrapperRepository() {
        for (CellSelection selection: CellSelection.values()){
            map.put(selection, new CellImageWrapper(selection));
        }
    }



    @Override
    public CellImage findBySelection(CellSelection selection) {
        return map.get(selection);
    }
}
