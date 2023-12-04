package cs3500.reversi.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the strategy for gaining the most points.
 */
public class CaptureStrategy extends Strategy {
  /**
   * Chooses a move to optimally move to the top and leftmost coordinate.
   *
   * @param model type of Reversi
   * @param turn  which color is currently moving
   * @return the best coordinate
   */
  @Override
  public Tuple<Integer, Integer> chooseMove(ReadonlyReversiModel model, Colors turn) {
    List<Tuple<Integer, Integer>> coordinates = model.getCoordinates();
    List<Tuple<Integer, Integer>> validCoordinates = new ArrayList<>();

    for (Tuple<Integer, Integer> coor : coordinates) {
      if (model.getTileAt(coor.getFirst(), coor.getSecond()).getColor() == Colors.GRAY) {
        validCoordinates.add(coor);
      }
    }
    if (!validCoordinates.isEmpty()) {
      return getBestCoordinate(model, turn, validCoordinates);
    }
    //there are no remaining spots
    return null;
  }

}
