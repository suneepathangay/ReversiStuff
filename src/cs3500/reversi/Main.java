package cs3500.reversi;

import cs3500.reversi.controller.Controller;
import cs3500.reversi.controller.HumanPlayer;
import cs3500.reversi.controller.Player;
import cs3500.reversi.model.BasicReversi;
import cs3500.reversi.model.Colors;
import cs3500.reversi.view.Frame;


/**
 * Main class.
 */
public class Main {
  /**
   * Main method to run a game between two human players.
   *
   * @param args String[]
   */
  public static void main(String[] args) {

    BasicReversi model = new BasicReversi(6);
    Frame view = new Frame(model);
    Frame view2 = new Frame(model);

    Player player = new HumanPlayer(model, Colors.BLACK);
    Player player2 = new HumanPlayer(model, Colors.WHITE);
    Controller controller = new Controller(model, view, player);
    Controller controller2 = new Controller(model, view2, player2);
    model.startGame();
  }
}
