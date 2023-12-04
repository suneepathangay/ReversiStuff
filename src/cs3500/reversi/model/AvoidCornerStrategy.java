package cs3500.reversi.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Strategy to avoid placing pieces in tiles next to the corner.
 */
public class AvoidCornerStrategy extends Strategy {
  /**
   * Gets the best coordiante for the strategy for avoiding the cells next to the corners.
   *
   * @param model type of Reversi
   * @param turn  which color is currently moving
   * @return the optimal coordinate for the Avoid Corner Strategy
   */
  @Override
  public Tuple<Integer, Integer> chooseMove(ReadonlyReversiModel model, Colors turn) {
    List<Tuple<Integer, Integer>> coordinates = model.getCoordinates();

    List<Tuple<Integer, Integer>> validNonNeighbor = new ArrayList<>();
    List<Tuple<Integer, Integer>> cornerCoor = getCornerCoor(model);
    List<Tuple<Integer, Integer>> neighborCoor = getNeighborCoor(model, cornerCoor);

    HashMap<Tuple<Integer, Integer>, Integer> coorToScore = new HashMap<>();

    for (Tuple<Integer, Integer> coor : coordinates) {
      //can only move to gray tiles
      Colors tileColor = model.getTileAt(coor.getFirst(), coor.getSecond()).getColor();
      if (tileColor == Colors.GRAY) {
        //if the coordinate is not next to a corner add it to a list of valid coordinatess
        if (!neighborCoor.contains(coor)) {
          validNonNeighbor.add(coor);
        }
      }


    }
    if (!validNonNeighbor.isEmpty()) {
      return getBestCoordinate(model, turn, validNonNeighbor);
    }
    //we have to move to a next corner tile there is no other option
    return null;
  }

  private List<Tuple<Integer, Integer>> getCornerCoor(ReadonlyReversiModel model) {
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

  private List<Tuple<Integer, Integer>> getNeighborCoor(
          ReadonlyReversiModel model, List<Tuple<Integer, Integer>> corners) {
    List<Tuple<Integer, Integer>> neighbor = new ArrayList<>();

    //creating a list of valdi directions
    List<Tuple<Integer, Integer>> directions = new ArrayList<>();
    directions.add(new Tuple<Integer, Integer>(0, 1));
    directions.add(new Tuple<Integer, Integer>(-1, 0));
    directions.add(new Tuple<Integer, Integer>(1, 0));
    directions.add(new Tuple<Integer, Integer>(1, 1));
    directions.add(new Tuple<Integer, Integer>(-1, -1));
    directions.add(new Tuple<Integer, Integer>(0, -1));

    //loop through the corners
    //loop through  directions and if it is within the boundaries
    //increment and then add it to the neighbor coordinates

    for (Tuple<Integer, Integer> coor : corners) {
      for (Tuple<Integer, Integer> direction : directions) {
        int row = coor.getFirst();
        int col = coor.getSecond();

        int numRows = model.getNumRows();

        int rowInc = direction.getFirst();
        int colInc = direction.getSecond();

        row += rowInc;
        col += colInc;

        if (row >= 0 && row < numRows) {
          if (col >= 0 && col < numRows) {
            Tuple<Integer, Integer> neighborCoor = new Tuple<>(row, col);
            neighbor.add(neighborCoor);
          }
        }
      }
    }
    return neighbor;
  }
}
