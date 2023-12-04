package cs3500.reversi.provider.strategy;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import cs3500.reversi.provider.model.BoardPosn;
import cs3500.reversi.provider.model.ReadOnlyReversiModel;

/**
 * A Strategy: capture as many pieces in a turn as possible. If there are multiple such
 * moves, choose the uppermost-leftmost one.
 */
public class MaximizeCaptured implements FallibleReversiStrategy {

  /**
   * Chooses a move for the current player (the player whose turn it is in the given cs3500.provider.view.model)
   * in the given cs3500.provider.view.model that the most pieces possible.
   * if there are multiple such moves, choose the uppermost-leftmost one. More specifically,
   * uppermost takes precedence over leftmost. For example, if there are two equally good moves,
   * and move A is a row lower but to the left to move B, we choose move B because it is uppermost.
   *
   * @param model the cs3500.provider.view.model to choose a move in
   * @return the move chosen
   * <p>
   * design decision: it is the cs3500.provider.view.controller's responsibility to ensure that the player who this
   * cs3500.provider.view.strategy is checking for is the current player of the reversi cs3500.provider.view.model and game.
   * </p>
   */
  @Override
  public Optional<BoardPosn> chooseMove(ReadOnlyReversiModel model) {
    List<BoardPosn> validMoves = model.getPossibleMoves();
    if (validMoves.isEmpty()) {
      return Optional.empty();
    } else {
      BoardPosn bestCell = Objects.requireNonNull(validMoves.get(0));
      int maxCaptured = model.getPotentialCapturedCellsForMove(bestCell).size();
      for (int i = 1; i < validMoves.size(); i++) {
        BoardPosn possible = validMoves.get(i);
        int curCapturedNum = model.getPotentialCapturedCellsForMove(possible).size();
        if (curCapturedNum > maxCaptured) {
          bestCell = possible;
          maxCaptured = curCapturedNum;
        } else if (curCapturedNum == maxCaptured) {
          bestCell = getUpperMostLeftMost(bestCell, possible);
        }
      }
      return Optional.of(bestCell);
    }

  }

  /**
   * Returns the uppermost-leftmost topmost of the two given BoardPosns. Uppermost
   * takes precedence over leftmost. For example, if BoardPosn A is a row lower but to the left of
   * B, return B.
   *
   * @param a the first BoardPosn to compare
   * @param b the second BoardPosn to compare
   * @return the leftmost topmost of the two given BoardPosns.
   */
  private BoardPosn getUpperMostLeftMost(BoardPosn a, BoardPosn b) {
    if (a.r < b.r) {
      return a;
    }
    if (a.r > b.r) {
      return b;
    } else {
      if (a.q < b.q) {
        return a;
      } else {
        return b;
      }
    }
  }
}
