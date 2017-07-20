package chess.controller;

import chess.controller.analyzer.MovementAnalyzer;

/**
 * @author Poshivalov Nikita
 * @since 20.04.2017.
 */
public interface CheckmateController {

    //todo subcriber
    void nextTurn();

    void setMovementAnalyze(MovementAnalyzer analyzer);
}
