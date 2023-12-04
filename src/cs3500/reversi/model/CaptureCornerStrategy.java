package cs3500.reversi.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the strategy to get corners.
 */
public class CaptureCornerStrategy extends Strategy {
  /**
   * Gets the optimal move for the capture corner strategy.
   *
   * @param model type of Reversi
   * @param turn  which color is currently moving
   * @return the ideal coordinate
   */
  @Override
  public Tuple<Integer, Integer> chooseMove(ReadonlyReversiModel model, Colors turn) {
    List<Tuple<Integer, Integer>> corners = getCornerCoors(model);
    List<Tuple<Integer, Integer>> coordinates = model.getCoordinates();
    List<Tuple<Integer, Integer>> validCoordinates = new ArrayList<>();


    for (Tuple<Integer, Integer> coor : coordinates) {
      Colors tileColor = model.getTileAt(coor.getFirst(), coor.getSecond()).getColor();
      if (tileColor == Colors.GRAY) {
        for (Tuple<Integer, Integer> coordinate : coordinates) {
          if (coordinate.getFirst() == coor.getFirst() &&
                  coordinate.getSecond() == coor.getSecond()) {
            validCoordinates.add(coor);
          }
        }
      }
    }
    if (!validCoordinates.isEmpty()) {
      return getBestCoordinate(model, turn, validCoordinates);
    }
    return null;
  }


  private List<Tuple<Integer, Integer>> getCornerCoors(ReadonlyReversiModel model) {
    List<Tuple<Integer, Integer>> cornerCoordinates = new ArrayList<>();
    //num rows in model
    int numRows = model.getNumRows();
    int size = model.getLength();
    cornerCoordinates.add(new Tuple<>(0, 0));
    cornerCoordinates.add(new Tuple<>(0, size - 1));
    cornerCoordinates.add(new Tuple<>((numRows - 1) / 2, 0));
    cornerCoordinates.add(new Tuple<>((numRows - 1) / 2, numRows - 1));
    cornerCoordinates.add(new Tuple<>(numRows - 1, size - 1));
    cornerCoordinates.add(new Tuple<>(numRows - 1, numRows - 1));

    return cornerCoordinates;
  }
}
