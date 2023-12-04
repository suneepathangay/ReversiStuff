package cs3500.reversi.controller;

import cs3500.reversi.model.Colors;

/**
 * Interface describing the moves that a player can make.
 */

public interface Player {



  /**
   * Gets the color of the player.
   *
   * @return the color of the player
   */
  Colors getColor();

  /**
   * Method for the player to notify its listener that it has performed an action.
   */

  void notifyOfMove();

  /**
   * Adds a listener for the player to interact with the model.
   */

  void addListener(PlayerFeatures listener);



}
