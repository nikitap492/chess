package chess.domain.cell;

/**
 * @author Poshivalov Nikita
 * @since 14.04.2017.
 */
public enum Digit {
    ONE(0), TWO(1), THREE(2), FOUR(3), FIVE(4), SIX(5), SEVEN(6), EIGHT(7);
    private static final int step = 82;
    private static final int offset = 40;

    Digit(int order) {
        this.order = order;
        this.y = (7 - order) * step + offset;
    }

    private final int y;
    private final int order;

    public int getY() {
        return y;
    }
    public int getOrder() { return order;}
}

