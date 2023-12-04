import org.junit.Assert;
import org.junit.Test;

import cs3500.reversi.model.BasicReversi;
import cs3500.reversi.model.Colors;
import cs3500.reversi.view.TextualReversiView;

/**
 * Class for the ViewTests class.
 */
public class ViewTests {


  @Test
  public void testView() {
    BasicReversi model = new BasicReversi(6);
    TextualReversiView view = new TextualReversiView(model);
    System.out.println(view);
    //get tile at 5,4 Inital piece
    Assert.assertEquals(model.getBoard().get(5).get(4).getColor(),
            Colors.WHITE);
  }

  @Test
  public void testViewSmaller() {
    BasicReversi model = new BasicReversi(4);
    TextualReversiView view = new TextualReversiView(model);
    System.out.println(view);
    //get tile at 3,2 Inital piece
    Assert.assertEquals(model.getBoard().get(3).get(2).getColor(), Colors.WHITE);
  }
}
