package thedrake.ui;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import thedrake.*;


public class StackOfUnits extends GridPane implements StackContext {

  private final TileViewContext context;
  private final PlayingSide playingSide;

  public StackOfUnits(PlayingSide playingSide, TileViewContext context) {

    this.context = context;
    this.playingSide = playingSide;

    setHgap(5);
    setVgap(5);
    setPadding(new Insets(15, 0, 15, 0));
    setAlignment(Pos.CENTER);

    StandardDrakeSetup sds = new StandardDrakeSetup();
    add(new StackView(new TroopTile(sds.DRAKE, playingSide, TroopFace.AVERS), context, this, playingSide), 0, 0);
    add(new StackView(new TroopTile(sds.CLUBMAN, playingSide, TroopFace.AVERS), context, this , playingSide), 1, 0);
    add(new StackView(new TroopTile(sds.CLUBMAN, playingSide, TroopFace.AVERS), context, this , playingSide), 2, 0);
    add(new StackView(new TroopTile(sds.MONK, playingSide, TroopFace.AVERS), context, this , playingSide), 3, 0);
    add(new StackView(new TroopTile(sds.SPEARMAN, playingSide, TroopFace.AVERS), context, this , playingSide), 4, 0);
    add(new StackView(new TroopTile(sds.SWORDSMAN, playingSide, TroopFace.AVERS), context, this , playingSide), 5, 0);
    add(new StackView(new TroopTile(sds.ARCHER, playingSide, TroopFace.AVERS), context, this , playingSide), 6, 0);
  }


  @Override
  public boolean canBePlacedFromStack(StackView stackView) {
    return getColumnIndex(stackView) == 0 && getRowIndex(stackView) == 0 && stackView.tile.hasTroop();
  }

  public void update(GameState state) {

    int cntr = 0;
    for (Troop troop : state.armyNotOnTurn().stack()) {
      add(new StackView(new TroopTile(troop, playingSide, TroopFace.AVERS), context, this, playingSide), cntr, 0);
      cntr++;
    }
    for(int i = 6; i >= state.armyNotOnTurn().stack().size(); i--) {
      add(new StackView(BoardTile.EMPTY, context, this, playingSide), i, 0);
    }
  }
}
