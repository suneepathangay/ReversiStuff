package cs3500.reversi.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Abstract class for the different strategies.
 */
public abstract class Strategy implements ReversiStrategy {

  /**
   * Gets a deep copy of the model at an instance.
   *
   * @param model Reversi Model passed in
   * @return a new deep copy of the model
   */
  @Override
  public Reversi getReversi(ReadonlyReversiModel model) {
    List<Tuple<Integer, Integer>> coordiantes = model.getCoordinates();
    Map<Tuple<Integer, Integer>, Colors> map = new HashMap<>();
    int size = model.getLength();
    Reversi modelCopy = new BasicReversi(size);

    //put the coordinates along with a color
    for (Tuple<Integer, Integer> coor : coordiantes) {
      Colors tileColor = model.getTileAt(coor.getFirst(), coor.getSecond()).getColor();
      map.put(coor, tileColor);
    }
    //looping through the coordinates and setting the color
    for (Tuple<Integer, Integer> coor : map.keySet()) {
      int row = coor.getFirst();
      int col = coor.getSecond();
      Colors color = map.get(coor);
      modelCopy.setTileColor(row, col, color);
    }
    return modelCopy;
  }

  /**
   * Gets the best coordinate based off of current board and strategy.
   *
   * @param model       Reversi model
   * @param turn        Color turn
   * @param coordinates valid coordiantes to check
   * @return the best coordinate
   */
  @Override
  public Tuple<Integer, Integer> getBestCoordinate(
          ReadonlyReversiModel model, Colors turn, List<Tuple<Integer, Integer>> coordinates) {

    Map<Tuple<Integer, Integer>, Integer> coordinateScoreMap = new HashMap<>();

    int maxScore = Integer.MIN_VALUE;
    //iterate through the coordiantes on the board
    for (Tuple<Integer, Integer> coordinate : coordinates) {
      int row = coordinate.getFirst();
      int col = coordinate.getSecond();

      Reversi modelCopy = getReversi(model);
      try {
        modelCopy.placeTile(row, col, turn);
        Tuple<Integer, Integer> scores = modelCopy.getScore();
        if (turn == Colors.WHITE) {
          Tuple<Integer, Integer> coor = new Tuple<Integer, Integer>(row, col);
          coordinateScoreMap.put(coor, scores.getFirst());
          if (scores.getFirst() > maxScore) {
            maxScore = scores.getFirst();
          }
        } else {
          Tuple<Integer, Integer> coor = new Tuple<Integer, Integer>(row, col);
          coordinateScoreMap.put(coor, scores.getSecond());
          if (scores.getSecond() > maxScore) {
            maxScore = scores.getFirst();
          }
        }
      } catch (IllegalStateException e) {
        modelCopy.passMove();
      }
    }
    //setting the max score
    for (Tuple<Integer, Integer> coor : coordinateScoreMap.keySet()) {
      int currScore = coordinateScoreMap.get(coor);
      if (currScore > maxScore) {
        maxScore = currScore;
      }
    }
    //got the maximum coording
    List<Tuple<Integer, Integer>> maxCoordinates = new ArrayList<>();
    for (Tuple<Integer, Integer> coor : coordinateScoreMap.keySet()) {
      int currScore = coordinateScoreMap.get(coor);
      if (currScore == maxScore) {
        maxCoordinates.add(coor);
      }
    }
    //now check if there are more than 2 options:
    if (maxCoordinates.size() == 1) {
      return maxCoordinates.get(0);
    }
    if (maxCoordinates.size() > 1) {
      //sort the coordinates first by the row and then by the column
      Comparator<Tuple<Integer, Integer>> customComparator =
              Comparator
                      .<Tuple<Integer, Integer>, Integer>comparing(Tuple::getFirst,
                              Comparator.reverseOrder())
                      .thenComparing(Tuple::getSecond, Comparator.reverseOrder());
      Collections.sort(maxCoordinates, customComparator);
      return maxCoordinates.get(0);
    }
    return null;
  }
}
