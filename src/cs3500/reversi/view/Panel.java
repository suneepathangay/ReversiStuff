package cs3500.reversi.view;

//import java.awt.*;
import java.awt.Polygon;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.JDialog;

import cs3500.reversi.controller.Features;
import cs3500.reversi.model.Colors;
import cs3500.reversi.model.IDisc;
import cs3500.reversi.model.ReadonlyReversiModel;
import cs3500.reversi.model.Tuple;

/**
 * Class to create a panel in the view.
 */
public class Panel extends JPanel implements IPanel {
  private ReadonlyReversiModel readOnlyGame;

  private int prevSelectedRow = -1;
  private int prevSelectedCol = -1;

  private List<List<IDisc>> currBoard;


  private Colors prevSelectedColor = Colors.GRAY;

  /**
   * Constructor to construct a panel object.
   *
   * @param model a Readonly Reversi model
   */
  public Panel(ReadonlyReversiModel model) {
    this.readOnlyGame = model;
    this.currBoard = readOnlyGame.getBoard();
    setFocusable(true);
    requestFocusInWindow();
  }


  private void handleClick(int x, int y) {
    int sideLength = readOnlyGame.getLength();
    int numRows = readOnlyGame.getNumRows();
    int hexSize = calculateHexSize(numRows);
    double hexHeight = Math.sqrt(3) * hexSize;
    double hexWidth = 2 * hexSize;

    double boardCenterX = getWidth() / 2.0;
    double boardCenterY = getHeight() / 2.0;
    double verticalAdjustment = (numRows * hexHeight) / 2;

    for (int row = 0; row < numRows; row++) {
      int colsInRow = sideLength + (row < sideLength ? row : 2 * sideLength - row - 2);
      double offsetX = boardCenterX - (colsInRow * hexWidth * 3 / 4) / 2;
      double offsetY = boardCenterY - verticalAdjustment + row * (hexHeight * 3 / 4);

      for (int col = 0; col < colsInRow; col++) {
        double hexX = offsetX + col * (hexWidth * 3 / 4);
        double hexY = offsetY;

        if (isClickInsideHexagon(x, y, hexX, hexY, hexSize)) {
          int adjustedCol = col;
          if (row > sideLength - 1) {
            adjustedCol = col + row - sideLength + 1;
          }

          if (row == prevSelectedRow && adjustedCol == prevSelectedCol) {
            Tuple<Integer, Integer> coor = new Tuple<Integer, Integer>(prevSelectedRow,
                    adjustedCol);
            Tuple<Integer, Integer> indexes = readOnlyGame.getIndexs(coor);

            currBoard.get(indexes.getFirst()).get(indexes.getSecond()).setColor(prevSelectedColor);
            prevSelectedRow = -1;
            prevSelectedCol = -1;
            prevSelectedColor = Colors.GRAY;
          } else {
            if (prevSelectedRow != -1 && prevSelectedCol != -1) {
              Tuple<Integer, Integer> coor = new Tuple<Integer, Integer>(prevSelectedRow,
                      prevSelectedCol);
              Tuple<Integer, Integer> prevIndexes = readOnlyGame.getIndexs(coor);

              currBoard.get(prevIndexes.getFirst()).get(prevIndexes.getSecond())
                      .setColor(prevSelectedColor);
            }

            prevSelectedColor = readOnlyGame.getTileAt(row, adjustedCol).getColor();
            Tuple<Integer, Integer> coor = new Tuple<Integer, Integer>(row, adjustedCol);
            Tuple<Integer, Integer> currIndexes = readOnlyGame.getIndexs(coor);
            currBoard.get(currIndexes.getFirst()).get(currIndexes.getSecond())
                    .setColor(Colors.BLUE);

            prevSelectedRow = row;
            prevSelectedCol = adjustedCol;
          }

          repaint();
          return;
        }
      }
    }

    if (prevSelectedRow != -1 && prevSelectedCol != -1) {
      Tuple<Integer, Integer> coor = new Tuple<Integer, Integer>(prevSelectedRow, prevSelectedCol);
      Tuple<Integer, Integer> indexes = readOnlyGame.getIndexs(coor);
      currBoard.get(indexes.getFirst()).get(indexes.getSecond()).setColor(prevSelectedColor);

      prevSelectedRow = -1;
      prevSelectedCol = -1;
      prevSelectedColor = Colors.GRAY;
      repaint();
    }
  }


