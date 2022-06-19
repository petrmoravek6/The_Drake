package thedrake.ui;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

import static thedrake.ui.NewGameState.DefaultGameState;

public class GameOverPopup extends VBox{

  public GameOverPopup(String result, String endMessage) {

    setMaxSize(420, 420);
    setPrefSize(getMaxWidth(), getMaxHeight());

    Label gameOverLabel = new Label("Konec hry");
    Label resultLabel = new Label(result);
    Label endMessageLabel = new Label(endMessage);


    Button newGameButton = new Button("Nová hra");
    Button backToMainMenuButton = new Button("Zpět do hlavního menu");
    Button stayInGameButton = new Button("Zůstat ve hře");

    gameOverLabel.setId("GameOverLabel");
    resultLabel.setId("ResultLabel");
    endMessageLabel.setId("EndMessageLabel");
    newGameButton.getStyleClass().add("GameOverScreenButtons");
    backToMainMenuButton.getStyleClass().add("GameOverScreenButtons");
    stayInGameButton.getStyleClass().add("GameOverScreenButtons");

    newGameButton.setOnMouseClicked(e -> newGame());
    backToMainMenuButton.setOnMouseClicked(e -> backToMainMenu());
    stayInGameButton.setOnMouseClicked(e -> this.setVisible(false));

    setId("GameOverPopup");
    setAlignment(Pos.CENTER);
    setMargin(endMessageLabel, new Insets(0, 0, 25, 0));
    getChildren().addAll(gameOverLabel, resultLabel, endMessageLabel, newGameButton, backToMainMenuButton, stayInGameButton);
  }

  public void newGame() {
    Stage stage = (Stage) getScene().getWindow();
    GameView gameView = new GameView(DefaultGameState());
    gameView.setId("GameView");
    Scene gameScene = new Scene(gameView);
    gameScene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
    stage.setScene(gameScene);
    stage.setMinWidth(800);
    stage.setMinHeight(1000);
    stage.setFullScreen(true);
  }
  public void backToMainMenu() {
    FXMLLoader fxmlLoader = new FXMLLoader(TheDrakeApp.class.getResource("theDrake.fxml"));
    Scene mainMenu = null;
    try {
      mainMenu = new Scene(fxmlLoader.load(), 1920, 1080);
    } catch (IOException e) {
      e.printStackTrace();
    }
    mainMenu.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
    Stage stage = (Stage) getScene().getWindow();
    boolean fullScreenOn = stage.isFullScreen();
    stage.setTitle("The Drake");
    stage.setScene(mainMenu);
    stage.setWidth(1920);
    stage.setHeight(1080);
    stage.setFullScreen(fullScreenOn);
    stage.show();
  }

}
