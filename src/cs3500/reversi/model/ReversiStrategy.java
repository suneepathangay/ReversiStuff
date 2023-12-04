package cs3500.reversi.model;

import java.util.List;

/**
 * Interface for implementing strategies to win the game.
 */
public interface ReversiStrategy {
  /**
   * Method to choose move.
   *
   * @param model type of Reversi
   * @param turn  which color is currently moving
   * @return the best coordinate for the move
   */
  Tuple<Integer, Integer> chooseMove(ReadonlyReversiModel model, Colors turn);

  /**
   * Returns a deep copy of the current model.
   *
   * @param model a Reversi Model
   * @return a deep copy version of the model
   */
  Reversi getReversi(ReadonlyReversiModel model);

  /**
   * Retrieves the coordinate that captures the most pieces and is also the most optimal.
   *
   * @param model       Reversi model
   * @param turn        color representing current turn
   * @param coordinates the list of coordinates to choose from
   * @return the most optimal coordinate.
   */
  Tuple<Integer, Integer> getBestCoordinate(ReadonlyReversiModel model,
                                            Colors turn, List<Tuple<Integer, Integer>> coordinates);
}
