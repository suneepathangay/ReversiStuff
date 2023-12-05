package cs3500.reversi.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cs3500.reversi.controller.ModelFeatures;
import cs3500.reversi.controller.Player;


/**
 * Represents a basic implementation of the Reversi game.
 */
public class BasicReversi implements Reversi {

  private final int length;
  private final List<Tuple<Integer, Integer>> directions = new ArrayList<>();
  private final Map<Colors, Colors> oppositeColors = new HashMap<>();
  private final List<Move> moves = new ArrayList<>();
  private final List<List<IDisc>> board = new ArrayList<>();
  private Colors turn;
  private boolean isGameStarted;

  private Message currPlayer;

  private List<ModelFeatures> listeners = new ArrayList<>();

  private List<Player> playerListeners = new ArrayList<>();

  /**
   * Constructor for the BasicReversi class.
   *
   * @param length the length of the board
   */
  public BasicReversi(int length) {
    if (length <= 0) {
      throw new IllegalArgumentException("invlaid input");
    }
    this.length = length;
    this.turn = Colors.BLACK;
    oppositeColors.put(Colors.BLACK, Colors.WHITE);
    oppositeColors.put(Colors.WHITE, Colors.BLACK);
    setupGame();
  }

  /**
   * Method to start the game.
   */

  private void setupGame() {
    if (this.isGameStarted) {
      throw new IllegalStateException("game already started");
    }
    this.isGameStarted = true;
    constructBoard();
    initializeDirections();
    placeInitialPieces();
    currPlayer = Message.Player1;
  }

  private boolean checkBoardFull() {
    for (int row = 0; row < board.size(); row++) {
      for (int col = 0; col < board.get(row).size(); col++) {
        IDisc tile = board.get(row).get(col);
        if (tile.getColor() == Colors.GRAY) {
          return false;
        }
      }
    }
    return true;
  }

  private boolean checkForConsecPasses() {
    //maybe keep a private list of moves and check if there are consecutive passes
    for (int index = 1; index < moves.size(); index++) {
      if (moves.get(index) == Move.PASS && moves.get(index - 1) == Move.PASS) {
        return true;
      }
    }
    return false;

  }

  /**
   * Checks if the game is over.
   *
   * @return boolean if game is over
   */
  @Override
  public boolean isGameOver() {
    checkIsGameStarted();
    //checks if the game is over
    //game is over when there are 2 passes or when the board is fill
    Tuple<Integer, Integer> scores = this.getScore();
    //checks if you lost all your pieces
    if (scores.getFirst() == 0 || scores.getSecond() == 0) {
      return true;
    }

    System.out.println("executing");
    return checkBoardFull() || checkForConsecPasses();
    //loop over and check if the board is empty

  }

  /**
   * Method to get score of the game.
   *
   * @return integer of score
   */
  @Override
  public Tuple<Integer, Integer> getScore() {
    checkIsGameStarted();
    int whiteScore = 0;
    int blackScore = 0;
    //gets the score of the game
    for (int i = 0; i < this.board.size(); i++) {
      List<IDisc> row = this.board.get(i);

      for (int j = 0; j < row.size(); j++) {
        IDisc disc = row.get(j);

        if (disc.getColor() == Colors.WHITE) {
          //add these values to the hash

          whiteScore++;

        }
        if (disc.getColor() == Colors.BLACK) {
          blackScore++;
        }
      }
    }

    return new Tuple<Integer, Integer>(whiteScore, blackScore);
  }

  /**
   * Takes in the proper player and places the move there.
   *
   * @param row in the board
   * @param col in the board
   */
  @Override
  public void placeTile(int row, int col, Colors currTurn) {

    notifyGameOver();

    //these are not the indexes they are the new coordiantes in the coordiante system
    Tuple<Integer, Integer> coordinate = new Tuple<Integer, Integer>(row, col);
    List<Tuple<Integer, Integer>> validAxes = getValidAxes(coordinate, currTurn);
    if (validAxes.isEmpty()) {
      throw new IllegalStateException("invalid move");
    }
    Tuple<Integer, Integer> indexes = getIndexs(coordinate);
    int rowIndex = indexes.getFirst();
    int colindex = indexes.getSecond();
    fillTiles(row, col, validAxes);
    this.board.get(rowIndex).set(colindex, new IDiscImpl(currTurn, row, col));
    moves.add(Move.PLAY);
    if (currPlayer == Message.Player1) {
      currPlayer = Message.Player2;
    } else {
      currPlayer = Message.Player1;
    }

    //we added multiple listeners each representing a controller attached to the  model

    //then we can loop over theses listeners and hit a notify method to both listneers
    notifyListeners();
    notifyListenersPlayer(currPlayer);

    setTurn();
    notifyPlayerMove();
  }

