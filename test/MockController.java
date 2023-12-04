import java.io.IOException;

import cs3500.reversi.controller.Features;
import cs3500.reversi.controller.ModelFeatures;
import cs3500.reversi.controller.Player;

import cs3500.reversi.model.Message;
import cs3500.reversi.model.Reversi;


/**
 * This MockController class is used to test the controller.
 * It implements the Features and ModelFeatures interfaces.
 * It has an appendable field that is used to test the controller.
 * This class implements those classes as our controller does.
 */
public class MockController implements Features, ModelFeatures {

  private final Appendable appendable;

  public MockController(Appendable appendable, Reversi model, MockFrame view, Player player) {
    this.appendable = appendable;
    // If you need to interact with the model
  }

  private void append(String s) {
    try {
      appendable.append(s + "\n");
    } catch (IOException e) {
      throw new RuntimeException("Failed to append log message", e);
    }
  }

  public Appendable getAppendable() {
    return this.appendable;
  }

  @Override
  public void clickTile(int row, int col) {
    append("clickTile called with row: " + row + ", col: " + col + "\n");

  }

  @Override
  public void passMove() {
    append("passMove called\n");

  }

  @Override
  public void notifyBoardChanged() {
    append("notifyBoardChanged called\n");
  }

  @Override
  public void notifyPlayerChanged(Message player) {
    append("notifyPlayerChanged called\n");

  }

  @Override
  public void setPlayer(Message player) {
    append("setPlayer called\n");
  }

  @Override
  public Message getPlayer() {
    append("getPlayer called\n");
    return null;
  }

  @Override
  public void setTitle(Message player) {
    append("setTitle called\n");
  }

}