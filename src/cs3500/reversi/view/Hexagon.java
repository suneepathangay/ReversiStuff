package cs3500.reversi.view;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.geom.Path2D;
import cs3500.reversi.model.Colors;
import cs3500.reversi.model.IDisc;

/**
 * Class to represent a hexagon for a Reversi board.
 * Represents a hexagon.
 * This class extends Path2D.Double.
 * This class is used to draw a hexagon.
 */
public class Hexagon extends Path2D.Double {
  private double centerX;
  private double centerY;
  private int size;
  private IDisc disc;

  /**
   * Constructor for Hexagon class.
   * @param centerX the x coordinate of the center of the hexagon
   * @param centerY the y coordinate of the center of the hexagon
   * @param size the size of the hexagon
   * @param fillColor the color to fill the hexagon with
   * @param disc the disc to draw inside the hexagon
   */
  public Hexagon(double centerX, double centerY, int size, Color fillColor, IDisc disc) {
    this.centerX = centerX;
    this.centerY = centerY;
    this.size = size;
    this.disc = disc;
  }

  /**
   * Method to draw the hexagon.
   * @param g2d the graphics object
   * @param discColor the color of the disc
   */
  public void draw(Graphics2D g2d, Color discColor) {
    Path2D.Double hex = new Path2D.Double();
    double startAngle = Math.PI / 6;
    hex.moveTo(centerX + size * Math.cos(startAngle), centerY + size * Math.sin(startAngle));
    for (int i = 1; i < 6; i++) {
      hex.lineTo(centerX + size * Math.cos(startAngle + i * Math.PI / 3),
              centerY + size * Math.sin(startAngle + i * Math.PI / 3));
    }
    hex.closePath();

    // Determine the color to fill the hexagon with
    Color fillColor = Color.LIGHT_GRAY;
    if (disc != null && disc.getColor() == Colors.BLUE) {
      fillColor = Color.CYAN;
    }
    g2d.setColor(fillColor);
    g2d.fill(hex);

    // If the disc is not null and not blue, draw the disc inside the hexagon
    if (disc != null && disc.getColor() != Colors.BLUE) {
      g2d.setColor(discColor);
      int radius = size - 5; // Adjust the size to fit inside the hexagon
      g2d.fillOval((int) (centerX - radius / 2), (int) (centerY - radius / 2), radius, radius);
    }

    // Draw the outline of the hexagon
    g2d.setColor(Color.BLACK);
    g2d.draw(hex);
  }
}