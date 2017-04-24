package chess.controller.analyzer;

/**
 * Created by nikitap4.92@gmail.com
 * 24.04.17.
 */
final class Directions {

    static boolean up(OrderStruct struct){
        return  ++struct.digitOrder <= 7;
    }

    static boolean right(OrderStruct struct){
        return  ++struct.charOrder <= 7;
    }

    static boolean down(OrderStruct struct){
        return  --struct.digitOrder >= 0;
    }

    static boolean left(OrderStruct struct){
        return  --struct.charOrder >= 0;
    }

    static boolean leftUp(OrderStruct struct){
        return  ++struct.digitOrder <= 7 && --struct.charOrder >= 0;
    }

    static boolean rightUp(OrderStruct struct){
        return  ++struct.digitOrder <= 7 && ++struct.charOrder <= 7;
    }

    static boolean rightDown(OrderStruct struct){
        return  --struct.digitOrder >= 0  && ++struct.charOrder <= 7;
    }

    static boolean leftDown(OrderStruct struct){
        return  --struct.digitOrder >= 0  && --struct.charOrder >= 0;
    }

    static boolean northNorthWest(OrderStruct struct){
        struct.digitOrder += 2;
        struct.charOrder -= 1;
        return struct.digitOrder <= 7 && struct.charOrder >= 0;
    }

    static boolean northWestWest(OrderStruct struct){
        struct.digitOrder += 1;
        struct.charOrder -= 2;
        return struct.digitOrder <= 7 && struct.charOrder >= 0;
    }

    static boolean northEastEast(OrderStruct struct){
        struct.digitOrder += 1;
        struct.charOrder += 2;
        return struct.digitOrder <= 7 && struct.charOrder <= 7;
    }

    static boolean northNorthEast(OrderStruct struct){
        struct.digitOrder += 2;
        struct.charOrder += 1;
        return struct.digitOrder <= 7 && struct.charOrder <= 7;
    }

    static boolean southSouthWest(OrderStruct struct){
        struct.digitOrder -= 2;
        struct.charOrder -= 1;
        return struct.digitOrder >= 0 && struct.charOrder >= 0;
    }

    static boolean southWestWest(OrderStruct struct){
        struct.digitOrder -= 1;
        struct.charOrder -= 2;
        return struct.digitOrder >= 0 && struct.charOrder >= 0;
    }

    static boolean southEastEast(OrderStruct struct){
        struct.digitOrder -= 1;
        struct.charOrder += 2;
        return struct.digitOrder >= 0 && struct.charOrder <= 7;
    }

    static boolean southSouthEast(OrderStruct struct){
        struct.digitOrder -= 2;
        struct.charOrder += 1;
        return struct.digitOrder >= 0 && struct.charOrder <= 7;
    }


}
