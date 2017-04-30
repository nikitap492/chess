package chess.view;

import chess.command.Clickable;
import chess.view.image.Image;


/**
 * Created by nikitap4.92@gmail.com
 * 21.04.17.
 */
interface View<T extends Image> extends Clickable {

    void setImage(T image);

}
