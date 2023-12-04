package cs3500.reversi.provider.model;

import java.util.List;

/**
 * Represents the empty (no game pieces),
 * hexagonal game board for a game of Reversi. The board is made up of
 * rows pointy top hexagons that are arranged in a flat top hexagonal shape.
 */
public interface IBoard {

  /**
   * Returns the coordinate of the center tile of this board.
   * @return the coordinate of the center tile of this board.
   */
  BoardPosn getCenterCoord();

  /**
   * Returns the adjacent tiles of the given tile in all directions.
   * Adjacent, meaning they share an edge.
   * @param cell the tile whose adjacent tiles we want to get.
   * @return the adjacent tiles of the given tile, in order starting from east, going clockwise.
   */
  List<BoardPosn> getAdjacent(BoardPosn cell);

  /**
   * Returns true if the given BoardPosn is in bounds of this board/a tile on the board.
   * @param cell the BoardPosn we want to check if it is in bounds.
   * @return true if the given BoardPosn is in bounds of this board.
   */
  boolean inBounds(BoardPosn cell);

  /**
   * Returns the board as a list of lists of BoardPosns. Each BoardPosn is one hexagon.
   * @return the board as a list of lists of BoardPosns.
   */
  List<List<BoardPosn>> getBoard();


  /**
   * Returns the edges of tiles from the given BoardPosn/cell.
   * Edges meaning all the tiles that are in a straight line from the given posn's tile. each edge
   * are all of the cells on the board that are adjacent to each other with the same edge
   * in a straight line. (ex. the west edge are all the cells that are directly to the west of the
   * given cell, and are adjacent to each other). If there are no adjacent cells, then the list is
   * empty.
   * @return the edges of tiles from the given BoardPosn/cell.
   */
  List<List<BoardPosn>> getEdgesFrom(BoardPosn posn);

  /**
   * Returns the cells at the corners of this board. There are 6 corners, and a corner
   * is where two edges of the board connect.
   * @return the cells at the corners of this board.
   */
  List<BoardPosn> getCorners();
}
