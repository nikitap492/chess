package chess.command;

import chess.view.PieceView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikitap4.92@gmail.com
 * 21.04.17.
 */
public class PieceViewClickListener implements ClickListener<PieceView> {

    private List<ClickSubscriber<PieceView>> subscribers = new ArrayList<>();

    @Override
    public void click(Click<PieceView> click) {
        subscribers.forEach(subscriber -> subscriber.update(click));
    }

    @Override
    public void addSubscriber(ClickSubscriber<PieceView> subscriber) {
        subscribers.add(subscriber);
    }


}
