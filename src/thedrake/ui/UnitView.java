package thedrake.ui;

import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import thedrake.Tile;

public abstract class UnitView extends Pane {

  protected Tile tile;

  protected TileBackgrounds backgrounds = new TileBackgrounds();

  protected Border selectBorder = new Border(
          new BorderStroke(Color.WHITESMOKE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3)));

  protected TileViewContext tileViewContext;

  public UnitView(Tile tile, TileViewContext tileViewContext) {
    this.tile = tile;
    this.tileViewContext = tileViewContext;
    setPrefSize(100, 100);
    setMaxSize(100, 100);
    setMinSize(100, 100);
  }

  public void select() {
    setBorder(selectBorder);
    tileViewContext.tileViewSelected(this);
  }

  public void unselect() {
    setBorder(null);
  }

  public void update() {
    setBackground(backgrounds.get(tile));
  }

  public abstract boolean canHaveActions();
}