  private void notifyListeners() {
    for (ModelFeatures listener : listeners) {
      listener.notifyBoardChanged();
    }
  }

  private void notifyListenersPlayer(Message player) {
    for (ModelFeatures listener : listeners) {
      if (listener.getPlayer() == currPlayer && listener.isHuman()) {
        listener.notifyPlayerChanged(currPlayer);
      }
    }
  }

  @Override
  public void addPlayerListeners(Player player) {
    this.playerListeners.add(player);
  }

  private void notifyPlayerMove() {
    for (Player player : this.playerListeners) {
      if (player.getColor() == turn) {
        player.notifyOfMove();
      }
    }
  }

  private void notifyGameOver(){
    if(this.isGameOver()){
      for(ModelFeatures feature:listeners){
        String winner=getWinner();
        feature.notifyGameOver(winner);
      }
    }
  }

  private String getWinner(){
    StringBuilder message=new StringBuilder();

    Tuple<Integer,Integer> scores=this.getScore();
    if(scores.getFirst()> scores.getSecond()){
      message.append("Player Two Won");
    } else if (scores.getFirst()<scores.getSecond()) {
      message.append("Player One Won");
    }else{
      message.append("Game tied");
    }
    return message.toString();
  }


  @Override
  public void addListeners(ModelFeatures features) {
    this.listeners.add(features);
  }

  @Override
  public void startGame() {


    //set the colors for the listeners
    ModelFeatures listener1 = listeners.get(0);
    ModelFeatures listener2 = listeners.get(1);


    listener1.setPlayer(Message.Player1);
    listener2.setPlayer(Message.Player2);

    listener1.setTitle(Message.Player1);
    listener2.setTitle(Message.Player2);

    notifyListenersPlayer(currPlayer);
    notifyPlayerMove();
  }


  private List<Tuple<Integer, Integer>> getValidAxes(
          Tuple<Integer, Integer> currPos, Colors currTurn) {
    //cur pos is not an index and is instead the custom coordinate
    List<Tuple<Integer, Integer>> validDirections = new ArrayList<>();

    for (Tuple<Integer, Integer> direction : directions) {

      int coorRowInc = direction.getFirst();
      int coorColInc = direction.getSecond();

      int orgCoorRowPos = currPos.getFirst();
      int orgCoorColPos = currPos.getSecond();

      boolean isValid = true;
      int colorCount = 0;

      while (isValid) {
        orgCoorRowPos += coorRowInc;
        orgCoorColPos += coorColInc;

        Tuple<Integer, Integer> indexes = getIndexs(
                new Tuple<Integer, Integer>(orgCoorRowPos, orgCoorColPos));
        if (indexes == null) {
          break;
        }

        int row = indexes.getFirst();
        int col = indexes.getSecond();


        if (row < board.size() && row >= 0) {
          if (col < board.get(indexes.getFirst()).size() && col >= 0) {
            IDisc tile = this.board.get(row).get(col);
            if (tile.getColor() == Colors.GRAY) {
              break;
            }
            if (tile.getColor() == oppositeColors.get(currTurn)) {
              colorCount++;
            }
            if (colorCount > 0) {
              if (tile.getColor() == currTurn) {
                validDirections.add(direction);
              }
            }
          } else {
            break;
          }
        } else {
          break;
        }
      }
    }
    return validDirections;
  }

