package cs3500.reversi.model;

import java.util.ArrayList;
import java.util.List;


import cs3500.reversi.controller.Converter;
import cs3500.reversi.provider.model.BoardPosn;
import cs3500.reversi.provider.model.GameStatus;
import cs3500.reversi.provider.model.IBoard;
import cs3500.reversi.provider.model.ModelStatus;
import cs3500.reversi.provider.model.PlayerColor;
import cs3500.reversi.provider.model.ReadOnlyReversiModel;
import cs3500.reversi.view.IBoardImpl;


//TODO figure out how to convert thier coordiantes to my coordinaes

//TODO Finish Model Adapter

//TODO Finish the IBoard Implementation

//readonlyReversi their model
public class ModelAdapter implements ReadOnlyReversiModel {

  //my model
  private ReadonlyReversiModel model;

  private Converter converter;
  public ModelAdapter(ReadonlyReversiModel model){
    this.model=model;
    this.converter=new Converter(model);
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
    Colors color;
    List<BoardPosn> posList=new ArrayList<>();

    if(playerColor==PlayerColor.BLACK){
      color=Colors.BLACK;
    }else{
      color=Colors.WHITE;
    }
    List<List<IDisc>> board=model.getCurrBoard();
    for(int row=0; row<board.size(); row++){
      for(int col=0; col<board.get(row).size(); col++){
        IDisc tile=board.get(row).get(col);
        if(tile.getColor()==color){
          int rowCoor= tile.getRow();
          int colCoor=tile.getCol();

          BoardPosn pos=converter.tupleToBoardPosn(new Tuple<>(rowCoor,colCoor));
          posList.add(pos);
        }
      }
    }
    return posList;
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
    List<List<IDisc>> board=model.getCurrBoard();
    List<BoardPosn> validMoves=new ArrayList<>();

    for(int row=0; row<board.size(); row++){
      for(int col=0; col<board.get(row).size(); col++){
        IDisc disc=board.get(row).get(col);
        if(disc.getColor()==Colors.GRAY){
          int rowCoor=disc.getRow();
          int colCoor=disc.getCol();
          Tuple<Integer,Integer> coor=new Tuple<>(rowCoor,colCoor);
          if(model.isValidMove(coor)){
            BoardPosn pos=converter.tupleToBoardPosn(coor);
            validMoves.add(pos);
          }
        }
      }
    }
    return validMoves;
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

    Tuple<Integer,Integer> coor=converter.boardPosntoTuple(posn);
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
    Tuple<Integer,Integer> coor=converter.boardPosntoTuple(posn);
    return this.model.isValidMove(coor);

  }

  //TODO METHOD
  @Override
  public List<BoardPosn> getPotentialCapturedCellsForMove(BoardPosn posn) {
    return null;
  }

  //TODO FIGURE THIS OUT
  @Override
  public void addModelListener(ModelStatus listener) {

  }

  @Override
  public int getBoardSize() {
    return (model.getLength()*2)-1;
  }

  //TODO FIGURE OUT IBOARD ASAP
  @Override
  public IBoard getBoard(){
    IBoardImpl board= new IBoardImpl(model);
    return board;

  }

}