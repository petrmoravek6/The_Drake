package thedrake;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class TroopTile implements Tile, JSONSerializable{
  private final Troop troop;
  private final PlayingSide side;
  private final TroopFace face;

  public TroopTile(Troop troop, PlayingSide side, TroopFace face) {
    this.troop = troop;
    this.side = side;
    this.face = face;
  }

  public Troop troop() { return troop; }

  public PlayingSide side() { return side; }

  public TroopFace face() { return face; }

  @Override
  public boolean canStepOn() { return false; }

  @Override
  public boolean hasTroop() { return true; }

  public TroopTile flipped() {
    return new TroopTile(this.troop, this.side, this.face == TroopFace.AVERS ? TroopFace.REVERS : TroopFace.AVERS);
  }

  @Override
  public List<Move> movesFrom(BoardPos pos, GameState state) {
    List<Move> result = new ArrayList<>();
    for (TroopAction ta : troop().actions(face)) {
      result.addAll(ta.movesFrom(pos, side(), state));
    }
    return result;
  }

  @Override
  public void toJSON(PrintWriter writer) {
    writer.write("{\"troop\":");
    troop().toJSON(writer);
    writer.write(",\"side\":");
    side().toJSON(writer);
    writer.write(",\"face\":");
    face().toJSON(writer);
    writer.write("}");
  }
}
