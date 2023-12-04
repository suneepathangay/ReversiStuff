package cs3500.reversi.model;

import cs3500.reversi.controller.ModelFeatures;
import cs3500.reversi.controller.Player;

/**
 * Interface for the Reversi game that includes methods to alter the game state.
 */
public interface Reversi extends ReadonlyReversiModel {


  /**
   * Places a tile on the board at the specified location. This method should handle changing
   * the player's turn and flipping the opponent's tiles according to the rules.
   *
   * @param row    the row index where the tile is to be placed
   * @param column the column index where the tile is to be placed
   */
  void placeTile(int row, int column, Colors currTurn);

  /**
   * Passes the current player's turn without making a move. This is typically allowed when the
   * player has no valid moves available.
   */
  void passMove();

  /**
   * Sets the tile at the specified location to the specified color. This method should not
   * handle changing the player's turn or flipping the opponent's tiles.
   *
   * @param row    the row index where the tile is to be placed
   * @param column the column index where the tile is to be placed
   * @param color  the color of the tile to place
   */
  void setTileColor(int row, int column, Colors color);

  /**
   * Adds a modelfeatures instance as a listener.
   * @param features a listener for the model
   */
  void addListeners(ModelFeatures features);

  /**
   * Method to start the game.
   */
  void startGame();

  /**
   * Method to add a player to the model.
   * @param player a Player implementation that acts as a listener
   */

  void addPlayerListeners(Player player);

}
