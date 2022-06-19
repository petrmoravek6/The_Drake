package thedrake.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TheDrakeApp extends Application{
  public static void main(String[] args) { launch(args); }

  @Override
  public void start(Stage stage) throws Exception {
    FXMLLoader fxmlLoader = new FXMLLoader(TheDrakeApp.class.getResource("theDrake.fxml"));

    Scene mainMenu = new Scene(fxmlLoader.load(), 1920, 1080);

    mainMenu.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
    stage.setTitle("The Drake");
    stage.setScene(mainMenu);
    stage.setFullScreen(true);
    stage.show();
  }
}
