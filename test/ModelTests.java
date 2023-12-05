import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import cs3500.reversi.model.BasicReversi;
import cs3500.reversi.model.Colors;
import cs3500.reversi.model.Tuple;
import cs3500.reversi.view.TextualReversiView;

/**
 * Class for the ModelTests class.
 */
public class ModelTests {


  @Test
  public void testGetScore() {
    BasicReversi game = new BasicReversi(6);
    TextualReversiView view = new TextualReversiView(game);
    System.out.println(view);
    Tuple<Integer, Integer> score = game.getScore();
    Assert.assertEquals(Optional.of(3).get(), score.getFirst());
    Assert.assertEquals(Optional.of(3).get(), score.getSecond());
  }

  @Test
  public void getBoard() {
    BasicReversi game = new BasicReversi(6);
    game.getCurrBoard();
    TextualReversiView view = new TextualReversiView(game);
    System.out.println(view);
    Assert.assertEquals(Colors.WHITE,
            game.getTileAt(5, 4).getColor());
  }

  @Test
  public void testPlaceTileRowTooBig() {
    BasicReversi game = new BasicReversi(6);
    Colors currTurn = game.getTurn();
    Assert.assertThrows(IllegalStateException.class, () -> game.placeTile(14, 100, currTurn));
  }

  @Test
  public void testPlaceTileColumnTooBig() {
    BasicReversi game = new BasicReversi(6);
    Colors currTurn = game.getTurn();
    Assert.assertThrows(IllegalStateException.class, () -> game.placeTile(6, 100, currTurn));
  }

  @Test
  public void testPlaceTileColumn() {
    BasicReversi game = new BasicReversi(6);
    Colors turn = game.getTurn();
    game.placeTile(4, 6, turn);
    TextualReversiView view = new TextualReversiView(game);
    System.out.println(view);
    Assert.assertEquals(Colors.BLACK,
            game.getTileAt(4, 6).getColor());
  }

  @Test
  public void testInvalidMoveOnOccupiedSpace() {
    BasicReversi game = new BasicReversi(6);
    Colors currTurn = game.getTurn();
    Assert.assertThrows(IllegalStateException.class, () -> game.placeTile(2, 2, currTurn));
  }

  @Test
  public void testGetindex() {
    BasicReversi game = new BasicReversi(4);

    TextualReversiView view = new TextualReversiView(game);
    System.out.println(view);

    Assert.assertEquals(Optional.of(0),
            Optional.of(game.getIndexs(new Tuple<Integer, Integer>(0, 0)).getFirst()));
    Assert.assertEquals(Optional.of(0),
            Optional.of(game.getIndexs(new Tuple<Integer, Integer>(0, 0)).getSecond()));

    Assert.assertEquals(Optional.of(4),
            Optional.of(game.getIndexs(new Tuple<Integer, Integer>(4, 2)).getFirst()));
    Assert.assertEquals(Optional.of(1),
            Optional.of(game.getIndexs(new Tuple<Integer, Integer>(4, 2)).getSecond()));
  }

  @Test
  public void testFillTIles() {
    BasicReversi game = new BasicReversi(4);
    Colors currTurn = game.getTurn();
    game.placeTile(2, 4, currTurn);
    TextualReversiView view = new TextualReversiView(game);
    System.out.println(view);

    Assert.assertEquals(Colors.BLACK,
            game.getTileAt(2, 4).getColor());
  }


