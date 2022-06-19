package thedrake.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import thedrake.GameResult;

import java.io.IOException;

public class ExitButton extends Button {

  private final GameView context;

  public ExitButton(GameView context) {
    this.context = context;
    setPrefSize(100, 100);
    setId("ExitButton");

    setOnMouseClicked(e -> onClick());
  }

  public void onClick() {
    if(context.getActualState() != GameResult.IN_PLAY) {
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
    else {
      context.draw();
    }
  }
}
