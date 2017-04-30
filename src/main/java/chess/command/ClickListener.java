package chess.command;

/**
 * Created by nikitap4.92@gmail.com
 * 21.04.17.
 */
public interface ClickListener<T extends Clickable> {

    void click(Click<T> click);

    void addSubscriber(ClickSubscriber<T> subscriber);

}
