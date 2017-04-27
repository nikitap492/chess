package chess.domain.cell;

/**
 * @author Poshivalov Nikita
 * @since 14.04.2017.
 */
public class Cell {
    private Digit digit;
    private Char aChar;
    private CellSelection selection;

    public Cell( Char aChar, Digit digit) {
        this.digit = digit;
        this.aChar = aChar;
    }

    public CellSelection getSelection() {
        return selection;
    }

    public void setSelection(CellSelection selection) {
        this.selection = selection;
    }

    public Digit getDigit() {
        return digit;
    }

    public Char getChar() {
        return aChar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cell cell = (Cell) o;

        return digit == cell.digit && aChar == cell.aChar;

    }

    @Override
    public int hashCode() {
        int result = digit.hashCode();
        result = 31 * result + aChar.hashCode();
        return result;
    }

    public static Cell of(Char aChar, Digit digit){
        return new Cell(aChar, digit);
    }
}
