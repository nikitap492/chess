package chess.command;

/**
 * Created by nikitap4.92@gmail.com
 * 21.04.17.
 */
public interface Click<T extends Clickable> {

    T target();

}
