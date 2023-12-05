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

  private Map<Tuple<Integer,Integer>,BoardPosn> ourToProv=new HashMap<>();

  private Map<BoardPosn,Tuple<Integer,Integer>> provToOur=new HashMap<>();

  public ModelAdapter(ReadonlyReversiModel model){
    this.model=model;
    convertCoor();
  }

  //TODO fix the method to change from their coordainte to the our coordinate
  private void convertCoor(){

    Map<Tuple<Integer,Integer>,BoardPosn> mapCoord=new HashMap<>();

    int rowStart=this.getBoardSize()/2;
    int colStart=0;

    List<BoardPosn> providerCoord=new ArrayList<>();

    for(int i=0; i<(this.getBoardSize()/2)+1; i++){
      List<BoardPosn> row=new ArrayList<>();
      for(int j=rowStart; j<this.getBoardSize(); j++){
        BoardPosn pos=new BoardPosn(j,colStart);
        providerCoord.add(pos);
      }

      colStart++;
      rowStart--;
    }
    //rowStart is now 0
    //colStart is now 3
    int newStart=getBoardSize()-1;

    for(int i=0; i<this.getBoardSize()/2; i++){
      List<BoardPosn> row=new ArrayList<>();
      for(int j=rowStart; j<newStart; j++){
        BoardPosn pos=new BoardPosn(j,colStart);
        providerCoord.add(pos);
      }
      colStart++;
      newStart--;
    }

    List<Tuple<Integer,Integer>> ourCoord=this.model.getCoordinates();

    for(int i=0; i<ourCoord.size(); i++){
      BoardPosn proCoord=providerCoord.get(i);
      Tuple<Integer,Integer> ourCoor=ourCoord.get(i);
      ourToProv.put(ourCoor,proCoord);
      provToOur.put(proCoord,ourCoor);
    }
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
    List<BoardPosn> provCoor=new ArrayList<>();
    for(Tuple<Integer,Integer> piece:pieces){
      BoardPosn coor=ourToProv.get(piece);
      provCoor.add(coor);

    }

    return provCoor;
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
    if(this.getWinner()==PlayerColor.BLACK || this.getWinner()==PlayerColor.WHITE){
      return GameStatus.WON;
    }
    if(this.getWinner()==null){
      return GameStatus.STALEMATE;
    }


    return GameStatus.PLAYING;
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
      return null;
    }
    return winner;
  }

  @Override
  public PlayerColor getPlayerAt(BoardPosn posn) throws IllegalArgumentException {

    Tuple<Integer,Integer> coor=provToOur.get(posn);
    Colors color=this.model.getTileAt(coor.getFirst(), coor.getSecond()).getColor();
    if(color==Colors.BLACK){
      return PlayerColor.BLACK;
    }
    if(color==Colors.WHITE){
      return PlayerColor.WHITE;
    }
    return null;
  }

  @Override
  public boolean isValidMove(BoardPosn posn) {
    Tuple<Integer,Integer> coor=provToOur.get(posn);
    return this.model.isValidMove(coor);

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
}