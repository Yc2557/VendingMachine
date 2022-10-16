package VendingApplication;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class LoginController implements Controller {
    @FXML
    private TextField loginText;

    @FXML
    private TextField passwordText;

    @FXML
    private Text invalidMessage;

    @FXML
    private Text invalidPathText;

    public static final double TEXT_XLAYOUT = 130.0;

    public void loginButtonAction(ActionEvent event) throws IOException {
        String username = loginText.getText();
        String password = passwordText.getText();

        // Check if username and password are correct
        if (loginText.getText().equals("")) { // TO DO: Need to include getting inforation from database
            // Change to successful login page
            changeScene(event, "back");
        } else {
            System.out.println("invalid");
            setInvalidMessage();
        }
    }

    public void createButtonAction(ActionEvent event) throws IOException {
        changeScene(event, "create");
        System.out.println("test");
    }

    public void backButtonAction(ActionEvent event) throws IOException {
        changeScene(event, "back");
    }

    public void createAccountButtonAction(ActionEvent event) throws IOException {
        // Add account to login.json
        changeScene(event, "login");
    }

    public void backLoginButtonAction(ActionEvent event) throws IOException {
        changeScene(event, "login");
    }

    public void setInvalidMessage() {
        invalidMessage.setText("Username or password is invalid.");
    }

    /**
     * Changes the scene to the next relevant scene
     * 
     * @param event
     * @param type
     * @throws IOException
     */
    public void changeScene(ActionEvent event, String type) throws IOException {
        String sceneName = "gui/";
        // Changes scene fxml file based on type
        if (type.equalsIgnoreCase("login")) {
            sceneName += "Login.fxml";
        } else if (type.equalsIgnoreCase("create")) {
            sceneName += "CreateAccount.fxml";
        } else if (type.equalsIgnoreCase("back")) {
            sceneName = "";
        }

        // Loads next relevant scene
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource(sceneName));
        Parent root = loader.load();
        Scene panelView = new Scene(root);

        Controller controller = loader.getController();

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(panelView);
        window.show();
    }
}
