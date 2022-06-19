package thedrake;

import java.util.ArrayList;
import java.util.List;

public class SlideAction extends TroopAction{
  public SlideAction(Offset2D offset) {
    super(offset);
  }

  public SlideAction(int offsetX, int offsetY) {
    super(offsetX, offsetY);
  }

  @Override
  public List<Move> movesFrom(BoardPos origin, PlayingSide side, GameState state) {
    List<Move> result = new ArrayList<>();
    Offset2D slideOffset = new Offset2D(offset().x, offset().y);
    TilePos target = origin.stepByPlayingSide(slideOffset, side);
    while (true) {
      if (state.canStep(origin, target)) {
        result.add(new StepOnly(origin, (BoardPos) target));
      } else if (state.canCapture(origin, target)) {
        result.add(new StepAndCapture(origin, (BoardPos) target));
        break;
      }
      else {
        break;
      }
      slideOffset = new Offset2D(slideOffset.x + offset().x, slideOffset.y + offset().y);
      target = origin.stepByPlayingSide(slideOffset, side);
    }
    return result;
  }
}