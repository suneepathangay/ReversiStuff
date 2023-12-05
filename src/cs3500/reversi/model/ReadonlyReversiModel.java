package cs3500.reversi.model;

import java.util.List;

/**
 * Readonly interface for Reversi game model. This interface provides methods
 * to query the game state without modifying it.
 */
public interface ReadonlyReversiModel {

  /**
   * Checks if the game is over.
   *
   * @return true if the game is over, false otherwise
   */
  boolean isGameOver();

  /**
   * Retrieves the current score of the game.
   *
   * @return a tuple representing the score for black and white players
   */
  Tuple<Integer, Integer> getScore();

  /**
   * Gets the current turn.
   *
   * @return the color of the player whose turn it is
   */
  Colors getTurn();

  /**
   * Provides a view of the game board.
   *
   * @return a list of lists representing the game board
   */
  List<List<IDisc>> getCurrBoard();

  /**
   * Retrieves the length of the board.
   *
   * @return the length of the board
   */
  Integer getLength();

  /**
   * Returns the number of rows in the board.
   *
   * @return the number of rows
   */
  Integer getNumRows();

  /**
   * Converts values in coordinate from coordinate system to array indexes.
   *
   * @param coordinates the coordinates to convert
   * @return the corresponding row and column indices
   */
  Tuple<Integer, Integer> getIndexs(Tuple<Integer, Integer> coordinates);

  /**
   * Retrieves the tile at a specified coordinate.
   *
   * @param rowCoor the row coordinate
   * @param colCoor the column coordinate
   * @return the disc at the specified location
   */
  IDisc getTileAt(int rowCoor, int colCoor);

  /**
   * Method to retrieve all the coordinates on the board.
   * @return a list of all the coordinates in the board
   */
  List<Tuple<Integer, Integer>> getCoordinates();

  /**
   * Determines if the propposed move is valid.
   * @return a boolean depending on if move is valid ro not
   */
  boolean isValidMove(Tuple<Integer,Integer> coor);

  /**
   * Method to start the game
   */
  void startGame();

}