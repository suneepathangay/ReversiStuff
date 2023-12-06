package cs3500.reversi.controller;

import cs3500.reversi.model.Tuple;
import cs3500.reversi.provider.controller.ReversiController;
import cs3500.reversi.provider.model.BoardPosn;
import cs3500.reversi.provider.view.PlayerAction;

public class FeaturesAdapter implements PlayerAction {

  private Features delegate;
  @Override
  public void chooseMove(BoardPosn move) {

    //convert their coordinates to our coordinates
    //call the delegate(controller) to make the move
    //delegate.clickTile();
  }

  @Override
  public void choosePass() {
    delegate.passMove();
  }

  @Override
  public void addListener(ReversiController listener) {


  }
}
