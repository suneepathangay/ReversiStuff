package cs3500.reversi.provider.strategy;

import java.util.Optional;

import cs3500.reversi.provider.model.BoardPosn;
import cs3500.reversi.provider.model.ReadOnlyReversiModel;

/**
 * This cs3500.provider.view.strategy chains multiple strategies, and if the first does not produce a move,
 * it will try the next and so on.
 */
public class StackingStrategy implements FallibleReversiStrategy {

  // the strategies to try in order from left to right.
  private final FallibleReversiStrategy[] strategies;

  public StackingStrategy(FallibleReversiStrategy... strategies) {
    this.strategies = strategies;
  }

  /**
   * Chooses a move for the current player of the given cs3500.provider.view.model in the given cs3500.provider.view.model.
   * Attempts the first cs3500.provider.view.strategy, if it fails, then the second, and so on.
   * @param model the cs3500.provider.view.model to choose a move in
   * @return the move chosen. If there are no possible moves, return an empty optional, which
   *         means that the player
   *         must pass with this cs3500.provider.view.strategy / no such move that fits the cs3500.provider.view.strategy was found.
   *
   * <p>
   * design decision: it is the cs3500.provider.view.controller's responsibility to ensure that the player who this
   * cs3500.provider.view.strategy is choosing for is the current player of the reversi cs3500.provider.view.model and game. does not take
   * in a player as the parameter because that information is already in the cs3500.provider.view.model AND should not
   * allow for scenarios where the given player is not the same as the current player of the game.
   * </p>
   */
  @Override
  public Optional<BoardPosn> chooseMove(ReadOnlyReversiModel model) {
    for (FallibleReversiStrategy strategy: this.strategies) {
      Optional<BoardPosn> move = strategy.chooseMove(model);
      if (move.isPresent()) {
        return move;
      }
    }
    return Optional.empty();
  }
}
