package thedrake.ui;

import javafx.scene.image.ImageView;
import thedrake.BoardPos;
import thedrake.Move;
import thedrake.Tile;

public class TileView extends UnitView {

    private final BoardPos boardPos;

    private Move move;

    private final ImageView moveImage;

    public TileView(BoardPos boardPos, Tile tile, TileViewContext tileViewContext) {
        super(tile, tileViewContext);
        this.boardPos = boardPos;

        update();

        setOnMouseClicked(e -> onClick());

        moveImage = new ImageView(getClass().getResource("/assets/move.png").toString());
        moveImage.setVisible(false);
        moveImage.setPreserveRatio(true);
        getChildren().add(moveImage);
    }

    private void onClick() {
        if (move != null)
            tileViewContext.executeMove(move);
        else if (tile.hasTroop())
            select();
    }

    public void setMove(Move move) {
        this.move = move;
        moveImage.setVisible(true);

    }

    public void clearMove() {
        this.move = null;
        moveImage.setVisible(false);
    }

    public BoardPos position() {
        return boardPos;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }

    @Override
    public boolean canHaveActions() {
        return true;
    }
}