  @Test
  public void testSimulateGame() {
    BasicReversi game = new BasicReversi(4);

    //blacks turn
    Colors currTurn = game.getTurn();
    game.placeTile(2, 4, currTurn);
    TextualReversiView view = new TextualReversiView(game);
    System.out.println(view);
    Assert.assertEquals(Colors.BLACK,
            game.getTileAt(2, 4).getColor());

    //whites turn
    currTurn = game.getTurn();
    game.placeTile(5, 4, currTurn);
    view = new TextualReversiView(game);
    System.out.println(view);
    Assert.assertEquals(Colors.WHITE,
            game.getTileAt(5, 4).getColor());

    currTurn = game.getTurn();
    game.placeTile(4, 2, currTurn);
    view = new TextualReversiView(game);
    System.out.println(view);
    Assert.assertEquals(Colors.BLACK,
            game.getTileAt(4, 2).getColor());

    currTurn = game.getTurn();
    game.placeTile(4, 1, currTurn);
    view = new TextualReversiView(game);
    System.out.println(view);
    Assert.assertEquals(Colors.WHITE,
            game.getTileAt(4, 1).getColor());

    currTurn = game.getTurn();
    game.placeTile(5, 2, currTurn);
    view = new TextualReversiView(game);
    System.out.println(view);
    Assert.assertEquals(Colors.BLACK,
            game.getTileAt(5, 2).getColor());

    currTurn = game.getTurn();
    game.placeTile(1, 4, currTurn);
    view = new TextualReversiView(game);
    System.out.println(view);
    Assert.assertEquals(Colors.WHITE,
            game.getTileAt(1, 4).getColor());

    currTurn = game.getTurn();
    game.placeTile(4, 5, currTurn);
    view = new TextualReversiView(game);
    System.out.println(view);
    Assert.assertEquals(Colors.BLACK,
            game.getTileAt(4, 5).getColor());

    currTurn = game.getTurn();
    game.placeTile(4, 6, currTurn);
    view = new TextualReversiView(game);
    System.out.println(view);
    Assert.assertEquals(Colors.WHITE,
            game.getTileAt(4, 6).getColor());

    currTurn = game.getTurn();
    game.placeTile(6, 5, currTurn);
    view = new TextualReversiView(game);
    System.out.println(view);
    Assert.assertEquals(Colors.BLACK,
            game.getTileAt(6, 5).getColor());

    currTurn = game.getTurn();
    game.placeTile(2, 1, currTurn);
    view = new TextualReversiView(game);
    System.out.println(view);
    Assert.assertEquals(Colors.WHITE,
            game.getTileAt(2, 1).getColor());

    currTurn = game.getTurn();
    game.placeTile(1, 0, currTurn);
    view = new TextualReversiView(game);
    System.out.println(view);
    Assert.assertEquals(Colors.BLACK,
            game.getTileAt(1, 0).getColor());

    currTurn = game.getTurn();
    game.placeTile(6, 4, currTurn);
    view = new TextualReversiView(game);
    System.out.println(view);
    Assert.assertEquals(Colors.WHITE,
            game.getTileAt(6, 4).getColor());

    currTurn = game.getTurn();
    game.placeTile(2, 5, currTurn);
    view = new TextualReversiView(game);
    System.out.println(view);
    Assert.assertEquals(Colors.BLACK,
            game.getTileAt(2, 5).getColor());

    currTurn = game.getTurn();
    game.placeTile(6, 3, currTurn);
    view = new TextualReversiView(game);
    System.out.println(view);
    Assert.assertEquals(Colors.WHITE,
            game.getTileAt(6, 3).getColor());

    currTurn = game.getTurn();
    game.placeTile(5, 6, currTurn);
    view = new TextualReversiView(game);
    System.out.println(view);
    Assert.assertEquals(Colors.BLACK,
            game.getTileAt(5, 6).getColor());

    currTurn = game.getTurn();
    game.placeTile(3, 6, currTurn);
    view = new TextualReversiView(game);
    System.out.println(view);
    Assert.assertEquals(Colors.WHITE,
            game.getTileAt(3, 6).getColor());

    //for black no moves
    game.passMove();

    currTurn = game.getTurn();
    game.placeTile(6, 6, currTurn);
    view = new TextualReversiView(game);
    System.out.println(view);
    Assert.assertEquals(Colors.WHITE,
            game.getTileAt(6, 6).getColor());

    //for black no moves
    game.passMove();

    currTurn = game.getTurn();
    game.placeTile(2, 0, currTurn);
    view = new TextualReversiView(game);
    System.out.println(view);
    Assert.assertEquals(Colors.WHITE,
            game.getTileAt(2, 0).getColor());

    currTurn = game.getTurn();
    game.placeTile(1, 2, currTurn);
    view = new TextualReversiView(game);
    System.out.println(view);
    Assert.assertEquals(Colors.BLACK,
            game.getTileAt(1, 2).getColor());

    currTurn = game.getTurn();
    game.placeTile(0, 1, currTurn);
    view = new TextualReversiView(game);
    System.out.println(view);
    Assert.assertEquals(Colors.WHITE,
            game.getTileAt(0, 1).getColor());

    currTurn = game.getTurn();
    game.placeTile(0, 2, currTurn);
    view = new TextualReversiView(game);
    System.out.println(view);
    Assert.assertEquals(Colors.BLACK,
            game.getTileAt(0, 2).getColor());

    currTurn = game.getTurn();
    game.placeTile(0, 3, currTurn);
    view = new TextualReversiView(game);
    System.out.println(view);
    Assert.assertEquals(Colors.WHITE,
            game.getTileAt(0, 3).getColor());

    Assert.assertFalse(game.isGameOver());

    currTurn = game.getTurn();
    game.placeTile(3, 0, currTurn);
    view = new TextualReversiView(game);
    System.out.println(view);
    Assert.assertEquals(Colors.BLACK,
            game.getTileAt(3, 0).getColor());

    game.passMove();
    game.passMove();

    boolean gameOver = game.isGameOver();
    Assert.assertTrue(gameOver);

    int scoreBlack = game.getScore().getFirst();
    int scoreWhite = game.getScore().getSecond();

    Assert.assertEquals(22, scoreBlack);
    Assert.assertEquals(7, scoreWhite);

  }

