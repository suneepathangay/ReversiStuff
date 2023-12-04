package cs3500.reversi.provider.strategy;

import java.util.Optional;

import cs3500.reversi.provider.model.BoardPosn;
import cs3500.reversi.provider.model.ReadOnlyReversiModel;


/**
 * A cs3500.provider.view.strategy that chooses where to play next in a given Reversi cs3500.provider.view.model/state for the
 * current player of that cs3500.provider.view.model. It will always choose a BoardPosn to move to, and if
 * no such BoardPosn exists, it will throw an IllegalStateException.
 */
public class ReversiStrategy implements InfallibleReversiStrategy {
  private FallibleReversiStrategy attempt;


  /**
   * Constructs a ReversiStrategu with the given FallibleReversiStrategy. It will
   * choose the move that the given cs3500.provider.view.strategy chooses.
   * @param attempt the cs3500.provider.view.strategy to use to choose a move.
   */
  public ReversiStrategy(FallibleReversiStrategy attempt) {
    this.attempt = attempt;
  }

  /**
   * Chooses a move for the current player of the given cs3500.provider.view.model in the given cs3500.provider.view.model.
   *
   * @param model the cs3500.provider.view.model to choose a move in
   * @return the position to play to.
   */
  @Override
  public BoardPosn chooseMove(ReadOnlyReversiModel model) {
    Optional<BoardPosn> guess = this.attempt.chooseMove(model);
    if (guess.isPresent()) {
      return guess.get();
    }
    else {
      throw new IllegalStateException("Strategy failed to make a choice.");
    }
  }
}
