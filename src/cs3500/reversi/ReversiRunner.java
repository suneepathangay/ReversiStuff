package cs3500.reversi;


import cs3500.reversi.controller.AIPlayer;
import cs3500.reversi.controller.Controller;
import cs3500.reversi.controller.HumanPlayer;
import cs3500.reversi.controller.Player;
import cs3500.reversi.model.AvoidCornerStrategy;
import cs3500.reversi.model.BasicReversi;
import cs3500.reversi.model.CaptureCornerStrategy;
import cs3500.reversi.model.CaptureStrategy;
import cs3500.reversi.model.Colors;
import cs3500.reversi.model.ReadonlyReversiModel;
import cs3500.reversi.model.Reversi;
import cs3500.reversi.provider.strategy.MaximizeCaptured;
import cs3500.reversi.provider.strategy.ReversiStrategy;
import cs3500.reversi.view.Frame;

/**
 * Class to customize different types of players either Human or AI and with different strategies.
 */
public class ReversiRunner {

  static private Player createPlayer(String playerInput, ReadonlyReversiModel model, Colors color) {

    switch (playerInput) {


      case "Human":
        return new HumanPlayer(model, color);
      case "captureStrategy":
        return new AIPlayer(model, new CaptureStrategy(), color);
      case "captureCornerStrategy":
        return new AIPlayer(model, new CaptureCornerStrategy(), color);
      case "avoidCornerStrategy":
        return new AIPlayer(model, new AvoidCornerStrategy(), color);
      case " providerStrategy1":
        return new AIPlayer(model,new MaximizeCaptured(), color);
      default:
        throw new IllegalStateException("Unexpected value: " + playerInput);
    }
  }


  /**
   * Main method to start the game with customized players.
   *
   * @param args the input
   */
  public static void main(String[] args) {
    System.out.println("Enter in model size and type players(etc 6 human strategy1)");

    if (args.length != 3) {
      System.out.println("You did not enter in the number of correct commands. Try again");
      return;
    }
    int modelSize;
    String playerOneType;
    String playerTwoType;

    try {
      modelSize = Integer.parseInt(args[0]);
    } catch (NumberFormatException e) {
      System.out.println("please enter a number");
      return;
    }
    playerOneType = args[1];
    playerTwoType = args[2];
    Reversi model = new BasicReversi(modelSize);

    Frame view = new Frame(model);
    Frame view2 = new Frame(model);


    Player player1 = createPlayer(playerOneType, model, Colors.BLACK);
    Player player2 = createPlayer(playerTwoType, model, Colors.WHITE);

    Controller controller = new Controller(model, view, player1);
    Controller controller2 = new Controller(model, view2, player2);
    model.startGame();

  }
}
