package cs3500.reversi.controller;


import cs3500.reversi.model.Colors;
import cs3500.reversi.model.Reversi;
import cs3500.reversi.model.Strategy;

/**
 * AI player class to implement different strategies.
 */

public class AIPlayer implements Player {

  private Colors color;

  private Reversi model;


  private PlayerFeatures listener;

  private Strategy strategy;


  /**
   * Constructor to create an AI player object.
   *
   * @param model    the model
   * @param strategy the different strategies
   * @param color    the color of the player
   */
  public AIPlayer(Reversi model, Strategy strategy, Colors color) {
    this.model = model;
    this.strategy = strategy;
    this.color = color;
  }

  @Override
  public Colors getColor() {
    return this.color;
  }


  @Override
  public void notifyOfMove() {
    this.listener.makeMove(strategy.chooseMove(model, color));

  }

  @Override
  public void addListener(PlayerFeatures listener) {
    this.listener = listener;
  }


}
