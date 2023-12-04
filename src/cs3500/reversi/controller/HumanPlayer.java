package cs3500.reversi.controller;


import cs3500.reversi.model.Colors;
import cs3500.reversi.model.ReadonlyReversiModel;

/**
 * Class for a human player that implements the Player Interface.
 */

public class HumanPlayer implements Player {

  private final Colors color;


  public HumanPlayer(ReadonlyReversiModel model, Colors color) {
    this.color = color;
  }


  @Override
  public Colors getColor() {
    return this.color;
  }


  @Override
  public void notifyOfMove() {
    //nothing cuz player interacts with view
  }

  @Override
  public void addListener(PlayerFeatures listener) {
    //nothing because the player interacts with the view
  }

}
