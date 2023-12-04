package cs3500.reversi.provider.view;



import javax.swing.KeyStroke;
import cs3500.reversi.provider.model.BoardPosn;


/**
 * Represents the frame in the Reversi cs3500.provider.view. The frame is the outermost window that
 * all the panels are contained in. Shows the current state of a ReversiGame.
 */
public interface ReversiFrameView {

  /**
   * Adds a feature listener to this ReversiFrameView.
   * @param features the feature listener to add
   */
  void addPlayerActionListener(PlayerAction features);

  /**
   * Displays this ReversiFrameView.
   */
  void display();


  /**
   * Signal the cs3500.provider.view to draw itself.
   */
  void refresh();

  /**
   * Sets the hot key for the given feature name ("pass" or "playMove").
   * @param keyStroke the keystroke to set to trigger the given feature.
   * @param featureName the name of the feature to set the keystroke for.
   */
  void setHotKey(KeyStroke keyStroke, String featureName);

  /**
   * Gets the current selected cell.
   * @return the selected cell.
   * @throws IllegalStateException if no cell is selected.
   */
  BoardPosn getSelectedCell() throws IllegalStateException;


  /**
   * Displays the game over message.
   */
  void displayGameOver(String message);

  /**
   * Clears the selected cell.
   */
  void clearSelectedCell();

  /**
   * Displays the given message.
   * @param message the message to display.
   */
  void showMessageDialog(String message);

  /**
   * Disables the player action listeners for this cs3500.provider.view, so that
   * the user cannot trigger a player action.
   */
  void disablePlayerActionListener();

  /**
   * Enables the player action listeners for this cs3500.provider.view, so that
   * the user can trigger a player action.
   */
  void enablePlayerActionListener();

  /**
   * Appends the given msg the base title of this cs3500.provider.view ("Reversi").
   * @param msg the title to add.
   */
  void appendTitle(String msg);


}
