package cs3500.reversi.view;

import java.util.List;

import cs3500.reversi.model.Colors;
import cs3500.reversi.model.IDisc;
import cs3500.reversi.model.Reversi;

/**
 * Class for the TextualReversiView class.
 * Represents a textual view of the game of Reversi.
 */
public class TextualReversiView implements ReversiView {
  private final Reversi model;

  /**
   * Constructor for the class.
   * @param model takes in a model to display
   */
  public TextualReversiView(Reversi model) {
    this.model = model;

  }

  private String addSpaces(int numSpaces, List<IDisc> row) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < numSpaces; i++) {
      sb.append(" ");
    }
    for (IDisc disc : row) {
      if (disc != null) {
        if (disc.getColor() == Colors.GRAY) {
          sb.append("_ ");
        }
        if (disc.getColor() == Colors.WHITE) {
          sb.append("0 ");
        }
        if (disc.getColor() == Colors.BLACK) {
          sb.append("X ");
        }
      }

    }
    for (int i = 0; i < numSpaces; i++) {
      sb.append(" ");
    }
    return sb.toString();

  }

  /**
   * Method to render the game.
   *
   * @return String representation of the game
   */
  public String toString() {
    StringBuilder sb = new StringBuilder();
    if (model.getTurn() == Colors.BLACK) {
      sb.append("Current turn is " + Colors.WHITE + "\n");
    } else {
      sb.append("Current turn is " + Colors.BLACK + "\n");
    }

    int numSpaces = model.getLength() - 1;
    int index = 0;
    while (index < model.getCurrBoard().size() / 2) {
      List<IDisc> row = model.getCurrBoard().get(index);
      String newRow = addSpaces(numSpaces, row);
      sb.append(newRow + "\n");
      numSpaces--;
      index++;
    }

    int numBottomSpaces = 0;

    while (index < model.getCurrBoard().size()) {
      List<IDisc> row = model.getCurrBoard().get(index);
      String newRow = addSpaces(numBottomSpaces, row);
      sb.append(newRow + "\n");
      numBottomSpaces++;
      index++;
    }

    return sb.toString();
  }
}
