package cs3500.reversi.provider.view;

import java.awt.geom.Path2D;

/**
 * Represents a geometric path of points for a pointy top hexagon.
 * each represents a cell on the game board
 */
public class PointyTopHexagon extends Path2D.Double {


  /**
   * Constructs a pointy top hexagon with the given center and size. The origin is in the top
   * left corner, and x increases to the right and y increases downwards.
   * @param size the size of the hexagon. Refers to the distance from the center to the
   *             north top point/corner.
   * @param xCenter the x coordinate of the center of the hexagon
   * @param yCenter the y coordinate of the center of the hexagon
   */
  public PointyTopHexagon(double xCenter, double yCenter, int size) {
    this.moveTo( xCenter + size * Math.cos(Math.toRadians(-30)),
            yCenter + size * Math.sin(Math.toRadians(-30)));
    for (int i = 1; i < 6 ; i++) {
      int angleDegree = 60 * i - 30;

      this.lineTo( xCenter + size * Math.cos(Math.toRadians(angleDegree)),
              yCenter + size * Math.sin(Math.toRadians(angleDegree)));
    }
    this.closePath();
  }

}

