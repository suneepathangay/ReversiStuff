package cs3500.reversi.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cs3500.reversi.model.BasicReversi;
import cs3500.reversi.model.ModelAdapter;
import cs3500.reversi.model.ReadonlyReversiModel;
import cs3500.reversi.model.Tuple;
import cs3500.reversi.provider.model.BoardPosn;
import cs3500.reversi.provider.view.PlayerAction;

public class Converter {


  ReadonlyReversiModel model;





  public Converter(ReadonlyReversiModel model){
    this.model = model;
  }

  /**
   * Converts the provider coordiante to our coordinate
   * @param coordinate
   * @return
   */
  public Tuple<Integer,Integer> boardPosntoTuple(BoardPosn coordinate) {
    Map<BoardPosn, Tuple<Integer, Integer>> hexGridMap = mapBoardPosnBoardtoRowColBpard();
    return hexGridMap.get(coordinate);
  }



  private Map<BoardPosn, Tuple<Integer, Integer>> mapBoardPosnBoardtoRowColBpard() {
    int diameter = model.getNumRows();
    List<Tuple<Integer, Integer>> rowColList = generateRowColCoordinates(diameter);
    List<BoardPosn> cubeList = generateBoardPosnCoordinates(diameter);

    Map<BoardPosn, Tuple<Integer, Integer>> hexGridMap = new HashMap<>();
    for (int i = 0; i < rowColList.size(); i++) {
      hexGridMap.put(cubeList.get(i), rowColList.get(i));
    }

    return hexGridMap;
  }

  /**
   * Converts our coordinate to their coordinate syste
   * @param coordinate our coordinate
   * @return their coordinate
   */

  public BoardPosn tupleToBoardPosn(Tuple<Integer, Integer> coordinate) {
    Map<Tuple<Integer, Integer>, BoardPosn> hexGridMap = mapRowColBoardtoBoardPosnBoard();
    return hexGridMap.get(coordinate);
  }



  private Map<Tuple<Integer, Integer>, BoardPosn> mapRowColBoardtoBoardPosnBoard() {
    int diameter = model.getNumRows();
    List<Tuple<Integer, Integer>> rowColList = generateRowColCoordinates(diameter);
    List<BoardPosn> cubeList = generateBoardPosnCoordinates(diameter);

    Map<Tuple<Integer, Integer>, BoardPosn> hexGridMap = new HashMap<>();
    for (int i = 0; i < rowColList.size(); i++) {
      hexGridMap.put(rowColList.get(i), cubeList.get(i));
    }

    return hexGridMap;
  }

  private List<Tuple<Integer, Integer>> generateRowColCoordinates(int diameter) {
    List<Tuple<Integer, Integer>> rowColCoordinates = new ArrayList<>();
    int radius = (diameter - 1) / 2;

    for (int row = 0; row < diameter; row++) {
      int cols = diameter - Math.abs(row - radius);
      for (int col = 0; col < cols; col++) {
        int adjustedCol;
        if (row <= radius) {
          adjustedCol = col;
        } else {
          adjustedCol = col + (row - radius);
        }
        rowColCoordinates.add(new Tuple<>(row, adjustedCol));
      }
    }

    return rowColCoordinates;
  }

  private List<BoardPosn> generateBoardPosnCoordinates(int diameter) {
    List<BoardPosn> cubeCoordinates = new ArrayList<>();
    int radius = (diameter - 1) / 2;

    for (int r = -radius; r <= radius; r++) {
      for (int q = -radius; q <= radius; q++) {
        int newR = r + radius;
        int newQ = q + radius;
        int s = -r - q;
        if (Math.abs(s) <= radius) {
          int newS = - newQ - newR;
          cubeCoordinates.add(new BoardPosn(newQ, newR, newS));
        }
      }
    }

    return cubeCoordinates;
  }



}






