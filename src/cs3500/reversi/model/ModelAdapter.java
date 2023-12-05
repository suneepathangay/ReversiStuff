package cs3500.reversi.model;


import java.util.List;

import cs3500.reversi.provider.model.BoardPosn;
import cs3500.reversi.provider.model.GameStatus;
import cs3500.reversi.provider.model.IBoard;
import cs3500.reversi.provider.model.ModelStatus;
import cs3500.reversi.provider.model.PlayerColor;
import cs3500.reversi.provider.model.ReadOnlyReversiModel;
import cs3500.reversi.provider.model.ReversiModel;


//readonlyReversi their model
public class ModelAdapter implements ReadOnlyReversiModel {

  //my model
  private ReadonlyReversiModel model;

  public ModelAdapter(ReadonlyReversiModel model){
    this.model=model;
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

    return null;
  }

  @Override
  public PlayerColor getPlayerTurn() throws IllegalStateException {



    return null;
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
    return null;
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
    return 0;
  }

  @Override
  public IBoard getBoard(){
    return null;
  }
}