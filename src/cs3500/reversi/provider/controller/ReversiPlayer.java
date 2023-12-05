package cs3500.reversi.provider.controller;

import cs3500.reversi.provider.model.PlayerColor;
import cs3500.reversi.provider.view.PlayerAction;

/**
 * Represents a player in the game Reversi.
 */
public interface ReversiPlayer {
  /**
   * this player attempts to choose a move, given the current player color of the game.
   * @param playerColor the current player color of the game.
   */
  public void chooseMove(PlayerColor playerColor);

  /**
   * Notifies that this player has chosen to pass.
   */
  public void choosePass();

  /**
   * Adds the given listener (player action) to be subscribed to this player's decisions.
   * @param listener the listener to be subscribed to this player actions.
   */
  public void addListener(PlayerAction listener);

  /**
   * Returns the color of this player.
   * @return the color of this player.
   */
  public PlayerColor getPlayerColor();

  /**
   * Disables notifications to the listeners of this player's actions.
   */
  public void disablePlayerActionListener();

  /**
   * Enables notifications to the listeners of this player's actions.
   */
  public void enablePlayerActionListener();

}