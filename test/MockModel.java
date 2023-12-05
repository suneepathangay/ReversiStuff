import java.io.IOException;
import java.util.List;

import cs3500.reversi.controller.ModelFeatures;
import cs3500.reversi.controller.Player;
import cs3500.reversi.model.Colors;
import cs3500.reversi.model.IDisc;
import cs3500.reversi.model.Reversi;
import cs3500.reversi.model.Tuple;

/**
 * This MockModel class is used to test the model.
 * It implements the Reversi interface.
 * Our BasicReversi class implements the Reversi interface.
 * Our BasicReversi class is the model for our game.
 * This class was made in order to test the general functionality of our model.
 * As well as to write the transcript for strategy 1.
 */
public class MockModel implements Reversi {
  private final Appendable appendable;
  private final Reversi template;

  /**
   * Constructor for the MockModel class.
   *
   * @param app   Appendable In order to keep track of the log.
   * @param model Reversi The model that we are testing.
   */
  public MockModel(Appendable app, Reversi model) {
    this.appendable = app;
    this.template = model;
  }

  private void append(String s) {
    try {
      appendable.append(s + "\n");
    } catch (IOException e) {
      throw new RuntimeException("Failed to append log message", e);
    }
  }

  @Override
  public void startGame() {
    append("startGame called\n");
    template.startGame();
  }

  @Override
  public void addPlayerListeners(Player player) {
    //pass
  }

  public Appendable getAppendable() {
    return this.appendable;
  }

  @Override
  public boolean isGameOver() {
    append("isGameOver called\n");
    return template.isGameOver();
  }

  @Override
  public Tuple<Integer, Integer> getScore() {
    append("getScore called\n");
    return template.getScore();
  }

  @Override
  public Colors getTurn() {
    append("getTurn called\n" + template.getTurn() + "\n");
    return template.getTurn();
  }


  @Override
  public List<List<IDisc>> getCurrBoard() {
    append("getBoard called\n");
    return template.getCurrBoard();
  }

  @Override
  public Integer getLength() {
    append("getLength called\n" + template.getLength() + "\n");
    return template.getLength();
  }

  @Override
  public Integer getNumRows() {
    return null;
  }

  @Override
  public Tuple<Integer, Integer> getIndexs(Tuple<Integer, Integer> coordinates) {
    append("getIndexs called\n");
    return template.getIndexs(coordinates);
  }

  @Override
  public IDisc getTileAt(int rowCoor, int colCoor) {
    append("getTileAt called\n" + rowCoor + " " + colCoor + "\n");
    return template.getTileAt(rowCoor, colCoor);
  }

  @Override
  public List<Tuple<Integer, Integer>> getCoordinates() {
    append("getCoordinates called\n");
    return template.getCoordinates();
  }


  @Override
  public void placeTile(int row, int column, Colors currTurn) {
    append("placeTile called\n");
    template.placeTile(row, column, currTurn);
    append("Placed tile at row: " + row + " col: " + column + "\n");
  }

  @Override
  public void passMove() {
    append("passMove called\n");
    template.passMove();
    append("Turn is now: " + template.getTurn() + "\n");
  }

  @Override
  public void setTileColor(int row, int column, Colors color) {
    append("setTileColor called\n");
    template.setTileColor(row, column, color);
    append("Set tile at row: " + row + " col: " + column + " to color: " + color + "\n");
  }

  @Override
  public void addListeners(ModelFeatures features) {
    append("addListeners called\n");
    template.addListeners(features);
  }
}