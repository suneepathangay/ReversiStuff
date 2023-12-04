package cs3500.reversi.view;



import cs3500.reversi.controller.Features;

/**
 * Interface for a panel that contains the board view for a game.
 */

public interface IPanel {


  /**
   * Method to add listen for events from the controller.
   *
   * @param features listeners from the Features interface to implement
   */
  void addFeatures(Features features);

  /**
   * Notifies the board something on the view bas changed.
   */
  void notifyGame();

  /**
   * Notifies the view that the player has changed.
   */
  void notifyPlayerChanged(String message);
}
