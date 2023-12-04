package cs3500.reversi.provider.view;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;

import cs3500.reversi.provider.model.BoardPosn;
import cs3500.reversi.provider.model.PlayerColor;
import cs3500.reversi.provider.model.ReadOnlyReversiModel;

/**
 * Represents a panel in the Reversi cs3500.provider.view. This displays the game board with the player pieces
 * of the current state of the game. The game board logical coordinates have the origin in the
 * top left corner, and x increases to the right and y increases downwards.
 * we translate based on board size to get the board to be centered on the panel.
 */

public class ReversiPanel extends JPanel {
  private final ReadOnlyReversiModel model;
  private final List<BoardPosn> selectedCells;
  private final Color selectedCellColor;
  private final Color emptyCellColor;
  private final List<List<BoardPosn>> boardAsListOfBoardPosn;
  private final int hexSize;
  private String endGameMsg;

  /**
   * Constructs a ReversiPanel that displays the given cs3500.provider.view.model's state.
   *
   * @param m the read only reversi cs3500.provider.view.model that our cs3500.provider.view shows,
   *          so that the cs3500.provider.view cannot mutate it.
   */
  public ReversiPanel(ReadOnlyReversiModel m) {
    this.model = m;
    this.boardAsListOfBoardPosn = this.model.getBoard().getBoard();
    this.hexSize = 400 / this.model.getBoardSize();
    this.emptyCellColor = new Color(229, 204, 255);
    this.selectedCellColor = new Color(204, 229, 255, 200);
    MouseEventsListener listener = new MouseEventsListener();
    this.addMouseListener(listener);
    this.selectedCells = new ArrayList<>();
    this.endGameMsg = "";
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g.create();
    g2d.transform(transformLogicalToPhysical());

    Color start = g2d.getColor();
    for (List<BoardPosn> row : this.boardAsListOfBoardPosn) {
      for (BoardPosn hexagon : row) {
        this.drawHexagon(hexagon, this.emptyCellColor, g2d);
      }
    }
    this.drawCurrentStatePlayers(this.model.getCapturedHexagons(PlayerColor.BLACK),
            Color.BLACK, g2d);
    this.drawCurrentStatePlayers(this.model.getCapturedHexagons(PlayerColor.WHITE),
            Color.WHITE, g2d);
    if (!this.selectedCells.isEmpty()) {
      this.drawHexagon(this.selectedCells.get(0), this.selectedCellColor, g2d);
    }
    this.drawGameOverMsg(g2d);

    g2d.setColor(start);


  }

  private void drawGameOverMsg(Graphics2D g2d) {
    Font startFont = g2d.getFont();
    Color startColor = g2d.getColor();

    // Set font and color
    g2d.setFont(new Font("Serif", Font.BOLD, 60));
    g2d.setColor(Color.BLUE);

    FontMetrics fm = g2d.getFontMetrics();
    int textWidth = fm.stringWidth(this.endGameMsg);

    // Calculate x-coordinate for centering text
    int x = (this.getPreferredLogicalSize().width - textWidth) / 2;

    // Draw text at the center along the x-axis
    g2d.drawString(this.endGameMsg, x, 0);
    g2d.setFont(startFont);
    g2d.setColor(startColor);
  }

  // made package private so only cs3500.provider.view related classes can call it.
  void setEndGameMsg(String msg) {
    this.endGameMsg = msg;
  }

  // draws a hexagon with the given BoardPosn and color
  private void drawHexagon(BoardPosn hexagon, Color color, Graphics2D g2d) {
    Color start = g2d.getColor();
    double physicalX = hexagon.convertBoardPosnToPixelX(this.hexSize);
    double physicalY = hexagon.convertBoardPosnToPixelY(this.hexSize);
    Path2D.Double gamePiece = new PointyTopHexagon(physicalX, physicalY, hexSize);
    g2d.setColor(color);
    g2d.fill(gamePiece);
    Color outline = new Color(178, 102, 255);
    g2d.setColor(outline);
    g2d.setStroke(new BasicStroke(2));
    g2d.draw(gamePiece);
    g2d.setColor(start);

  }


