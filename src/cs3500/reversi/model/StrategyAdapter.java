package cs3500.reversi.model;

import java.util.Optional;

import cs3500.reversi.provider.model.BoardPosn;
import cs3500.reversi.provider.model.ReadOnlyReversiModel;
import cs3500.reversi.provider.strategy.FallibleReversiStrategy;

public class StrategyAdapter extends Strategy implements FallibleReversiStrategy {
  @Override
  public Tuple<Integer, Integer> chooseMove(ReadonlyReversiModel model, Colors turn) {
    return null;
  }

  @Override
  public Optional<BoardPosn> chooseMove(ReadOnlyReversiModel model) {
    return Optional.empty();
  }
}
