import java.util.ArrayList;
import java.util.List;

import cs3500.reversi.controller.Features;
import cs3500.reversi.view.IFrame;

/**
 * This MockFrame class is used to test the controller.
 * It implements the IFrame interface.
 * It has a boolean field that is used to test the controller.
 * Class was implmented as a controller takes in an IFrame.
 */
public class MockFrame implements IFrame {
  public boolean wasUpdated = false;
  private List<String> playerChangeMessages = new ArrayList<>();
  private String title;

  @Override
  public void display() {
    //pass
  }

  @Override
  public void addFeatures(Features features) {
    //pass
  }

  @Override
  public void notifyPanel() {
    wasUpdated = true;
  }

  @Override
  public void notifyPlayerChanged(String message) {
    playerChangeMessages.add(message);
  }

  @Override
  public void setTitle(String player) {
    this.title = player;
  }

  @Override
  public void notifyGameOver(String winner) {

  }

  public List<String> getPlayerChangeMessages() {
    return playerChangeMessages;
  }

  public String getTitle() {
    return title;
  }
}

