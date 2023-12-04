package cs3500.reversi.model;

/**
 * Interface for the IDisc class.
 * Represents a disc in the game of Reversi.
 * A disc has a color, a row, and a column.
 * A disc can be flipped to the other color.
 */
public interface IDisc {

  /**
   * Method to get the string of the IDisc.
   *
   * @return String representation of the IDisc
   */
  String toString();

  /**
   * Gets the Color of the dics.
   */
  Colors getColor();

  /**
   * Sets the visibility of the disc.
   */
  void setColor(Colors newColor);

  /**
   * Gets the row of the disc.
   *
   * @return the row of the coordinate
   */
  Integer getRow();

  /**
   * Gets the column of the disc.
   *
   * @return the column integer of the coordinate
   */
  Integer getCol();


}
