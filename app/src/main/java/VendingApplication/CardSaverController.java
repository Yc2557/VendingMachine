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

public class CardSaverController implements Controller {

    private CardHandler handler;
    private String nameText;
    private int numberText;

    public CardSaverController(CardHandler handler) {
        this.handler = handler;
        start();
    }

    public void changeScene(ActionEvent event, String type) throws IOException{

        String sceneName = "gui/";
        if (type.equals("validCard")) {
            sceneName += "SaveCard.fxml";
        } else if (type.equals("back")) {
            sceneName += "PayingCard.fxml";
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

    public void start() {

    }

    public void detail(PayingCardController pcc) {
        this.nameText = pcc.getCardName();
        this.numberText = pcc.getCardNum();
    }

}