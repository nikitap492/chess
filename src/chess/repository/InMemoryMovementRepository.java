package chess.repository;

import chess.domain.movement.Movement;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Created by nikitap4.92@gmail.com
 * 28.04.17.
 */
public class InMemoryMovementRepository implements MovementRepository{

    private Deque<Movement> deque = new ArrayDeque<>();

    @Override
    public Movement last() {
        return deque.peekLast();
    }

    @Override
    public void add(Movement movement) {
        deque.offerLast(movement);
    }

    @Override
    public Movement undo() {
        return deque.removeLast();
    }

    @Override
    public void clear() {
        deque.clear();
    }
}
