package cs3500.reversi.controller;


import cs3500.reversi.model.Colors;
import cs3500.reversi.model.ReadonlyReversiModel;
import cs3500.reversi.model.Reversi;
import cs3500.reversi.model.Strategy;
import cs3500.reversi.model.Tuple;

/**
 * AI player class to implement different strategies.
 */

public class AIPlayer implements Player {

  private Colors color;

  private ReadonlyReversiModel model;


  private PlayerFeatures listener;

  private Strategy strategy;

  private boolean isHuman;


  /**
   * Constructor to create an AI player object.
   *
   * @param model    the model
   * @param strategy the different strategies
   * @param color    the color of the player
   */
  public AIPlayer(ReadonlyReversiModel model, Strategy strategy, Colors color) {
    this.model = model;
    this.strategy = strategy;
    this.color = color;
    this.isHuman=false;
  }

  @Override
  public Colors getColor() {
    return this.color;
  }


  @Override
  public void notifyOfMove() {
    Tuple<Integer,Integer> move=strategy.chooseMove(model, color);
    this.listener.makeMove(move);

  }

  @Override
  public void addListener(PlayerFeatures listener) {
    this.listener = listener;
  }

  @Override
  public boolean isHuman() {
    return this.isHuman;
  }


}
