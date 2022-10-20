/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package VendingApplication;

import javafx.application.Application;
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
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("gui/Selection.fxml"));
        Parent root = loader.load();

        primaryStage.setTitle("Vending Machine");
        primaryStage.setScene(new Scene(root, 600, 400));

        SelectionController controller = loader.getController();
        controller.initialize();
        primaryStage.show();
    }
}