  private void fillTiles(int row, int col, List<Tuple<Integer, Integer>> validAxes) {


    for (Tuple<Integer, Integer> validAxe : validAxes) {

      boolean isValid = true;
      //row and col are not indexes but my coordinate system
      int orgRow = row;
      int orgCol = col;

      int rowInc = validAxe.getFirst();
      int colInc = validAxe.getSecond();

      while (isValid) {
        orgRow += rowInc;
        orgCol += colInc;

        Tuple<Integer, Integer> indexes = getIndexs(new Tuple<Integer, Integer>(orgRow, orgCol));
        if (indexes == null) {
          break;
        }
        int rowIndex = indexes.getFirst();
        int colIndex = indexes.getSecond();
        if (rowIndex < board.size() && rowIndex >= 0) {
          if (colIndex < board.get(rowIndex).size() && colIndex >= 0) {
            IDisc tile = this.board.get(rowIndex).get(colIndex);
            if (tile.getColor() == turn) {
              break;
            } else {
              if (tile.getColor() == oppositeColors.get(turn)) {
                this.board.get(rowIndex).set(colIndex, new IDiscImpl(turn, orgRow, orgCol));
              }
            }
          }
        }
      }
    }
  }

  private boolean checkPossibleMovesColor(Colors turn) {
    for (int row = 0; row < board.size(); row++) {
      for (int col = 0; col < board.get(row).size(); col++) {
        IDisc tile = board.get(row).get(col);
        if (tile.getColor() == Colors.GRAY) {
          int rowCoor = tile.getRow();
          int colCoor = tile.getCol();

          Tuple<Integer, Integer> coordinate = new Tuple<Integer, Integer>(rowCoor, colCoor);

          List<Tuple<Integer, Integer>> validAxe = getValidAxes(coordinate, turn);
          if (validAxe.isEmpty()) {
            return false;
          }

        }
      }

    }
    return true;
  }

  /**
   * Passes the move to the next player.
   */
  @Override
  public void passMove() {
    checkIsGameStarted();
    notifyGameOver();
    if (checkPossibleMovesColor(turn)) {
      throw new IllegalStateException("you cant pass the move");
    }
    setTurn();
    moves.add(Move.PASS);

    if (currPlayer == Message.Player1) {
      currPlayer = Message.Player2;
    } else {
      currPlayer = Message.Player1;
    }
    notifyListenersPlayer(currPlayer);
    notifyPlayerMove();
  }

  @Override
  public void setTileColor(int row, int column, Colors color) {
    checkIsGameStarted();
    if (row < 0 || column < 0) {
      throw new IllegalArgumentException("invalid input");
    }
    Tuple<Integer, Integer> indexes = getIndexs(new Tuple<Integer, Integer>(row, column));
    if (indexes == null) {
      throw new IllegalArgumentException("invalid input");
    }
    int rowIndex = indexes.getFirst();
    int colIndex = indexes.getSecond();
    this.board.get(rowIndex).set(colIndex, new IDiscImpl(color, row, column));
  }


  /**
   * Gets the current turn.
   *
   * @return the color of the player whose turn it is
   */
  @Override
  public Colors getTurn() {
    checkIsGameStarted();

    return this.turn;
  }

  /**
   * Gets the Array of Array representation of the board.
   *
   * @return a board.
   */
  @Override
  public Integer getLength() {
    checkIsGameStarted();
    return this.length;
  }

  /**
   * Gets the Array of Array representation of the board.
   *
   * @return a board.
   */
  @Override
  public List<List<IDisc>> getBoard() {
    checkIsGameStarted();
    List<List<IDisc>> newBoard = new ArrayList<>();

    for (int i = 0; i < board.size(); i++) {
      List<IDisc> newRow = new ArrayList<>();
      for (int j = 0; j < board.get(i).size(); j++) {
        IDisc disc = board.get(i).get(j);
        newRow.add(disc);
      }
      newBoard.add(newRow);
    }
    return newBoard;

  }

  /**
   * Returns the number of rows in the board.
   *
   * @return the number of rows in the board
   */
  @Override
  public Integer getNumRows() {
    return this.board.size();
  }

  /**
   * Gets the indexs of the tile in the board.
   *
   * @param coordinates the coordinates of the tile
   * @return the row and column index pairs of the coordinates
   */
  @Override
  public Tuple<Integer, Integer> getIndexs(Tuple<Integer, Integer> coordinates) {
    checkIsGameStarted();
    for (int row = 0; row < board.size(); row++) {
      for (int col = 0; col < board.get(row).size(); col++) {
        IDisc tile = board.get(row).get(col);
        if (tile.getRow() == coordinates.getFirst() && tile.getCol() == coordinates.getSecond()) {
          return new Tuple<Integer, Integer>(row, col);
        }
      }
    }
    return null;
  }