  // draws a circle with a center and color. represents a game piece.
  private void drawGamePiece(BoardPosn captured, Color color, Graphics2D g2d) {
    Color start = g2d.getColor();
    int physicalX = (int) captured.convertBoardPosnToPixelX(this.hexSize);
    int physicalY = (int) captured.convertBoardPosnToPixelY(this.hexSize);
    int radius = hexSize / 2;
    g2d.setColor(color);
    g2d.fillOval(physicalX - radius, physicalY - radius, 2 * radius, 2 * radius);
    g2d.setColor(start);
  }

  /**
   * Draws the current state of the captured pieces in the game.
   *
   * @param capturedCells the list of captured cells to draw
   * @param playerColor   the color of the player who captured the cells
   * @param g2d           the graphics object to draw on
   */
  private void drawCurrentStatePlayers(List<BoardPosn> capturedCells, Color playerColor,
                                       Graphics2D g2d) {
    Color start = g2d.getColor();
    for (BoardPosn hexagon : capturedCells) {
      this.drawGamePiece(hexagon, playerColor, g2d);
    }
    g2d.setColor(start);
  }

  // set the preferred size
  // to be the width of the longest (middle) row of the game board (rt 3 * hexSize * boardSize)
  // pixels wide and tall
  @Override
  public Dimension getPreferredSize() {
    return new Dimension((int) Math.ceil(
            Math.sqrt(3) * this.hexSize * this.model.getBoardSize()
    ), (int) Math.ceil(
            Math.sqrt(3) * this.hexSize * this.model.getBoardSize()
    ));
  }

  // returns the preferred logical size of the board. it is 800 units x 800 units. it is
  // easier to think about than the physical size.
  private Dimension getPreferredLogicalSize() {
    return new Dimension(800, 800);
  }

  /**
   * Computes the transformation that converts game board coordinates (with (0,0) in the top left,
   * width and height our logical size) into screen coordinates (with (0,0) to be shifted down
   * and to the left {@link ReversiPanel#translationX()}
   * {@link ReversiPanel#translationY()}--which allows our game board to be centered when shown,
   * and our logical width and height in pixels).
   * <p>
   * This is the inverse of {@link ReversiPanel#transformPhysicalToLogical()}.
   * </p>
   *
   * @return The necessary transformation
   */

  private AffineTransform transformLogicalToPhysical() {
    AffineTransform at = new AffineTransform();
    Dimension preferred = this.getPreferredLogicalSize();

    at.translate(translationX(),
            translationY());
    at.scale(this.getWidth() / preferred.getWidth(),
            this.getHeight() / preferred.getHeight());

    return at;
  }

  /**
   * Computes the transformation that converts screen coordinates (with (0,0) to be shifted down
   * and to the left{@link ReversiPanel#translationX()} {@link ReversiPanel#translationY()}
   * --which allows our game board to be centered when shown, and our logical width
   * and height in pixels) into board coordinates (with (0,0) in the top left, width and
   * height our logical size).
   *
   * @return The necessary transformation
   * <p>
   * This is the inverse of {@link ReversiPanel#transformLogicalToPhysical()}.
   * </p>
   */

  private AffineTransform transformPhysicalToLogical() {
    AffineTransform at = new AffineTransform();
    Dimension preferred = this.getPreferredLogicalSize();
    at.scale(preferred.getWidth() / this.getWidth(),
            preferred.getHeight() / this.getHeight());

    at.translate(-translationX(),
            -translationY());
    return at;
  }

  // math to translate the board along the X axis to be centered/completely shown on the panel.
  // the 0 x value is now further to the left.
  private double translationX() {
    double width = this.getWidth() / (1.25 * this.model.getBoardSize());
    return (width - (width / 2) * this.model.getBoardSize() / 2);
  }

