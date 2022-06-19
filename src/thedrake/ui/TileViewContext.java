package thedrake.ui;

import thedrake.Move;

public interface TileViewContext {

  void tileViewSelected(UnitView unitView);

  void executeMove(Move move);

}