  /**
   * Gets the tile at the given coordinates.
   *
   * @param rowCoor the row coordinate
   * @param colCoor the column coordinate
   * @return the tile at the given coordinates
   */
  @Override
  public IDisc getTileAt(int rowCoor, int colCoor) {
    Tuple<Integer, Integer> coordinates = new Tuple<Integer, Integer>(rowCoor, colCoor);
    Tuple<Integer, Integer> indexes = getIndexs(coordinates);
    if (indexes != null) {
      int row = indexes.getFirst();
      int col = indexes.getSecond();
      return this.board.get(row).get(col);

    }
    return null;
  }

  private void checkIsGameStarted() {
    if (!isGameStarted) {
      throw new IllegalStateException("game is not started");
    }
  }

  private void setTurn() {
    //Class invariant: turn can never be GRAY
    if (this.turn == Colors.GRAY) {
      throw new IllegalStateException("Turn can't be GRAY");
    }
    this.turn = oppositeColors.get(this.turn);
  }

  private void initializeDirections() {
    this.directions.add(new Tuple<Integer, Integer>(0, 1));
    this.directions.add(new Tuple<Integer, Integer>(-1, 0));
    this.directions.add(new Tuple<Integer, Integer>(1, 0));
    this.directions.add(new Tuple<Integer, Integer>(1, 1));
    this.directions.add(new Tuple<Integer, Integer>(-1, -1));
    this.directions.add(new Tuple<Integer, Integer>(0, -1));
  }

  private List<IDisc> fillBoard(Integer rowLength) {
    List<IDisc> row = new ArrayList<>();
    for (int i = 0; i < rowLength; i++) {
      row.add(null);
    }
    return row;
  }

  private void constructBoard() {
    //add arrays to the board of the corresponding size in loops
    int rowTracker = 0;

    for (int i = length; i < length * 2; i++) {

      int row = i - length;

      List<IDisc> topRow = fillBoard(i);
      for (int j = 0; j < topRow.size(); j++) {
        topRow.set(j, new IDiscImpl(Colors.GRAY, row, j));
      }
      board.add(topRow);
      rowTracker++;
      if (i == (length * 2) - 1) {
        int columnTracker = 1;

        for (int j = i - 1; j >= length; j--) {
          List<IDisc> bottomRow = fillBoard(j);
          for (int a = 0; a < bottomRow.size(); a++) {
            bottomRow.set(a, new IDiscImpl(Colors.GRAY, rowTracker, a + columnTracker));
          }
          columnTracker++;
          rowTracker++;
          board.add(bottomRow);
        }
      }

    }
  }

  /**
   * Gets a list of coordinates on the board.
   *
   * @return list of coordinates on the board
   */
  @Override
  public List<Tuple<Integer, Integer>> getCoordinates() {
    List<Tuple<Integer, Integer>> boardCoordinates = new ArrayList<>();

    for (int row = 0; row < board.size(); row++) {
      for (int col = 0; col < board.get(row).size(); col++) {
        IDisc tile = this.board.get(row).get(col);
        Tuple<Integer, Integer> coordinate =
                new Tuple<Integer, Integer>(tile.getRow(), tile.getCol());
        boardCoordinates.add(coordinate);
      }

    }
    return boardCoordinates;
  }


  private void placeInitialPieces() {


    Colors currColor = Colors.BLACK;

    Tuple<Integer, Integer> center = new Tuple<Integer, Integer>(length - 1, length - 1);

    for (Tuple<Integer, Integer> direction : directions) {
      int rowIncrement = direction.getFirst();
      int colIncrement = direction.getSecond();

      int newTileRow = center.getFirst() + rowIncrement;
      int newTileCol = center.getSecond() + colIncrement;

      for (int row = 0; row < board.size(); row++) {
        for (int col = 0; col < board.get(row).size(); col++) {
          IDisc tile = this.board.get(row).get(col);
          if (tile.getRow() == newTileRow && tile.getCol() == newTileCol) {
            this.board.get(row).set(col, new IDiscImpl(currColor, tile.getRow(), tile.getCol()));
            currColor = oppositeColors.get(currColor);
          }
        }
      }
    }
  }
}
