package cs3500.reversi.provider.view;

import cs3500.reversi.provider.controller.ReversiController;
import cs3500.reversi.provider.model.BoardPosn;

/**
 * Represents a listener for the high level features/events of the players and the cs3500.provider.view. Also
 * notifies its own listeners about those events that occur.
 */
public interface PlayerAction {

  /**
   * Indicates that the user has requested to play/choose a move.
   * @param move the move that the user has requested to play to.
   */
  void chooseMove(BoardPosn move);

  /**
   * Indicates that the user has requested to pass.
   */
  void choosePass();

  /**
   * Adds the given listener (cs3500.provider.view.controller) to be subscribed to this PlayerAction's notifications.
   * @param listener the listener to be subscribed to this PlayerAction's notifications.
   */
  void addListener(ReversiController listener);
}
