package cs3500.reversi.provider.strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import cs3500.reversi.provider.model.BoardPosn;
import cs3500.reversi.provider.model.IBoard;
import cs3500.reversi.provider.model.ReadOnlyReversiModel;

/**
 * This cs3500.provider.view.strategy chooses to play at the best (highest scoring) corner if possible.
 */
public class GoForCorners extends AbstractStrategy implements FallibleReversiStrategy {
  /**
   * Chooses a move for the current player of the given cs3500.provider.view.model in the given cs3500.provider.view.model. Will
   * choose a corner. If there are multiple corners available to play a move to, will
   * choose the corner that captures the most pieces. If there are multiple such moves, it
   * will choose the uppermost-leftmost one (uppermost takes precedence over leftmost)
   *
   * @param model the cs3500.provider.view.model to choose a move in
   * @return the move chosen. If there are no possible moves that fit the cs3500.provider.view.strategy,
   *         return an empty optional, which means
   *         that the player must pass with this cs3500.provider.view.strategy / no such move that fits the cs3500.provider.view.strategy
   *         was found.
   * <p>
   * design decision: it is the cs3500.provider.view.controller's responsibility to ensure that the player who this
   * cs3500.provider.view.strategy is checking for is the current player of the reversi cs3500.provider.view.model and game. does not take
   * in a player as the parameter because that information is already in the cs3500.provider.view.model AND should not
   * allow for scenarios where the given player is not the same as the current player of the game.
   * </p>
   */
  @Override
  public Optional<BoardPosn> chooseMove(ReadOnlyReversiModel model) {
    IBoard board = model.getBoard();
    List<BoardPosn> viableCorners = new ArrayList<>();
    List<BoardPosn> corners = board.getCorners();
    for (BoardPosn corner : corners) {
      if (model.getPossibleMoves().contains(corner)) {
        viableCorners.add(corner);
      }
    }
    return super.maximizeCaptured(model, viableCorners);
  }
}
