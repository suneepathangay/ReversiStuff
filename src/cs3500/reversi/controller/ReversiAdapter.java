package cs3500.reversi.controller;

import java.util.List;

import cs3500.reversi.model.BasicReversi;
import cs3500.reversi.model.Tuple;
import cs3500.reversi.provider.controller.ReversiController;
import cs3500.reversi.provider.model.BoardPosn;

public class ReversiAdapter implements PlayerFeatures {

  ReversiController providerController;
  BasicReversi model;

  public ReversiAdapter(ReversiController providerController, BasicReversi model){
    this.providerController = providerController;
    this.model = model;
  }


  @Override
  public void makeMove(Tuple<Integer, Integer> coordinate) {
    List<Tuple<Integer, Integer>> coordinates = model.getCoordinates();
    BoardPosn boardPosn = convertRowColtoBoardPosn(coordinates);
    providerController.moveMade(boardPosn);

  }

  private BoardPosn convertRowColtoBoardPosn(List<Tuple<Integer, Integer>> coordinates) {
    //
    return null;
  }


}
