package thedrake;

import java.io.PrintWriter;
import java.util.List;

public class Troop implements JSONSerializable {
  private final String name;
  private final Offset2D aversPivot;
  private final Offset2D reversPivot;
  private final List<TroopAction> aversActions;
  private final List<TroopAction> reversActions;

  public Troop(String name, Offset2D aversPivot, Offset2D reversPivot, List<TroopAction> aversActions, List<TroopAction> reversActions) {
    this.name = name;
    this.aversPivot = aversPivot;
    this.reversPivot = reversPivot;
    this.aversActions = aversActions;
    this.reversActions = reversActions;
  }

  public Troop(String name, Offset2D pivot, List<TroopAction> aversActions, List<TroopAction> reversActions) {
    this.name = name;
    this.aversPivot = pivot;
    this.reversPivot = pivot;
    this.aversActions = aversActions;
    this.reversActions = reversActions;
  }

  public Troop(String name, List<TroopAction> aversActions, List<TroopAction> reversActions) {
    this.name = name;
    aversPivot = new Offset2D(1, 1);
    reversPivot = new Offset2D(1, 1);
    this.aversActions = aversActions;
    this.reversActions = reversActions;
  }

  @Override
  public void toJSON(PrintWriter writer) {
    writer.write("\"" + name + "\"");
    

  }

  public String name() { return this.name; }

  public Offset2D pivot(TroopFace face) {
    return face == TroopFace.AVERS ? aversPivot : reversPivot;
  }

  public List<TroopAction> actions(TroopFace face) {
    return face == TroopFace.AVERS ? aversActions : reversActions;
  }
}