package chess.view.layout;

import chess.domain.cell.Cell;
import chess.domain.cell.Digit;
import chess.domain.piece.PieceColor;
import chess.domain.piece.PieceType;
import chess.view.Dimension;
import chess.view.Position;

import static chess.domain.cell.Digit.*;
import static chess.domain.piece.PieceType.*;

/**
 * Created by nikitap4.92@gmail.com
 * 22.04.17.
 */
public interface CellLayoutFactory {

    CellLayout create(Cell cell, Position position, Dimension dimension);

    default boolean hasPiece(Cell cell) {
        Digit digit = cell.getDigit();
        return digit == ONE || digit == TWO || digit == EIGHT || digit == SEVEN;
    }

    default PieceColor getColor(Cell cell){
        if(cell.getDigit() == ONE || cell.getDigit() == TWO){
            return PieceColor.WHITE;
        }else return PieceColor.BLACK;
    }

    default PieceType getType(Cell cell){
        Digit digit = cell.getDigit();
        if(digit == TWO || digit == SEVEN){
            return PAWN;
        }else {
            switch (cell.getChar()){
                case A:
                case H:
                    return ROOK;
                case B:
                case G:
                    return KNIGHT;
                case C:
                case F:
                    return BISHOP;
                case D:
                    if(digit == ONE)
                        return QUEEN;
                    else return KING;
                case E:
                    if(digit == ONE)
                        return KING;
                    else return QUEEN;
            }
        }
        return null;
    }

}
