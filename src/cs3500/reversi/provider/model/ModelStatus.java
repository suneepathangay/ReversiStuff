package cs3500.reversi.provider.model;

import cs3500.reversi.provider.controller.ReversiController;
import cs3500.reversi.provider.controller.ReversiPlayer;


/**
 * Represents the listener for notifications for changes in the cs3500.provider.view.model's status / the state
 * of the game that
 * then communicates those events its own listeners.
 */
public interface ModelStatus {

  /**
   * Notifies that the current player of the game has switched.
   *
   * @param color the color of the player that is now playing.
   */
  void changeInTurn(PlayerColor color);

  /**
   * Adds the given listener (cs3500.provider.view.controller) to be subscribed to this cs3500.provider.view.model status notifications.
   *
   * @param listener the listener to be subscribed to this cs3500.provider.view.model status notifications.
   */
  void addControllerListener(ReversiController listener);

  /**
   * adds the given player as a listener to be subscribed to this cs3500.provider.view.model status notifications.
   *
   * @param listener the listener to be subscribed to this cs3500.provider.view.model status notifications.
   */
  void addPlayerListener(ReversiPlayer listener);

  /**
   * notifies a change in the game status of the cs3500.provider.view.model.
   *
   * @param status the new game status.
   */
  void changeInGameStatus(GameStatus status);

  /**
   * notifies a change in the board of the cs3500.provider.view.model.
   */
  void changeOnBoard();
}
