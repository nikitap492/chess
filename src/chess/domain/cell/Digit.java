package chess.domain.cell;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Poshivalov Nikita
 * @since 14.04.2017.
 */
public enum Digit {
    ONE(0), TWO(1), THREE(2), FOUR(3), FIVE(4), SIX(5), SEVEN(6), EIGHT(7);
    private static final List<Digit> digits = new ArrayList<>();
    static {
        digits.addAll(Arrays.asList(Digit.values()));
    }

    Digit(int order) {
        this.order = order;
    }

    private final int order;

    public int getOrder() { return order;}

    public static Digit get(int order){
        return digits.get(order);
    }
}

