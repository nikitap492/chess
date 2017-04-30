package chess.repository;

import chess.domain.movement.Movement;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Optional;

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
    public Optional<Movement> undo() {
        if (!deque.isEmpty()) return Optional.of(deque.removeLast());
        else return Optional.empty();
    }

    @Override
    public void clear() {
        deque.clear();
    }
}
