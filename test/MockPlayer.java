import cs3500.reversi.controller.Player;
import cs3500.reversi.controller.PlayerFeatures;
import cs3500.reversi.model.Colors;


/**
 * Mock class for a mock player.
 */
public class MockPlayer implements Player {

  private final Colors color;

  /**
   * Constructor for the MockPlayer class.
   *
   * @param color The color of the player (BLACK or WHITE).
   */
  public MockPlayer(Colors color) {
    this.color = color;
  }


  @Override
  public Colors getColor() {
    return this.color;
  }

  @Override
  public void notifyOfMove() {
    //pass
  }

  @Override
  public void addListener(PlayerFeatures listener) {
    //pass
  }

}

