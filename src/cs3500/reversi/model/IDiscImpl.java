package cs3500.reversi.model;

/**
 * Class for the IDisc class.
 * Implements the IDisc interface and represents a disc in the game of Reversi.
 */
public class IDiscImpl implements IDisc {
  private final Integer row;
  private final Integer col;
  private Colors color;

  /**
   * Constructor for the IDiscImpl class.
   *
   * @param color of the disc
   * @param row   of the disc
   * @param col   of the disc
   */
  public IDiscImpl(Colors color, int row, int col) {
    this.color = color;
    this.row = row;
    this.col = col;
  }

  /**
   * Gets the color of the disc.
   *
   * @return Color of the disc
   */
  @Override
  public Colors getColor() {
    return this.color;
  }

  /**
   * Sets the disc to a new color.
   *
   * @param newColor new color for the disc
   */
  @Override
  public void setColor(Colors newColor) {
    this.color = newColor;
  }

  /**
   * Gets the string representation of the disc.
   *
   * @return String representation of disc
   */
  @Override
  public String toString() {
    return this.color.toString();
  }

  /**
   * Gets the row coordinate of disc.
   *
   * @return row
   */
  @Override
  public Integer getRow() {
    return this.row;
  }

  /**
   * Gets the col coordinate of the disc.
   *
   * @return the col
   */
  @Override
  public Integer getCol() {
    return this.col;
  }

}