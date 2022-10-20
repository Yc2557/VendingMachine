package VendingApplication;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class PaymentChooser implements Controller {

    @FXML
    private Button cashButton;

    @FXML
    private Button cardButton;

    @FXML
    private Button backButton;

    @FXML
    private Text instructions;

    public void changeScene(ActionEvent event, String type) throws IOException{

        String sceneName = "gui/";
        if (type.equals("cash")) {
            //sceneName += "PayingCash.fxml";

        } else if (type.equals("card")) {
            sceneName += "PayingCard.fxml";

        } else if (type.equals("back")) {
            //back to select items
        }

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource(sceneName));
        Parent root = loader.load();
        Scene panelView = new Scene(root);
        Controller controller = loader.getController();

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(panelView);
        window.show();
    }

    public void cashButtonAction(ActionEvent event) throws IOException {
        changeScene(event, "cash");
    }

    public void cardButtonAction(ActionEvent event) throws IOException {
        changeScene(event, "card");
    }

    public void backButtonAction(ActionEvent event) throws IOException {
        changeScene(event, "back");
    }

}