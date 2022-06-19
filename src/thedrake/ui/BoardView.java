package thedrake.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import thedrake.BoardPos;
import thedrake.GameState;
import thedrake.PositionFactory;

public class BoardView extends GridPane {

    public BoardView(GameState gameState, TileViewContext context) {

        PositionFactory positionFactory = gameState.board().positionFactory();
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                BoardPos boardPos = positionFactory.pos(x, 3 - y);
                add(new TileView(boardPos, gameState.tileAt(boardPos), context), x, y);
            }
        }

        setHgap(5);
        setVgap(5);
        setPadding(new Insets(0,10,0,10));
        setAlignment(Pos.CENTER);
    }







}
