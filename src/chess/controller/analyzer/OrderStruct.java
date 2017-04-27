package chess.controller.analyzer;

import chess.domain.piece.Piece;

/**
 * Created by nikitap4.92@gmail.com
 * 24.04.17.
 */
class OrderStruct {
    private OrderStruct(int charOrder, int digitOrder) {
        this.charOrder = charOrder;
        this.digitOrder = digitOrder;
    }

    static OrderStruct of(Piece piece) {
        return new OrderStruct(piece.getCell().getChar().getOrder(),
                piece.getCell().getDigit().getOrder());
    }

    int charOrder;
    int digitOrder;
}
