package thedrake.ui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.InputEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

import static thedrake.ui.NewGameState.DefaultGameState;


public class Controller implements Initializable {
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
  }
  @FXML
  private Button mainMenuExitButton;

  @FXML
  private Button mainMenuPlayAgainstAnotherPlayerButton;

  public void onMouseClickExitButton() {
    Stage stage = (Stage) mainMenuExitButton.getScene().getWindow();
    stage.close();
  }

  public void onMouseClickPlayAgainstAnotherPlayerButton() {
    Stage stage = (Stage) mainMenuPlayAgainstAnotherPlayerButton.getScene().getWindow();
    GameView gameView = new GameView(DefaultGameState());
    gameView.setId("GameView");
    Scene gameScene = new Scene(gameView);
    gameScene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
    stage.setScene(gameScene);
    stage.setMinWidth(800);
    stage.setMinHeight(1000);
    stage.setFullScreen(true);
  }
}
