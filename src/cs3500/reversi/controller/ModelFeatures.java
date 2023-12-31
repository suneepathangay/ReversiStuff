package cs3500.reversi.controller;

import cs3500.reversi.model.Message;

/**
 * Model features interface describing the methods that model can implement.
 */

public interface ModelFeatures {

  /**
   * Method to notify that the board has changed and request a refresh of teh board.
   */
  void notifyBoardChanged();

  /**
   * Notify the view that the player has changed.
   */
  void notifyPlayerChanged(Message player);

  /**
   * Notifies the listener that the game is over.
   * @param winner the winner.
   */
  void notifyGameOver(String winner);


  /**
   * Sets the controller to a player in the model.
   */
  void setPlayer(Message player);

  /**
   * Gets the player for the controller in the model.
   */
  Message getPlayer();

  /**
   * Method to set the player title.
   *
   * @param player the player
   */
  void setTitle(Message player);

  /**
   * Returns if the player is human
   * @return if the player is human
   */
  boolean isHuman();


}
