package cs3500.reversi.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cs3500.reversi.model.BasicReversi;
import cs3500.reversi.model.Tuple;
import cs3500.reversi.provider.model.BoardPosn;
import cs3500.reversi.provider.view.PlayerAction;

public class ReversiAdapter implements PlayerFeatures {

  PlayerAction providerPlayerAction;
  BasicReversi model;

  public ReversiAdapter(PlayerAction providerPlayerAction, BasicReversi model){
    this.providerPlayerAction = providerPlayerAction;
    this.model = model;
  }


  @Override
  public void makeMove(Tuple<Integer, Integer> coordinate) {
    BoardPosn boardPosn = tupleToBoardPosn(coordinate);
    providerPlayerAction.chooseMove(boardPosn);
  }

  private BoardPosn tupleToBoardPosn(Tuple<Integer, Integer> coordinate) {
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

  private static List<Tuple<Integer, Integer>> generateRowColCoordinates(int diameter) {
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

  private static List<BoardPosn> generateBoardPosnCoordinates(int diameter) {
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


