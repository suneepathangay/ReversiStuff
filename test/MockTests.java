import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import cs3500.reversi.model.BasicReversi;
import cs3500.reversi.model.CaptureStrategy;
import cs3500.reversi.model.Colors;
import cs3500.reversi.model.Message;
import cs3500.reversi.model.Tuple;


/**
 * Test Class that using Mocks for creating a transcript of startegy 1.
 * Also Tests basic mock functionality for the controller and model.
 */
public class MockTests {


  private StringBuilder mockAppendable;
  private MockModel mockModel;
  private MockController mockController;

  @Test
  public void testStrategy1Transcript() throws FileNotFoundException {
    PrintStream originalOut = System.out;
    FileOutputStream fos = new FileOutputStream("strategy-transcript.txt");
    PrintStream ps = new PrintStream(fos);
    System.setOut(ps);
    System.out.println("Strategy transcript:");

    try {
      CaptureStrategy cs = new CaptureStrategy();
      StringBuilder log = new StringBuilder();
      MockModel mockModel = new MockModel(log, new BasicReversi(4));

      Tuple<Integer, Integer> coordinate = cs.chooseMove(mockModel, Colors.BLACK);
      System.out.println("Chosen move: " + coordinate.getFirst() + " " + coordinate.getSecond());

      Colors turn = mockModel.getTurn();
      mockModel.placeTile(coordinate.getFirst(), coordinate.getSecond(), turn);
      System.out.println(log);

    } finally {
      System.setOut(originalOut);
      ps.close();
    }
    Assert.assertFalse(false);
  }

  @Before
  public void setUp() {
    mockAppendable = new StringBuilder();
    mockModel = new MockModel(mockAppendable, new BasicReversi(4));
    MockFrame mockView = new MockFrame();
    MockPlayer mockPlayer = new MockPlayer(Colors.BLACK);
    mockController = new MockController(mockAppendable, mockModel, mockView, mockPlayer);
  }

  @Test
  public void testMockController() {
    mockController.getPlayer();

    mockModel.placeTile(2, 4, Colors.BLACK);
    Assert.assertEquals(Colors.BLACK,mockModel.getTileAt(2,4));
    mockController.getPlayer();
    mockController.clickTile(2, 4);
    Message player = mockController.getPlayer();
    mockController.notifyPlayerChanged(player);
    mockController.notifyBoardChanged();
    mockController.passMove();
    mockController.setTitle(player);

    String output = mockAppendable.toString();
    System.out.println(output);


  }

  @Test
  public void testMockModel() {
    MockModel mockModel = new MockModel(mockAppendable, new BasicReversi(4));
    mockModel.placeTile(2, 4, Colors.BLACK);
    mockModel.getTurn();
    mockModel.placeTile(5, 4, Colors.WHITE);

    mockModel.getScore();
    mockModel.getTurn();
    mockModel.getCurrBoard();
    mockModel.getTileAt(5, 4);

    String output = mockAppendable.toString();
    System.out.println(output);
    Assert.assertEquals(Colors.WHITE,mockModel.getTileAt(5,4));
  }
}