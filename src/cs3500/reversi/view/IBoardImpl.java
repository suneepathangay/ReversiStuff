package cs3500.reversi.view;

import java.util.ArrayList;
import java.util.List;

import cs3500.reversi.controller.Converter;
import cs3500.reversi.model.IDisc;
import cs3500.reversi.model.ReadonlyReversiModel;
import cs3500.reversi.model.Tuple;
import cs3500.reversi.provider.model.BoardPosn;
import cs3500.reversi.provider.model.IBoard;


public class IBoardImpl implements IBoard {

  private ReadonlyReversiModel model;

  private Converter converter;

  public IBoardImpl(ReadonlyReversiModel model){
    this.model=model;
    this.converter=new Converter(model);

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
        adjacentCells.add(newCoord);
      } else {
        adjacentCells.add(null);
      }
    }

    return adjacentCells;
  }


  @Override
  public boolean inBounds(BoardPosn cell) {
    int radius = model.getNumRows()/2;
    return (Math.abs(cell.s) < model.getNumRows() + radius) && (Math.abs(cell.s)>=radius);
  }

  @Override
  public List<List<BoardPosn>> getBoard() {
    List<List<BoardPosn>> board=new ArrayList<>();

    List<List<IDisc>> delBoard=model.getCurrBoard();

    for(int i=0; i<delBoard.size(); i++){
      List<BoardPosn> convertedRow=new ArrayList<>();
      for(int j=0; j<delBoard.get(i).size(); j++){
        IDisc disc=delBoard.get(i).get(j);
        int rowCoor= disc.getRow();
        int colCoor=disc.getCol();
        Tuple<Integer,Integer> coor=new Tuple<>(rowCoor,colCoor);
        BoardPosn pos= converter.tupleToBoardPosn(coor);
        convertedRow.add(pos);
      }
      board.add(convertedRow);

    }
    return board;
  }

  @Override
  public List<List<BoardPosn>> getEdgesFrom(BoardPosn posn) {
    return null;
  }

  @Override
  public List<BoardPosn> getCorners() {
    List<Tuple<Integer, Integer>> cornerCoordinates = new ArrayList<>();
    List<BoardPosn> cornerCoordinatesBoardPosn = new ArrayList<>();
    int numRows = model.getNumRows();
    int size = model.getLength();
    cornerCoordinates.add(new Tuple<>(0, 0));
    cornerCoordinates.add(new Tuple<>(0, size - 1));
    cornerCoordinates.add(new Tuple<>((numRows - 1) / 2, 0));
    cornerCoordinates.add(new Tuple<>((numRows - 1) / 2, numRows - 1));
    cornerCoordinates.add(new Tuple<>(numRows - 1, size - 1));
    cornerCoordinates.add(new Tuple<>(numRows - 1, numRows - 1));


    for (Tuple<Integer, Integer> t:
         cornerCoordinates) {
      cornerCoordinatesBoardPosn.add(converter.tupleToBoardPosn(t));
    }

    return cornerCoordinatesBoardPosn;
  }
}
