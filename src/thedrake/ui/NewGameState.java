package thedrake.ui;

import thedrake.*;

public class NewGameState {

  public static GameState DefaultGameState() {
    Board board = new Board(4);
    PositionFactory pf = board.positionFactory();
    board = board.withTiles(new Board.TileAt(pf.pos(1, 0), BoardTile.MOUNTAIN));
    board = board.withTiles(new Board.TileAt(pf.pos(2, 3), BoardTile.MOUNTAIN));
    return new StandardDrakeSetup().startState(board);
  }
}
