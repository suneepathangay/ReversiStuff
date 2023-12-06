package cs3500.reversi;

import cs3500.reversi.controller.AIPlayer;
import cs3500.reversi.controller.Controller;
import cs3500.reversi.controller.HumanPlayer;
import cs3500.reversi.controller.Player;
import cs3500.reversi.model.BasicReversi;
import cs3500.reversi.model.CaptureStrategy;
import cs3500.reversi.model.Colors;
import cs3500.reversi.model.Reversi;
import cs3500.reversi.model.Strategy;
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
;  public static void main(String[] args) {

    Reversi model = new BasicReversi(6);
    Frame view = new Frame(model);
    Frame view2 = new Frame(model);

    Player player = new HumanPlayer(model, Colors.BLACK);
    Player player2 = new HumanPlayer(model, Colors.WHITE);
    Strategy strat = new CaptureStrategy();
    Player ai1=new AIPlayer(model,strat,Colors.BLACK);
    Player ai2=new AIPlayer(model,strat,Colors.WHITE);


    Controller controller = new Controller(model, view, ai1);
    Controller controller2 = new Controller(model, view2, ai2);
    model.startGame();
  }
}
