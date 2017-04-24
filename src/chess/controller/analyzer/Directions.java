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

}
