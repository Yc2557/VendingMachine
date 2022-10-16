/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package VendingApplication;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("gui/login.fxml"));
        primaryStage.setTitle("Vending Machine");
        primaryStage.setScene(new Scene(root, 550, 400));
        primaryStage.show();
    }
}
