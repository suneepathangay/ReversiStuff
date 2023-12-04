package cs3500.reversi.controller;

/**
 * Features interface describing actions that a listener to the IView can perform.
 */

public interface Features {

  /**
   * Method to place a piece on a tile.
   *
   * @param row the row to click
   * @param col the column to clikc
   */
  void clickTile(int row, int col);

  /**
   * Method to pass the players turn.
   */
  void passMove();

}