  @Override
  public void addFeatures(Features features) {

    this.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        handleClick(e.getX(), e.getY());

      }
    });


    this.addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        if (e.getKeyChar() == 'p' || e.getKeyChar() == 'P') {

          features.passMove();
          repaint();
        }
        if (prevSelectedRow != -1 && prevSelectedCol != -1 && e.getKeyCode() == KeyEvent.VK_ENTER) {
          int originalCol = prevSelectedCol;

          try {

            features.clickTile(prevSelectedRow, originalCol);

            prevSelectedRow = -1;
            prevSelectedCol = -1;
            prevSelectedColor = Colors.GRAY;
            repaint();
          } catch (IllegalStateException ex) {

            JOptionPane
                    .showMessageDialog(null,
                            "Illegal move. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
          }
        }
      }


    });

  }


  @Override
  public void notifyGame() {

    this.currBoard = readOnlyGame.getBoard();
    repaint();

  }

  @Override
  public void notifyPlayerChanged(String message) {
    JOptionPane optionPane = new JOptionPane(
            message,
            JOptionPane.INFORMATION_MESSAGE,
            JOptionPane.DEFAULT_OPTION,
            null,
            new Object[]{},

            null);
    JDialog dialog = optionPane.createDialog(this, "Player Turn");
    dialog.setModal(true);


    dialog.setLocationRelativeTo(this);

    dialog.setVisible(true);
  }

  @Override
  public void notifyGameOver(String winner) {
    JOptionPane optionPane = new JOptionPane(
            winner,
            JOptionPane.INFORMATION_MESSAGE,
            JOptionPane.DEFAULT_OPTION,
            null,
            new Object[]{},

            null);
    JDialog dialog = optionPane.createDialog(this, "Game Over");
    dialog.setModal(true);

    dialog.setLocationRelativeTo(this);
    dialog.setVisible(true);
  }

  private boolean isClickInsideHexagon(
          double clickX, double clickY, double centerX, double centerY, int size) {

    Polygon hex = new Polygon();
    for (int i = 0; i < 6; i++) {
      double angleDeg = 60 * i - 30;
      double angleRad = Math.PI / 180 * angleDeg;
      hex.addPoint(
              (int) (centerX + size * Math.cos(angleRad)),
              (int) (centerY + size * Math.sin(angleRad))
      );
    }

    return hex.contains(clickX, clickY);
  }


  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    List<List<IDisc>> board = readOnlyGame.getBoard();
    int sideLength = readOnlyGame.getLength();
    int numRows = readOnlyGame.getNumRows();
    int hexSize = calculateHexSize(numRows);
    double hexHeight = Math.sqrt(3) * hexSize;
    double hexWidth = 2 * hexSize;

    double padding = hexSize / 10.0;
    double boardCenterX = getWidth() / 2.0;
    double boardCenterY = getHeight() / 2.0;


    double verticalAdjustment = (numRows * hexHeight) / 2;

    for (int row = 0; row < numRows; row++) {
      int colsInRow = sideLength + (row < sideLength ? row : 2 * sideLength - row - 2);


      double offsetX = boardCenterX - (colsInRow * (hexWidth + padding) * 3 / 4) / 2;
      double offsetY = boardCenterY - verticalAdjustment + row * (hexHeight * 3 / 4 + padding);


      for (int col = 0; col < colsInRow; col++) {
        IDisc disc = board.get(row).size() > col ? board.get(row).get(col) : null;
        drawAndFillHexagon(g2d, offsetX + col * ((hexWidth + padding) * 3 / 4),
                offsetY, hexSize, Color.GRAY, disc);
      }
    }
  }


  private int calculateHexSize(int numRows) {
    int baseSize = 300;
    int hexSize = baseSize / numRows;
    return hexSize;
  }

  private void drawAndFillHexagon(Graphics2D g2d, double centerX, double centerY,
                                  int size, java.awt.Color fillColor, IDisc disc) {
    Hexagon hexagon = new Hexagon(centerX, centerY, size, fillColor, disc);
    Color discColor = getDiscColor(disc.getColor());
    hexagon.draw(g2d, discColor);
  }

  private Color getDiscColor(Colors color) {
    switch (color) {
      case BLACK:
        return Color.BLACK;
      case WHITE:
        return Color.WHITE;
      case BLUE:
        return Color.CYAN;
      default:
        return Color.LIGHT_GRAY;
    }
  }
}