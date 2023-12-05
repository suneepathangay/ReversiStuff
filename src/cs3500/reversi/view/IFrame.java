package cs3500.reversi.view;

import cs3500.reversi.controller.Features;

/**
 * Interface to represent a frame that contains the board and panel for the view.
 */
public interface IFrame {
  /**
   * Method to display the fucking frame.
   */
  void display();

  /**
   * Method to add Features.
   *
   * @param features the Features interface.
   */
  void addFeatures(Features features);

  /**
   * Notifies the panel.
   */
  void notifyPanel();

  /**
   * Notify that the player has changed.
   */
  void notifyPlayerChanged(String message);

  /**
   * Sets the title of the frame.
   *
   * @param player the title of the frame
   */
  void setTitle(String player);

  void notifyGameOver(String winner);

}