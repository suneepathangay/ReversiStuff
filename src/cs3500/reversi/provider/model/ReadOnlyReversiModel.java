package cs3500.reversi.provider.model;

import java.util.List;

/**
 * A read only version of the ReversiModel. There are no mutator methods.
 * Represents the current state of the game (the board, the players, the captured pieces, etc.).
 */
public interface ReadOnlyReversiModel {
  /**
   * Returns the number of captured pieces of the given playerColor.
   *
   * @param playerColor whose number of captured peices we wish to return.
   * @throws IllegalArgumentException if the given playerColor is invalid.
   */
  int getNumCapturedHexagons(PlayerColor playerColor) throws IllegalArgumentException;

  /**
   * Returns all the captured hexagons on the board for the given playerColor.
   *
   * @param playerColor whose captured hexagons we wish to return.
   * @return all of the captured hexagons for this playerColor.
   * @throws IllegalArgumentException if the given playerColor is invalid.
   */
  List<BoardPosn> getCapturedHexagons(PlayerColor playerColor) throws IllegalArgumentException;

  /**
   * Returns the player whose turn it is.
   *
   * @return the play whose turn it is.
   * @throws IllegalStateException if the game is not started yet or the game is over
   */
  PlayerColor getPlayerTurn() throws IllegalStateException;

  /**
   * Returns all the possible hexagons that the current player is legally allowed to capture.
   *
   * @return all the possible moves the current player is allowed to capture
   */
  List<BoardPosn> getPossibleMoves();

  /**
   * Returns a copy of the empty board of the game.
   *
   * @return the board of the game
   */
  IBoard getBoard();

  /**
   * Returns the status of the game (either won, playing, stalemate). The game is only won or
   * stalemate (over) if there are two consecutive passes.
   *
   * @return the status of the game.
   */
  GameStatus getGameStatus();

  /**
   * Returns the winner of this game. The winner is the player with the most pieces/captured
   * cells on the board.
   *
   * @return the winning PlayerColor
   * @throws IllegalStateException if the game is not over or there is no winner, or the game
   *        is not started yet.
   */
  PlayerColor getWinner() throws IllegalStateException;

  /**
   * Returns the player whose token is at the given position.
   *
   * @param posn of one of the hexagons that make up the board
   * @return the player  whose token is at the given position.
   * @throws IllegalArgumentException if the posn is invalid (does not exist on the board) or
   *                                  if there is no player at the given position.
   */
  PlayerColor getPlayerAt(BoardPosn posn) throws IllegalArgumentException;

  /**
   * Checks if placing a game piece/ playing a move to the given position is legal.
   *
   * @param posn where the move will be made to.
   * @return true if the move is legal, false otherwise.
   */
  boolean isValidMove(BoardPosn posn);

  /**
   * Gets the potential captured BoardPosns/cells for a potential move to the given position.
   * DOES NOT actually apply the move or update this cs3500.provider.view.model.
   * @param posn the position to check
   * @return the captured cells for a move to the given position.
   */
  List<BoardPosn> getPotentialCapturedCellsForMove(BoardPosn posn);

  /**
   * Adds the given listener to be subscribed to this cs3500.provider.view.model's changes in state.
   * @param listener the listener to be subscribed to this cs3500.provider.view.model's changes in state.
   */
  void addModelListener(ModelStatus listener);

  /**
   * Returns the diameter of the board aka the number of hexagons on the board's longest row.
   * @return the diameter of the board.
   */
  int getBoardSize();

}
