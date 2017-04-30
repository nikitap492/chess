package chess.command

import org.junit.Test

import static org.mockito.Mockito.mock
import static org.mockito.Mockito.spy
import static org.mockito.Mockito.times
import static org.mockito.Mockito.verify

/**
 * Created by nikitap4.92@gmail.com
 30.04.17.
 */
class PieceViewClickListenerTest {
    @Test
    void addSubscriberAndClick() {
        def spy = spy(ClickSubscriber.class);
        def click = mock(Click.class);
        def listener = new PieceViewClickListener();
        listener.addSubscriber(spy);
        listener.click(click);
        verify(spy, times(1)).update(click);
    }
}
