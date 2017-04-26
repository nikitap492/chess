package chess.domain.cell;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Poshivalov Nikita
 * @since 14.04.2017.
 */
public enum Char {
    A(0), B(1), C(2), D(3), E(4), F(5), G(6), H(7);
    private static final List<Char> chars = new ArrayList<>();
    static {
        chars.addAll(Arrays.asList(Char.values()));
    }

    Char(int order) {
        this.order = order;
    }

    private final int order;

    public int getOrder() {
        return order;
    }

    public static Char get(int order){
        return chars.get(order);
    }
}

