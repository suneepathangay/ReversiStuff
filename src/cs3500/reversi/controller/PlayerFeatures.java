package cs3500.reversi.controller;

import cs3500.reversi.model.Tuple;

/**
 * Features interface describing the actions that a listener to a Player can perform.
 */
public interface PlayerFeatures {
  /**
   * Makes a move for the player based on a strategy.
   * @param coordinate the best valid move returned by a strategy.
   */
  void makeMove(Tuple<Integer, Integer> coordinate);



}
