package chess.view.display;

import chess.domain.cell.Cell;
import chess.repository.CellImageRepository;
import chess.repository.CellLayoutRepository;
import chess.view.image.CellImage;
import chess.view.layout.CellLayout;

/**
 * Created by nikitap4.92@gmail.com
 * 21.04.17.
 */
public class DefaultCellDisplay implements CellDisplay {
    private CellImageRepository imageRepository;
    private CellLayoutRepository repository;


    public DefaultCellDisplay(CellImageRepository imageRepository, CellLayoutRepository repository) {
        this.imageRepository = imageRepository;
        this.repository = repository;
    }

    @Override
    public void put(Cell cell) {
        CellImage cellImage = imageRepository.findBySelection(cell.getSelection());
        CellLayout container = repository.get(cell);
        container.putCellImage(cellImage);
    }

    @Override
    public void remove(Cell cell) {
        repository.get(cell).clearCellImage();
    }

    @Override
    public void clear() {
        repository.all().forEach(CellLayout::clearCellImage);
    }
}
