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
    return null;
  }

  @Override
  public List<BoardPosn> getAdjacent(BoardPosn cell) {
    return null;
  }

  @Override
  public boolean inBounds(BoardPosn cell) {
    return false;
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
    return null;
  }
}
