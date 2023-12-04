package cs3500.reversi.provider.model;

/**
 * Represents the current state of a Reversi Game. The primary rules keeper
 * of the game. The cs3500.provider.view.model is responsible for keeping track of the current state of the game
 * and determine the validity of moves and update the state of the game to match it.
 */
public interface ReversiModel extends ReadOnlyReversiModel {

  /**
   * plays a move by placing the current player's game piece at the
   * specified coordinates of the game board if allowable by the rules of the game.
   * captures all opposing player's discs sandwiched between this current players discs.
   * @param posn where on the game board to place the current player's piece.
   * @throws IllegalStateException if the game is over or not started yet.
   * @throws IllegalArgumentException if the posn is invalid
   * @throws IllegalStateException
   *       if the move is logically impossible or the cell is already occupied
   *
   */
  void playMove(BoardPosn posn) throws IllegalStateException, IllegalArgumentException;

  /**
   * allows the current player to skip their turn.
   * @throws IllegalStateException if the game is over or not started yet.
   */
  void pass() throws IllegalStateException;

  /**
   * Sets the first playercolor to be Black.
   * @throws IllegalStateException if the game has already started.
   */
  void startGame();


}
