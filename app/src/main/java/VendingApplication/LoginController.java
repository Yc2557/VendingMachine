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

public class LoginController implements Controller {
    @FXML
    private TextField loginText;

    @FXML
    private PasswordField passwordText;

    @FXML
    private Text invalidMessage;

    @FXML
    private Text invalidPathText;

    //public UserManager userManager = new UserManager();

    public void loginButtonAction(ActionEvent event) throws IOException {
        String username = loginText.getText();
        String password = passwordText.getText();

        // Check if username and password are correct
        /*if (username.equals(userManager.getUsername(username)) && password.equals(userManager.getPassword(username))) {
            // Send signal to main controller

            // Change to successful login page
            changeScene(event, "back");
        } else {
            System.out.println("invalid");
            invalidMessage.setText("Username or password is invalid.");
        }*/
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
        String username = loginText.getText();
        String password = passwordText.getText();

        /*if (userManager.addUser(username, password)) {
            changeScene(event, "back");
            System.out.println("Successful login");
        } else {
            invalidMessage.setText("Username already exists.");
        }*/
    }

    public void backLoginButtonAction(ActionEvent event) throws IOException {
        changeScene(event, "login");
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

    private void checkLogin(String login, String pass) {

    }
}
