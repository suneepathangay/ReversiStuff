package cs3500.reversi.provider.strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import cs3500.reversi.provider.model.BoardPosn;
import cs3500.reversi.provider.model.IBoard;
import cs3500.reversi.provider.model.ReadOnlyReversiModel;

/**
 * This cs3500.provider.view.strategy avoids choosing to play at a cell that is next to a corner.
 */
public class AvoidCellsNextToCorners extends AbstractStrategy
        implements FallibleReversiStrategy {
  /**
   * Chooses a move for the current player of the given cs3500.provider.view.model in the given cs3500.provider.view.model.
   * Will avoid choosing to play at a cell that is next to a corner (where two
   * edges of the board connect). If there are multiple
   * such moves, it will choose the position that captures the most pieces. If there are multiple
   * such moves, it will choose the uppermost-leftmost one (uppermost matters more than leftmost).
   * @param model the cs3500.provider.view.model to choose a move in
   * @return the move chosen. If there are no possible moves, return an empty optional, which
   *         means that the player must pass with this cs3500.provider.view.strategy.
   * <p>
   * design decision: it is the cs3500.provider.view.controller's responsibility to ensure that the player who this
   * cs3500.provider.view.strategy is checking for is the current player of the reversi cs3500.provider.view.model and game.
   * </p>

   */
  @Override
  public Optional<BoardPosn> chooseMove(ReadOnlyReversiModel model) {
    List<BoardPosn> validMoves = model.getPossibleMoves();
    List<BoardPosn> validMovesNotNextToCorner = new ArrayList<BoardPosn>();
    for (BoardPosn posn : validMoves) {
      if (!this.isAdjToCorner(posn, model)) {
        validMovesNotNextToCorner.add(posn);
      }
    }
    return super.maximizeCaptured(model, validMovesNotNextToCorner);

  }

  /**
   * Returns true if the given position is not a corner and
   * adjacent to a corner in the given cs3500.provider.view.model.
   * @param posn the position to check
   * @param model the cs3500.provider.view.model to check in
   * @return true if the given position is adjacent to a corner in the given cs3500.provider.view.model.
   */
  private boolean isAdjToCorner(BoardPosn posn, ReadOnlyReversiModel model) {
    IBoard board = model.getBoard();
    List<BoardPosn> corners = board.getCorners();
    if (corners.contains(posn)) {
      return false;
    }
    else {
      for (BoardPosn corner : corners) {
        List<BoardPosn> adjToCorner = board.getAdjacent(posn);
        if (adjToCorner.contains(corner)) {
          return true;
        }
      }
    }
    return false;
  }
}


