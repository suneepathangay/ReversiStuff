package cs3500.reversi.model;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cs3500.reversi.provider.model.BoardPosn;
import cs3500.reversi.provider.model.GameStatus;
import cs3500.reversi.provider.model.IBoard;
import cs3500.reversi.provider.model.ModelStatus;
import cs3500.reversi.provider.model.PlayerColor;
import cs3500.reversi.provider.model.ReadOnlyReversiModel;



//TODO figure out how to convert thier coordiantes to my coordinaes

//readonlyReversi their model
public class ModelAdapter implements ReadOnlyReversiModel {

  //my model
  private ReadonlyReversiModel model;

  public ModelAdapter(ReadonlyReversiModel model){
    this.model=model;
  }

  //TODO fix the method to change from their coordainte to the our coordinate
  public Tuple<Integer,Integer> convertProviderCoord(BoardPosn coor){
      int startIndex=this.getBoardSize()/2;
      int startCol=0;
      //creating the first half of the board

    List<List<BoardPosn>> board=new ArrayList<>();

    for(int i=0; i<startIndex; i++){
      List<BoardPosn> row=new ArrayList<>();
      for(int j=startIndex; i<this.getBoardSize(); j++){
        //j is the row
        BoardPosn pos=new BoardPosn(j,startCol);
        row.add(pos);
      }
      board.add(row);
      startCol++;
    }

    for(int i=0; i<startIndex; i++){

    }


    return null;
  }

  @Override
  public int getNumCapturedHexagons(PlayerColor playerColor) throws IllegalArgumentException {
    //delegate the call to my model
    Colors color;
    int count=0;

    if(playerColor==PlayerColor.BLACK){
      color=Colors.BLACK;
    }else{
      color=Colors.WHITE;
    }
    for(int row=0; row<model.getCurrBoard().size(); row++){
      List<IDisc> currRow=model.getCurrBoard().get(row);
      for(int col=0; col< currRow.size(); col++){
        IDisc disc=currRow.get(col);
        if(disc.getColor()==color){
          count++;
        }
      }
    }
    return count;
  }

  @Override
  public List<BoardPosn> getCapturedHexagons(PlayerColor playerColor) throws IllegalArgumentException {
    //
    Colors color;
    if(playerColor==PlayerColor.BLACK){
      color=Colors.BLACK;
    }else{
      color=Colors.WHITE;
    }


    List<Tuple<Integer,Integer>> pieces=new ArrayList<>();
    for(int row=0; row< model.getNumRows(); row++){
      List<IDisc> currRow=model.getCurrBoard().get(row);
      for(int col=0; col<currRow.size(); col++){
        IDisc disc=currRow.get(col);
        if(disc.getColor()==color) {
          int x = disc.getRow();
          int y = disc.getCol();
          Tuple<Integer, Integer> coordinate = new Tuple<>(x, y);
          pieces.add(coordinate);
        }
      }
    }
    //convert my coordinate to their coordinate system


    return null;
  }

  @Override
  public PlayerColor getPlayerTurn() throws IllegalStateException {
    PlayerColor color;
    if(model.getTurn()==Colors.BLACK){
      color=PlayerColor.BLACK;
    }else{
      color=PlayerColor.WHITE;
    }

    return color;
  }

  @Override
  public List<BoardPosn> getPossibleMoves() {
    return null;
  }

  @Override
  public GameStatus getGameStatus() {



    return null;
  }

  @Override
  public PlayerColor getWinner() {
    PlayerColor winner;
    Tuple<Integer,Integer> scores=model.getScore();
    if(scores.getFirst()> scores.getSecond()){
      winner=PlayerColor.WHITE;
    } else if (scores.getFirst()<scores.getSecond()) {
      winner=PlayerColor.BLACK;
    }else{
      throw new IllegalStateException("no winner");
    }
    return winner;
  }

  @Override
  public PlayerColor getPlayerAt(BoardPosn posn) throws IllegalArgumentException {
    return null;
  }

  @Override
  public boolean isValidMove(BoardPosn posn) {
    return false;
  }

  @Override
  public List<BoardPosn> getPotentialCapturedCellsForMove(BoardPosn posn) {
    return null;
  }

  @Override
  public void addModelListener(ModelStatus listener) {

  }

  @Override
  public int getBoardSize() {
    return model.getLength();
  }

  //TODO
  @Override
  public IBoard getBoard(){
    return null;
  }

  public Tuple<Integer, Integer> boardPosnToTuple(BoardPosn posn) {
    Map<BoardPosn, Tuple<Integer, Integer>> hexGridMap = mapBoardPosnBoardtoRowColBoard();
    return hexGridMap.get(posn);
  }


  private Map<BoardPosn, Tuple<Integer, Integer>>  mapBoardPosnBoardtoRowColBoard() {
    int diameter = model.getNumRows();
    List<Tuple<Integer, Integer>> rowColList = generateRowColCoordinates(diameter);
    List<BoardPosn> cubeList = generateBoardPosnCoordinates(diameter);

    Map<BoardPosn, Tuple<Integer, Integer>> hexGridMap = new HashMap<>();
    for (int i = 0; i < rowColList.size(); i++) {
      hexGridMap.put( cubeList.get(i), rowColList.get(i));
    }

    return hexGridMap;
  }



  private static List<Tuple<Integer, Integer>> generateRowColCoordinates(int diameter) {
    List<Tuple<Integer, Integer>> rowColCoordinates = new ArrayList<>();
    int radius = (diameter - 1) / 2;

    for (int row = 0; row < diameter; row++) {
      int cols = diameter - Math.abs(row - radius);
      for (int col = 0; col < cols; col++) {
        int adjustedCol;
        if (row <= radius) {
          adjustedCol = col;
        } else {
          adjustedCol = col + (row - radius);
        }
        rowColCoordinates.add(new Tuple<>(row, adjustedCol));
      }
    }

    return rowColCoordinates;
  }

  private static List<BoardPosn> generateBoardPosnCoordinates(int diameter) {
    List<BoardPosn> cubeCoordinates = new ArrayList<>();
    int radius = (diameter - 1) / 2;

    for (int r = -radius; r <= radius; r++) {
      for (int q = -radius; q <= radius; q++) {
        int newR = r + radius;
        int newQ = q + radius;
        int s = -r - q;
        if (Math.abs(s) <= radius) {
          int newS = - newQ - newR;
          cubeCoordinates.add(new BoardPosn(newQ, newR, newS));
        }
      }
    }

    return cubeCoordinates;
  }
}