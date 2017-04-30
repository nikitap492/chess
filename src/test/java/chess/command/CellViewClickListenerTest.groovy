package chess.command

import chess.view.CellView
import org.junit.Test
import org.mockito.Mockito

import static org.mockito.Mockito.*


/**
 * Created by nikitap4.92@gmail.com
 30.04.17.
 */
class CellViewClickListenerTest {
    @Test
    void addSubscriberAndClick() {
        def spy = spy(ClickSubscriber.class);
        def click = mock(Click.class);
        def listener = new CellViewClickListener();
        listener.addSubscriber(spy);
        listener.click(click);
        verify(spy, times(1)).update(click);
    }
}
