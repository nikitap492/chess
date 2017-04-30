package chess.repository;

import chess.domain.movement.Movement;

import java.util.Optional;

/**
 * Created by nikitap4.92@gmail.com
 * 28.04.17.
 */
public interface MovementRepository {

    Movement last();

    void add(Movement movement);

    Optional<Movement> undo();

    void clear();

}
