package cs3500.reversi.model;

import java.util.Objects;

/**
 * Class for the Tuple class that represents a coordinate.
 *
 * @param <A> First element
 * @param <B> Second element
 */
public class Tuple<A, B> {
  private final Integer first;
  private final Integer second;

  public Tuple(Integer first, Integer second) {
    this.first = first;
    this.second = second;
  }

  /**
   * Gets the row of the coordinate.
   *
   * @return the first integer in tuple.
   */
  public Integer getFirst() {
    return first;
  }

  /**
   * Gets the column of the coordinate.
   *
   * @return the second integer in tuple
   */
  public Integer getSecond() {
    return second;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Tuple<?, ?> tuple = (Tuple<?, ?>) o;
    return Objects.equals(first, tuple.first) &&
            Objects.equals(second, tuple.second);
  }

  @Override
  public int hashCode() {
    return Objects.hash(first, second);
  }


}