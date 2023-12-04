package cs3500.reversi.provider.strategy;

import cs3500.reversi.provider.model.BoardPosn;
import cs3500.reversi.provider.model.ReadOnlyReversiModel;

/**
 * A Strategy for choosing where to play next. Should never fail to choose a position.
 */
public interface InfallibleReversiStrategy {

  /**
   * Chooses a move for the current player of the given cs3500.provider.view.model in the given cs3500.provider.view.model.
   * @param model the cs3500.provider.view.model to choose a move in
   * @return the position to play to.
   * @throws IllegalStateException if there are no possible moves for the current player of the
   *         cs3500.provider.view.model.
   */
  BoardPosn chooseMove(ReadOnlyReversiModel model) throws IllegalStateException;
}
