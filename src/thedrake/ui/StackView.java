package thedrake.ui;

import thedrake.PlayingSide;
import thedrake.Tile;

public class StackView extends UnitView {

  private final StackContext stackContext;

  private final PlayingSide playingSide;

  public PlayingSide getPlayingSide() {
    return playingSide;
  }

  public StackView(Tile tile, TileViewContext tileViewContext, StackContext stackContext, PlayingSide playingSide) {
    super(tile, tileViewContext);
    this.stackContext = stackContext;
    this.playingSide = playingSide;
    update();

    setOnMouseClicked(e -> onClick());

  }

  public void onClick() {
    if (tile.hasTroop()) {
      select();
    }
  }

  @Override
  public boolean canHaveActions() {
    return stackContext.canBePlacedFromStack(this);
  }
}
