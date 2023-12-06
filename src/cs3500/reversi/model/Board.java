package cs3500.reversi.model;

import java.util.ArrayList;
import java.util.List;

import cs3500.reversi.provider.model.BoardPosn;
import cs3500.reversi.provider.model.IBoard;
import cs3500.reversi.provider.model.ReadOnlyReversiModel;

public class Board implements IBoard {
ReadonlyReversiModel model;
  public Board(ReadonlyReversiModel model){
    this.model = model;
  }

  @Override
  public BoardPosn getCenterCoord() {
    int radius = model.getNumRows()/2;
    return new BoardPosn(radius, radius, - (radius * 2));
  }

  @Override
  public List<BoardPosn> getAdjacent(BoardPosn cell) {
    BoardPosn currentCoord = cell;
    List<BoardPosn> adjacentCells = new ArrayList<>(6);

    int[][] directions = {
            {0, -1, 1},    // Top-left
            {1, -1, 0},    // Top-right
            {1, 0, -1},    // Right
            {0, 1, -1},    // Bottom-right
            {-1, 1, 0},    // Bottom-left
            {-1, 0, 1}     // Left
    };

    for (int[] direction : directions) {
      BoardPosn newCoord = new BoardPosn(
              currentCoord.q + direction[0],
              currentCoord.r + direction[1],
              currentCoord.s + direction[2]
      );

      if (inBounds(new BoardPosn(newCoord.q, newCoord.r, newCoord.s))) {
        BoardPosn adjacentCell = model.getCoordinates();
        adjacentCells.add(adjacentCell);
      } else {
        adjacentCells.add(null);
      }
    }

    return adjacentCells;
  }

  @Override
  public boolean inBounds(BoardPosn cell) {
    return false;
  }

  @Override
  public List<List<BoardPosn>> getBoard() {
    return null;
  }

  @Override
  public List<List<BoardPosn>> getEdgesFrom(BoardPosn posn) {
    return null;
  }

  @Override
  public List<BoardPosn> getCorners() {
    return null;
  }
}
