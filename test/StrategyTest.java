import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;

import cs3500.reversi.model.BasicReversi;
import cs3500.reversi.model.CaptureCornerStrategy;
import cs3500.reversi.model.CaptureStrategy;
import cs3500.reversi.model.Colors;
import cs3500.reversi.model.Tuple;
import cs3500.reversi.view.TextualReversiView;

/**
 * Test class for all the different types of strategies.
 */
public class StrategyTest {


  @Test
  public void testStratMultipleMoves() {
    CaptureStrategy cs = new CaptureStrategy();
    BasicReversi model = new BasicReversi(4);
    TextualReversiView view = new TextualReversiView(model);
    System.out.println(view);
    //placeing a black piec
    model.placeTile(2, 4, model.getTurn());
    //placing a white piece
    model.placeTile(5, 4, model.getTurn());

    //black turn
    Tuple<Integer, Integer> coordinate = cs.chooseMove(model, model.getTurn());
    Colors turn = model.getTurn();
    System.out.println(coordinate.getFirst() + " " + coordinate.getSecond());
    Assert.assertEquals(Optional.of(6).get(), coordinate.getFirst());
    Assert.assertEquals(Optional.of(4).get(), coordinate.getSecond());
    model.placeTile(coordinate.getFirst(), coordinate.getSecond(), turn);
    Assert.assertEquals(Optional.of(7).get(), model.getScore().getSecond());
    System.out.println(view);
  }

  @Test
  public void testUpperLeftMove() {
    CaptureStrategy cs = new CaptureStrategy();
    BasicReversi model = new BasicReversi(4);
    //placing black tile
    model.placeTile(2, 4, model.getTurn());
    //placing a white piece
    model.placeTile(5, 4, model.getTurn());
    //placing black tile
    model.placeTile(4, 2, model.getTurn());
    //placing a white tile
    model.placeTile(1, 4, model.getTurn());

    //now we have the option to place a tile at 6,5 and 4,5
    //we should place a tile at 4,5 so we can have be upper and more left
    Tuple<Integer, Integer> coordinate = cs.chooseMove(model, model.getTurn());
    Assert.assertEquals(Optional.of(4).get(), coordinate.getFirst());
    Assert.assertEquals(Optional.of(5).get(), coordinate.getSecond());
    Colors turn = model.getTurn();
    model.placeTile(coordinate.getFirst(), coordinate.getSecond(), turn);
    Assert.assertEquals(Optional.of(8).get(), model.getScore().getSecond());
  }

  @Test
  public void testTakeCornerCell() {
    CaptureCornerStrategy cs = new CaptureCornerStrategy();
    BasicReversi model = new BasicReversi(4);
    Colors turn = model.getTurn();
    model.placeTile(1, 2, turn);
    turn = model.getTurn();
    model.placeTile(4, 2, turn);
    turn = model.getTurn();
    model.placeTile(5, 2, turn);
    turn = model.getTurn();
    model.placeTile(4, 1, turn);
    turn = model.getTurn();
    model.placeTile(5, 4, turn);
    turn = model.getTurn();
    model.placeTile(0, 2, turn);
    turn = model.getTurn();
    model.placeTile(2, 1, turn);
    turn = model.getTurn();
    model.placeTile(4, 5, turn);
    turn = model.getTurn();
    //3 0 is a corner move
    Tuple<Integer, Integer> optimal = cs.chooseMove(model, turn);
    Assert.assertEquals(Optional.of(3), optimal.getFirst());
    Assert.assertEquals(Optional.of(0), optimal.getFirst());
  }

  @Test
  public void testAvoidCornerNext() {
    CaptureCornerStrategy cs = new CaptureCornerStrategy();
    BasicReversi model = new BasicReversi(4);
    Colors turn = model.getTurn();
    model.placeTile(1, 2, turn);
    turn = model.getTurn();
    model.placeTile(4, 2, turn);
    turn = model.getTurn();
    model.placeTile(5, 2, turn);
    turn = model.getTurn();
    model.placeTile(4, 1, turn);
    turn = model.getTurn();
    model.placeTile(5, 4, turn);
    turn = model.getTurn();
    model.placeTile(0, 2, turn);
    turn = model.getTurn();
    model.placeTile(2, 1, turn);
    turn = model.getTurn();
    model.placeTile(4, 5, turn);
    turn = model.getTurn();
    model.placeTile(3, 0, turn);
    turn = model.getTurn();
    model.placeTile(2, 0, turn);
    turn = model.getTurn();
    model.placeTile(4, 6, turn);
    turn = model.getTurn();
    model.placeTile(5, 6, turn);
    Assert.assertNotEquals(Optional.of(1), cs.chooseMove(model, model.getTurn()).getFirst());
    Assert.assertNotEquals(Optional.of(0), cs.chooseMove(model, model.getTurn()).getSecond());
  }

}
