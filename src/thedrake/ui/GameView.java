package thedrake.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import thedrake.*;

import java.util.List;

public class GameView extends BorderPane implements TileViewContext {

  private GameState gameState;
  private final BoardView boardView;
  private final StackOfUnits blueStack;
  private final StackOfUnits orangeStack;
  private UnitView selected;
  private ValidMoves validMoves;
  private final Label orangeCaptured;
  private final Label blueCaptured;
  private final Label playerOnTurn;
  private final StackPane stackPane;

  public GameView(GameState gameState) {
    this.gameState = gameState;
    this.validMoves = new ValidMoves(gameState);
    this.boardView = new BoardView(gameState, this);
    this.blueStack = new StackOfUnits(PlayingSide.BLUE, this);
    this.orangeStack = new StackOfUnits(PlayingSide.ORANGE, this);
    selected = null;


    orangeCaptured = new Label("ZAJATÉ JEDNOTKY: ŽÁDNÉ");
    blueCaptured = new Label("ZAJATÉ JEDNOTKY: ŽÁDNÉ");
    playerOnTurn = new Label();
    playerOnTurn.getStyleClass().add("PlayerOnTurn");
    updatePlayingSide();
    orangeCaptured.setPrefSize(730, 50);
    blueCaptured.setPrefSize(730, 50);
    playerOnTurn.setPrefSize(450, 65);
    blueCaptured.setId("CapturedLabelBlue");
    orangeCaptured.setId("CapturedLabelOrange");

    Button exitButton1 = new ExitButton(this);
    Button exitButton2 = new ExitButton(this);

    HBox middleBox = new HBox(exitButton1, boardView, exitButton2);
    middleBox.setAlignment(Pos.CENTER);
    middleBox.setSpacing(30);
    middleBox.setPadding(new Insets(0,10,0,10));
    this.stackPane = new StackPane(middleBox);
    VBox upperVBox = new VBox(orangeCaptured, orangeStack);
    upperVBox.setAlignment(Pos.CENTER);
    upperVBox.setPadding(new Insets(30, 10, 0, 10));
    VBox lowerVBox = new VBox(blueStack, blueCaptured, playerOnTurn);
    lowerVBox.setAlignment(Pos.CENTER);
    lowerVBox.setPadding(new Insets(0, 10, 30, 10));
    VBox.setMargin(playerOnTurn, new Insets(55, 0, 0, 0));
    setCenter(stackPane);
    setTop(upperVBox);
    setBottom(lowerVBox);

  }

  @Override
  public void tileViewSelected(UnitView unitView) {
    if (selected != null)
      selected.unselect();
    if (selected == unitView) {
      selected = null;
      clearMoves();
      return;
    }

    selected = unitView;

    clearMoves();
    if(unitView.canHaveActions()) {
      if (unitView instanceof TileView) {
        showMoves(validMoves.boardMoves(((TileView) unitView).position()));
      } else if (((StackView)unitView).getPlayingSide() == gameState.sideOnTurn()){
        showMoves(validMoves.movesFromStack());
      }
    }
  }

  private void clearMoves() {
    for (Node node : boardView.getChildren()) {
      TileView tileView = (TileView) node;
      tileView.clearMove();
    }
  }

  private void showMoves(List<Move> moveList) {
    for (Move move : moveList) {
      tileViewAt(move.target()).setMove(move);
    }
  }

  private TileView tileViewAt(BoardPos target) {
    int index = (3 - target.j()) * 4 + target.i();
    return (TileView) boardView.getChildren().get(index);
  }

  @Override
  public void executeMove(Move move) {
    boolean isMoveFromStack = false;
    PlayingSide stackUnitPlayingSide = PlayingSide.BLUE;
    if (selected instanceof StackView) {
      isMoveFromStack = true;
      stackUnitPlayingSide = ((StackView)selected).getPlayingSide();
    }
    selected.unselect();
    selected = null;
    clearMoves();
    gameState = move.execute(gameState);
    validMoves = new ValidMoves(gameState);
    updateTiles(isMoveFromStack, stackUnitPlayingSide);
    updateCaptured(PlayingSide.BLUE);
    updateCaptured(PlayingSide.ORANGE);
    if(! isVictory()) {
      updatePlayingSide();
      checkPossibleMoves();
    }
  }

  private void updateTiles(boolean isMoveFromStack, PlayingSide stackUnitPlayingSide) {
    for (Node node : boardView.getChildren()) {
      TileView tileView = (TileView) node;
      tileView.setTile(gameState.tileAt(tileView.position()));
      tileView.update();
    }
    if(isMoveFromStack){
      if(stackUnitPlayingSide == PlayingSide.BLUE) {
        blueStack.update(gameState);
      }
      else {
        orangeStack.update(gameState);
      }
    }
  }

  private void updateCaptured(PlayingSide playingSide) {
    if(gameState.army(playingSide).captured().isEmpty()) {
      return;
    }
    String text = "ZAJATÉ JEDNOTKY: ";
    int size = gameState.army(playingSide).captured().size();
    for(int i = 0; i < size - 1; i++) {
      text += gameState.army(playingSide).captured().get(i).name() + ", ";
    }
    text += gameState.army(playingSide).captured().get(size - 1).name();
    if (playingSide == PlayingSide.BLUE) {
      blueCaptured.setText(text);
    }
    else {
      orangeCaptured.setText(text);
    }
  }

  private void updatePlayingSide() {
    if(gameState.sideOnTurn() == PlayingSide.BLUE) {
      playerOnTurn.setText("Modrý hráč je na tahu");
      playerOnTurn.setId("PlayerOnTurnBlue");
    }
    else {
      playerOnTurn.setText("Oranžový hráč je na tahu");
      playerOnTurn.setId("PlayerOnTurnOrange");
    }
  }

  private boolean isVictory() {
    if (gameState.result() == GameResult.VICTORY) {
      String winner;
      String loser;
      if (gameState.sideOnTurn() == PlayingSide.ORANGE) {
        winner = "Modrý";
        loser = "Oranžový";
      }
      else {
        winner = "Oranžový";
        loser = "Modrý";
      }
      String result = winner + " hráč vítězí";
      String endMessage = loser + " Drake byl zneškodněn";
      gameOver(result, endMessage);
      return true;
    }
    return false;
  }

  private void checkPossibleMoves() {
    if (validMoves.allMoves().isEmpty()) {
      gameState = gameState.resign();
      String winner;
      String loser;
      if (gameState.sideOnTurn() == PlayingSide.BLUE) {
        winner = "Modrý";
        loser = "Oranžový";
      }
      else {
        winner = "Oranžový";
        loser = "Modrý";
      }
      String endMessage = loser + " hráč je zablokován, nemá možnost táhnout";
      String result = winner + " hráč vítězí";
      gameOver(result, endMessage);
    }
  }

  private void gameOver(String result, String endMessage) {
    validMoves = new ValidMoves(gameState);
    playerOnTurn.setVisible(false);
    GameOverPopup gameOverPopup = new GameOverPopup(result, endMessage);
    stackPane.getChildren().add(gameOverPopup);
  }

  protected void draw() {
    gameState = gameState.draw();
    gameOver("Remíza", "");
  }

  protected GameResult getActualState() {
    return gameState.result();
  }
}
