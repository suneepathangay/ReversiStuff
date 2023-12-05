package cs3500.reversi.view;


import java.awt.Color;


import javax.swing.JFrame;

import cs3500.reversi.controller.Features;

import cs3500.reversi.model.ReadonlyReversiModel;
import cs3500.reversi.model.Reversi;

/**
 * Class to represent the frame of the view that contains the panel and board.
 */

public class Frame extends JFrame implements IFrame {


  private JFrame frame;

  private Panel panel;

  /**
   * Constructor to contstruct a frame.
   * @param model a non mutable model
   */
  public Frame(Reversi model) {


    frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(800, 800);


    //create a new panel
    panel = new Panel(model);
    panel.setOpaque(false);
    panel.setBackground(Color.BLACK);
    frame.getContentPane().setBackground(Color.DARK_GRAY);
    frame.add(panel);


  }

  @Override
  public void display() {
    frame.setVisible(true);
  }

  @Override
  public void addFeatures(Features features) {
    this.panel.addFeatures(features);
  }

  @Override
  public void notifyPanel() {
    this.panel.notifyGame();
  }

  @Override
  public void notifyPlayerChanged(String message) {
    this.panel.notifyPlayerChanged(message);
  }

  @Override
  public void setTitle(String player) {
    frame.setTitle(player);
  }

  @Override
  public void notifyGameOver(String winner) {
    this.panel.notifyGameOver(winner);
  }


}