  @Test
  public void testSimulateGameSmallerSize() {
    BasicReversi game = new BasicReversi(3);
    TextualReversiView view = new TextualReversiView(game);
    System.out.println(view);

    Colors currTurn = game.getTurn();
    game.placeTile(4, 3, currTurn);
    view = new TextualReversiView(game);
    System.out.println(view);

    currTurn = game.getTurn();
    game.placeTile(0, 1, currTurn);
    view = new TextualReversiView(game);
    System.out.println(view);

    currTurn = game.getTurn();
    game.placeTile(1, 0, currTurn);
    view = new TextualReversiView(game);
    System.out.println(view);

    currTurn = game.getTurn();
    game.placeTile(3, 4, currTurn);
    view = new TextualReversiView(game);
    System.out.println(view);

    currTurn = game.getTurn();
    game.placeTile(1, 3, currTurn);
    view = new TextualReversiView(game);
    System.out.println(view);

    currTurn = game.getTurn();
    game.placeTile(3, 1, currTurn);
    view = new TextualReversiView(game);
    System.out.println(view);

    game.passMove();
    game.passMove();

    boolean gameOver = game.isGameOver();
    Assert.assertTrue(gameOver);

    int scoreBlack = game.getScore().getFirst();
    int scoreWhite = game.getScore().getSecond();

    Assert.assertEquals(7, scoreBlack);
    Assert.assertEquals(5, scoreWhite);
  }

  @Test
  public void testGetTurn() {
    BasicReversi game = new BasicReversi(4);
    Assert.assertEquals(Colors.BLACK, game.getTurn());

    Colors currTurn = game.getTurn();
    game.placeTile(2, 4, currTurn);
    Assert.assertEquals(Colors.WHITE, game.getTurn());

    currTurn = game.getTurn();
    game.placeTile(5, 4, currTurn);
    Assert.assertEquals(Colors.BLACK, game.getTurn());
  }

  @Test
  public void testGetLength() {
    BasicReversi game = new BasicReversi(4);

    Assert.assertEquals(Optional.of(4), Optional.of(game.getLength()));
  }

  @Test
  public void testGetNumRows() {
    BasicReversi game = new BasicReversi(4);
    int numRows = (game.getLength() * 2) - 1;
    Assert.assertEquals(Optional.of(numRows),
            Optional.of(game.getNumRows()));

    BasicReversi game2 = new BasicReversi(6);
    numRows = (game2.getLength() * 2) - 1;
    Assert.assertEquals(Optional.of(numRows),
            Optional.of(game2.getNumRows()));
  }

  @Test
  public void testGetIndexs() {
    BasicReversi game = new BasicReversi(4);
    Tuple<Integer, Integer> indexs = game.getIndexs(new Tuple<Integer, Integer>(0, 0));
    Assert.assertEquals(Optional.of(0),
            Optional.of(indexs.getFirst()));
    Assert.assertEquals(Optional.of(0),
            Optional.of(indexs.getSecond()));

    indexs = game.getIndexs(new Tuple<Integer, Integer>(4, 2));
    Assert.assertEquals(Optional.of(4),
            Optional.of(indexs.getFirst()));
    Assert.assertEquals(Optional.of(1),
            Optional.of(indexs.getSecond()));
  }

  @Test
  public void testGetCoordinates() {
    BasicReversi game = new BasicReversi(3);
    List<Tuple<Integer, Integer>> coordinates = game.getCoordinates();
    List<Tuple<Integer, Integer>> expectedCoordinates = new ArrayList<>();
    expectedCoordinates.add(new Tuple<>(0, 0));
    expectedCoordinates.add(new Tuple<>(0, 1));
    expectedCoordinates.add(new Tuple<>(0, 2));
    expectedCoordinates.add(new Tuple<>(1, 0));
    expectedCoordinates.add(new Tuple<>(1, 1));
    expectedCoordinates.add(new Tuple<>(1, 2));
    expectedCoordinates.add(new Tuple<>(2, 1));
    expectedCoordinates.add(new Tuple<>(2, 2));
    expectedCoordinates.add(new Tuple<>(2, 3));
    for (int i = 0; i < coordinates.size(); i++) {
      int x1 = expectedCoordinates.get(i).getFirst();
      int y1 = expectedCoordinates.get(i).getSecond();

      int x = coordinates.get(i).getFirst();
      int y = coordinates.get(i).getSecond();
      Assert.assertEquals(x1, x);
      Assert.assertEquals(y1, y);
    }
  }

  @Test
  public void testSetTileColor() {
    BasicReversi game = new BasicReversi(3);
    game.setTileColor(0, 0, Colors.WHITE);
    Assert.assertEquals(Colors.WHITE, game.getTileAt(0, 0).getColor());

    game.setTileColor(1, 0, Colors.BLACK);
    Assert.assertEquals(Colors.BLACK, game.getTileAt(1, 0).getColor());
  }


}











