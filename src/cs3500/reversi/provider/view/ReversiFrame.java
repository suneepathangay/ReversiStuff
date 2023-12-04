package cs3500.reversi.provider.view;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import cs3500.reversi.provider.model.BoardPosn;
import cs3500.reversi.provider.model.ReadOnlyReversiModel;


/**
 * Represents the frame in the Reversi cs3500.provider.view.
 * the frame is the outermost and contains all necessary panels.
 * Shows the current state of a ReversiGame.
 */
public class ReversiFrame extends JFrame implements ReversiFrameView {

  //  we have a final reversi panel - private final because our panel with the visualization
  // of the game board must be immutable/not able to be reassigned.
  // we have a final key component - private final so that unnecessary reassigning/mutations don't
  // occur
  private final ReversiPanel panel;
  private final KeyComponent keyComponent;

  /**
   * constructs the frame for a given cs3500.provider.view.model.
   * set the size, set title and sets the exit when closed
   * adds the key component and panel
   *
   * @param m the read only reversi cs3500.provider.view.model that our cs3500.provider.view shows,
   *          so that the cs3500.provider.view cannot mutate it
   */
  public ReversiFrame(ReadOnlyReversiModel m) {
    this.setSize(800, 800);
    this.setTitle("Reversi!");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.keyComponent = new KeyComponent();
    this.add(this.keyComponent);
    this.keyComponent.setVisible(true);

    this.panel = new ReversiPanel(m);

    this.add(panel);
    this.pack();


  }


  /**
   * Sets the hot key for the given feature name ("pass" or "playMove").
   *
   * @param keyStroke   the keystroke to set to trigger the given feature.
   * @param featureName the name of the feature to set the keystroke for.
   */
  @Override
  public void setHotKey(KeyStroke keyStroke, String featureName) {
    this.keyComponent.getInputMap().put(keyStroke, featureName);
  }

  /**
   * Adds a feature listener to this ReversiFrameView.
   *
   * @param features the feature listener to add
   */
  @Override
  public void addPlayerActionListener(PlayerAction features) {
    this.keyComponent.addFeatures(features);
  }

  /**
   * Shows this ReversiFrameView.
   */
  @Override
  public void display() {
    this.setVisible(true);
  }


  /**
   * Signal the cs3500.provider.view to draw itself.
   */
  @Override
  public void refresh() {
    this.panel.paintImmediately(this.panel.getBounds());

  }

  /**
   * Gets the user's current selected cell.
   *
   * @return the selected cell.
   * @throws IllegalStateException if no cell is selected.
   */
  @Override
  public BoardPosn getSelectedCell() throws IllegalStateException {
    return this.panel.getSelectedCell();
  }


  /**
   * Displays the game over message.
   *
   * @param message the message to display.
   */
  @Override
  public void displayGameOver(String message) {
    this.panel.setEndGameMsg(message);
    this.refresh();
  }

  /**
   * Clears the selected cell.
   */
  @Override
  public void clearSelectedCell() {
    this.panel.clearSelectedCell();
  }

  /**
   * Displays the given message.
   *
   * @param message the message to display.
   */
  @Override
  public void showMessageDialog(String message) {
    JOptionPane.showMessageDialog(this, message);
  }

  /**
   * Disables the player action listeners for this cs3500.provider.view, so that
   * the user cannot trigger a player action.
   */
  @Override
  public void disablePlayerActionListener() {
    this.keyComponent.notifyListeners = false;
  }

  /**
   * Enables the player action listeners for this cs3500.provider.view, so that
   * the user can trigger a player action.
   */
  @Override
  public void enablePlayerActionListener() {
    this.keyComponent.notifyListeners = true; // fields of fields is ok bc it's an inner class
  }

  /**
   * Returns the key component of this cs3500.provider.view. Package private so
   * that cs3500.provider.view related classes can access it, but no one else.
   * @return the key component of this cs3500.provider.view.
   */
  JComponent getKeyComponent() {
    return this.keyComponent;
  }

  // package private, so that cs3500.provider.view related classes can access it, but no one else.
  ReversiPanel getPanel() {
    return this.panel;
  }

  /**
   * Appends the given msg the base title of this cs3500.provider.view ("Reversi").
   *
   * @param msg the title to add.
   */
  @Override
  public void appendTitle(String msg) {
    this.setTitle("Reversi: " + msg);
  }

  /**
   * This component handles keyboard events for the Reversi game.
   */
  private class KeyComponent extends JPanel {
    private final List<PlayerAction> featureListeners = new ArrayList<>();
    private boolean notifyListeners;

    /**
     * constructs the key component by adding ability to pass and play move.
     * When either of those are triggered, will call the appropriate method in the feature
     * listeners.
     */
    private KeyComponent() {
      this.setLayout(new FlowLayout());
      this.notifyListeners = false;
      // Install action command -> Feature callback associations
      this.getActionMap().put("pass", new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
          if (!notifyListeners) {
            return;
          }
          for (PlayerAction f : featureListeners) {
            f.choosePass();
          }
          ReversiFrame.this.clearSelectedCell();
        }
      });
      this.getActionMap().put("playMove", new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
          if (!notifyListeners) {
            return;
          }
          for (PlayerAction f : featureListeners) {
            try {
              f.chooseMove(ReversiFrame.this.getSelectedCell());
            } catch (IllegalStateException ex) {
              ReversiFrame.this.showMessageDialog("Please select a cell to play a move to.");
            }
          }

        }
      });

    }


    /**
     * Includes a new feature listener in responding to key events.
     *
     * @param features the feature listener to add.
     */
    private void addFeatures(PlayerAction features) {
      this.featureListeners.add(features);
    }

  }

}
