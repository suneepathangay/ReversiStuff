package cs3500.reversi.controller;

import cs3500.reversi.model.Colors;
import cs3500.reversi.model.Message;
import cs3500.reversi.model.Reversi;
import cs3500.reversi.model.Tuple;
import cs3500.reversi.view.IFrame;

/**
 * Controller class that implements Features,ModelFeatures, and PlayerFeatures interfaces
 * to subscribe itself as a listener for both the model and the view.
 */

public class Controller implements Features, ModelFeatures, PlayerFeatures {

  private Reversi model;

  private IFrame view;

  private Player player;


  private Message typePlayer;

  /**
   * Constructor for Controller class.
   * @param model a mutable Reversi model
   * @param view a IFrame view
   * @param player a human or AI player
   */

  public Controller(Reversi model, IFrame view, Player player) {
    this.model = model;
    this.player = player;
    this.view = view;

    view.addFeatures(this);
    model.addListeners(this);
    player.addListener(this);
    model.addPlayerListeners(player);
    view.display();


  }

  @Override
  public void clickTile(int row, int col) {
    Colors playerColor = player.getColor();
    Colors modelTurn = model.getTurn();
    if (modelTurn == playerColor) {
      model.placeTile(row, col, playerColor);
    } else {
      throw new IllegalStateException("Not your turn");
    }
  }

  @Override
  public void setPlayer(Message player) {
    this.typePlayer = player;
  }



  @Override
  public Message getPlayer() {
    return this.typePlayer;
  }


  @Override
  public void setTitle(Message player) {
    if (player == Message.Player1) {
      view.setTitle("Player 1 " + this.player.getColor());
    } else {
      view.setTitle("Player 2 " + this.player.getColor());
    }

  }

  @Override
  public boolean isHuman() {
    return player.isHuman();
  }


  @Override
  public void passMove() {
    model.passMove();
  }


  @Override
  public void notifyBoardChanged() {
    this.view.notifyPanel();
  }

  @Override
  public void notifyPlayerChanged(Message player) {
    String message = "It is your turn" + " " + player;
    this.view.notifyPlayerChanged(message);
  }

  @Override
  public void notifyGameOver(String winner) {
    this.view.notifyGameOver(winner);
  }

  @Override
  public void makeMove(Tuple<Integer, Integer> coordinate) {
    if(coordinate!=null) {
      model.placeTile(coordinate.getFirst(), coordinate.getSecond(), player.getColor());
    }else{
      model.passMove();
    }
  }
}