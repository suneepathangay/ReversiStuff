package cs3500.reversi.provider.controller;

import cs3500.reversi.provider.model.BoardPosn;
import cs3500.reversi.provider.model.GameStatus;
import cs3500.reversi.provider.model.PlayerColor;

/**
 * Represents a cs3500.provider.view.controller for the game of Reversi. Responsible for communicating between the cs3500.provider.view.model,
 * cs3500.provider.view, and players. Will control the flow of the game, and signal to the cs3500.provider.view.model, cs3500.provider.view, and players.
 */
public interface ReversiController {

  /**
   * notifies that it is now the given player color's turn.
   * @param color the color of the player that is now playing.
   */
  void notifyPlayerTurn(PlayerColor color);

  void notifyGameStatus(GameStatus status);

  void moveMade(BoardPosn posn);

  void passed();

  void changeOnBoard();
}
