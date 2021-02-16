package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {


    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent scene = FXMLLoader.load(getClass().getResource("Fxml/Home.fxml"));
        primaryStage.setTitle("WoW Mailing List");
        primaryStage.setScene(new Scene(scene, 750, 500));
        primaryStage.show();
    }
}