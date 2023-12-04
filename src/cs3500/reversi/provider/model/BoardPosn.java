package cs3500.reversi.provider.model;

/**
 * Represents a position of a pointy top hexagon on the board. The board is made up of
 * pointy top hexagons. (0,0,0) is the top left corner.
 */
public class BoardPosn {

  // public because this field is immutable/final. additionally, these are the only
  // interesting features of a BoardPosn.
  // represents a row of the board, 0 based, starting from the top (north) and increasing downwards
  // / southwards.
  public final int r;

  // public because this field is immutable/final. additionally, these are the only
  // interesting features of a BoardPosn.
  // 0 based, starting from the south west side of the board, and increasing towards the north east
  // side of the board.
  public final int q;

  // public because this field is immutable/final. additionally, these are the only
  // interesting features of a BoardPosn.
  // 0 based, starting from the south east side of the board, and increasing towards the north west
  // side of the board.
  public final int s;

  // INVARIANT: r + q + s = 0

  /**
   * Constructs a BoardPosn with the given r, q, and s values.
   *
   * @param r the row of the board, 0 based,
   *          starting from the top (north) and increasing downwards
   * @param q 0 based, starting from the south west side of the board,
   *          and increasing towards the north east side of the board.
   * @param s 0 based, starting from the south east side of the board,
   *          and increasing towards the north west side of the board.
   * @throws IllegalArgumentException if r + q + s != 0
   */
  public BoardPosn(int q, int r, int s) {
    if (r + q + s != 0) {
      throw new IllegalArgumentException("r + q + s must equal 0");
    }
    this.r = r;
    this.q = q;
    this.s = s;
  }

  /**
   * Constructs a BoardPosn with the given r and q values.
   *
   * @param r the row of the board, 0 based,
   *          starting from the top (north) and increasing downwards
   * @param q 0 based, starting from the south west side of the board, a
   *          nd increasing towards the north east side of the board.
   */
  public BoardPosn(int q, int r) {
    this(q, r, -r - q);
  }

  /**
   * Determines wether the given object is the same (in meaning) as this board posn.
   *
   * @param o that we checking eqaulity against
   * @return true if this and given have the same meaning (same s, q, r)
   */
  @Override
  public boolean equals(Object o) {
    if (o instanceof BoardPosn) {
      BoardPosn other = (BoardPosn) o;
      return this.r == other.r && this.q == other.q && this.s == other.s;
    }
    return false;
  }

  /**
   * creates a hash code for this board posn.
   *
   * @return an int representing the hashcode
   */
  @Override
  public int hashCode() {
    return this.r * 31 + this.q * 17 + this.s * 13;
  }

  /**
   * Converts this board posn to the pixel x coordinate.
   * The x returned is the x value of the center of the hexagon that this board posn represents.
   *
   * @param hexSize the size of the hexagon, which is the distance from the center to top
   *                middle corner.
   * @return the pixel x coordinate of the center of the hexagon that this board posn represents.
   */
  // math is from the following source: https://www.redblobgames.com/grids/hexagons/#basics
  public double convertBoardPosnToPixelX(int hexSize) {
    return hexSize * (Math.sqrt(3) * this.q + Math.sqrt(3) / 2 * this.r);
  }

  /**
   * Converts this board posn to the pixel y coordinate.
   * The y returned is the y value of center of the hexagon that this board posn represents.
   *
   * @param hexSize the size of the hexagon, which is the distance from the center to top
   *                middle corner.
   * @return the pixel y coordinate of the center of the hexagon that this board posn represents.
   */
  // math is from the following source: https://www.redblobgames.com/grids/hexagons/#basics
  public double convertBoardPosnToPixelY(int hexSize) {
    return hexSize * (3. / 2 * this.r);
  }


}
