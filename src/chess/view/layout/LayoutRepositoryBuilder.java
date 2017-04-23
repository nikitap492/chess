package chess.view.layout;

import chess.domain.cell.Cell;
import chess.domain.cell.Char;
import chess.domain.cell.Digit;
import chess.view.Dimension;
import chess.view.Position;

/**
 * Created by nikitap4.92@gmail.com
 * 22.04.17.
 */
public class LayoutRepositoryBuilder {
    
    private double zeroX;
    private double zeroY;
    private double offsetX;
    private double offsetY;
    private double cellWidth;
    private double cellHeight;
    private CellLayoutFactory factory;

    public LayoutRepositoryBuilder setZeroX(double zeroX) {
        this.zeroX = zeroX;
        return this;
    }

    public LayoutRepositoryBuilder setZeroY(double zeroY) {
        this.zeroY = zeroY;
        return this;
    }

    public LayoutRepositoryBuilder setOffsetX(double offsetX) {
        this.offsetX = offsetX;
        return this;
    }

    public LayoutRepositoryBuilder setOffsetY(double offsetY) {
        this.offsetY = offsetY;
        return this;
    }

    public LayoutRepositoryBuilder setCellWidth(double cellWidth) {
        this.cellWidth = cellWidth;
        return this;
    }

    public LayoutRepositoryBuilder setCellHeight(double cellHeight) {
        this.cellHeight = cellHeight;
        return this;
    }

    public LayoutRepositoryBuilder setFactory(CellLayoutFactory factory) {
        this.factory = factory;
        return this;
    }

    public CellLayoutRepository build(){

        DefaultCellLayoutRepository repository = new DefaultCellLayoutRepository();
        for (Char c: Char.values())
            for (Digit d: Digit.values()){
                Position position = new Position(c.getOrder() * offsetX + zeroX, d.getOrder() * offsetY + zeroY);
                Dimension dimension = new Dimension(cellHeight , cellWidth);

                Cell cell = new Cell(c, d);
                CellLayout cellLayout = factory.create(cell, position, dimension);
                repository.put(cell, cellLayout);
            }
        return repository;
    }

    
}
