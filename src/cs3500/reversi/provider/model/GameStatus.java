package cs3500.reversi.provider.model;

/**
 * an enumeration of game status.
 * Playing - game is in play
 * Won - the game is over, board is full, and we have a winner
 * Stalemate - the game is over because the board is full but white and black players
 *              have captured the same amount of cells
 * NotStarted - the game has not started yet
 */
public enum GameStatus {
  NOTSTARTED, PLAYING, WON, STALEMATE;
}
