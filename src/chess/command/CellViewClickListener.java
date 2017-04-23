package chess.command;

import chess.view.CellView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikitap4.92@gmail.com
 * 22.04.17.
 */
public class CellViewClickListener implements ClickListener<CellView> {
    private List<Subscriber<CellView>> subscribers = new ArrayList<>();

    @Override
    public void click(Click<CellView> click) {
        subscribers.forEach(subscriber -> subscriber.update(click));
    }

    @Override
    public void addSubscriber(Subscriber<CellView> subscriber) {
        subscribers.add(subscriber);
    }
}
