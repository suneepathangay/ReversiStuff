package cs3500.reversi.provider.strategy;

import java.util.Optional;

import cs3500.reversi.provider.model.BoardPosn;
import cs3500.reversi.provider.model.ReadOnlyReversiModel;

/**
 * A cs3500.provider.view.strategy that chooses where to play next for a Reversi cs3500.provider.view.model/state. Represents
 * a cs3500.provider.view.strategy that might not succeed at choosing a move.
 */
public interface FallibleReversiStrategy {

  /**
   * Chooses a move for the current player of the given cs3500.provider.view.model in the given cs3500.provider.view.model.
   * @param model the cs3500.provider.view.model to choose a move in
   * @return the move chosen. If there are no possible moves, return an empty optional, which
   *         means that the player must pass with this cs3500.provider.view.strategy / no such move that fits the
   *         cs3500.provider.view.strategy was found.
   * <p>
   * design decision: it is the cs3500.provider.view.controller's responsibility to ensure that the player who this
   * cs3500.provider.view.strategy is choosing for is the current player of the reversi cs3500.provider.view.model and game. does not take
   * in a player as the parameter because that information is already in the cs3500.provider.view.model AND should not
   * allow for scenarios where the given player is not the same as the current player of the game.
   * </p>
   */
  Optional<BoardPosn> chooseMove(ReadOnlyReversiModel model);
}