  // math to translate the board along the Y axis to be centered/completely shown on the panel
  // th 0 y value is now further down.
  private double translationY() {
    return (double) this.getHeight() / (this.model.getBoardSize() + 1);
  }

  // math to convert a pixel to a board posn. works even for pixels that are not the exact
  // center of the hexagon (will convert all pixels in the hexagon to the correct board posn)
  // math is from the following source: https://www.redblobgames.com/grids/hexagons/#basics
  private BoardPosn convertPixelToBoardPosn(double x, double y) {
    double q = (Math.sqrt(3) / 3 * x - 1. / 3 * (y)) / hexSize;
    double r = (2. / 3 * y) / hexSize;
    double s = -q - r;
    return roundPixelBoardPosn(q, r, s);
  }

  // math to round double q,r,s values to the correct board posn -- mainly so when the
  // pixel is not the exact center of the hexagon, the correct BoardPosn will be produced.
  // math is from the following source: https://www.redblobgames.com/grids/hexagons/#basics
  private BoardPosn roundPixelBoardPosn(double q, double r, double s) {
    int qInt = (int) Math.round(q);
    int rInt = (int) Math.round(r);
    int sInt = (int) Math.round(s);

    double qDiff = Math.abs(qInt - q);
    double rDiff = Math.abs(rInt - r);
    double sDiff = Math.abs(sInt - s);

    if (qDiff > rDiff && qDiff > sDiff) {
      qInt = -rInt - sInt;
    } else if (rDiff > sDiff) {
      rInt = -qInt - sInt;
    } else {
      sInt = -qInt - rInt;
    }
    return new BoardPosn(qInt, rInt, sInt);
  }

  /**
   * Returns the selected/highlighted cell in this ReversiPanel/on the visible game board.
   *
   * @return the selected cell in this ReversiPanel.
   * @throws IllegalStateException if no cell is selected.
   */
  BoardPosn getSelectedCell() throws IllegalStateException {
    if (this.selectedCells.isEmpty()) {
      throw new IllegalStateException("No cell is selected");
    } else {
      return this.selectedCells.get(0);
    }
  }

  /**
   * Clears the selected cell. Made package private so that the frame can call it.
   */
  void clearSelectedCell() {
    this.selectedCells.clear();
  }



  /**
   * Represents a mouse listener for this ReversiPanel. Handles relevant mouse events like
   * clicking on the board.
   */
  private class MouseEventsListener extends MouseInputAdapter implements MouseListener {

    /**
     * Invoked when the mouse button has been pressed on this panel.
     * If the mouse is pressed on an unselected hexagon, then that cell is selected and highlighted.
     * If the mouse is pressed on a selected hexagon, then that hexagon is deselected.
     * if the mouse is pressed on a position off of the board (not on a visible hexagon),
     * if there is a selected
     * cell, it will also be deselected.
     *
     * @param e the event to be processed
     */
    @Override
    public void mousePressed(MouseEvent e) {
      Point physicalP = e.getPoint();
      Point2D logicalP = transformPhysicalToLogical().transform(physicalP, null);
      BoardPosn cellPosn = convertPixelToBoardPosn(logicalP.getX(), logicalP.getY());
      System.out.println("(q, r) : (" + cellPosn.q + ", " + cellPosn.r + ")");


      if (model.getBoard().inBounds(cellPosn)) {
        if (ReversiPanel.this.selectedCells.isEmpty()) {
          ReversiPanel.this.selectedCells.add(cellPosn);
        } else {
          BoardPosn prevSelected = ReversiPanel.this.selectedCells.get(0);
          ReversiPanel.this.selectedCells.clear();

          if (!prevSelected.equals(cellPosn)) {
            ReversiPanel.this.selectedCells.add(cellPosn);
          }
        }
      } else {
        ReversiPanel.this.selectedCells.clear();
      }
      ReversiPanel.this.repaint();
    }

  }

}
