package VendingApplication;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.List;

public class LoginController implements Controller {
    @FXML
    private TextField loginText;

    @FXML
    private PasswordField passwordText;

    @FXML
    private Text invalidMessage;

    @FXML
    private Text invalidPathText;

    public UserManager userManager = new UserManager("src/main/resources/data/user.json");

    private VendingMachine vendingMachine;

    public void loginButtonAction() throws IOException {
        String username = loginText.getText().trim();
        String password = passwordText.getText();

        // Check if username and password are correct
        if (checkLogin(username, password)) {
            String cardName = userManager.getCardName(username);
            String cardNumber = userManager.getCardNumber(username);
            String expDate = userManager.getExpiryDate(username);
            String cvv = userManager.getCVV(username);
            String role = userManager.getRole(username);
            List<String> hist = userManager.getHistory(username);
            // Send signal to main controller
            vendingMachine.setAccount(new Account(username, password, cardNumber, cardName, expDate, cvv, hist, role));
            // Change to successful login page
            changeScene(role);
        } else {
            System.out.println("invalid");
            invalidMessage.setText("Username or password is invalid.");
        }
    }

    public void createButtonAction() throws IOException {
        changeScene("create");
        System.out.println("test");
    }

    public void backButtonAction() throws IOException {
        changeScene("customer");
    }

    public void createAccountButtonAction() throws IOException {
        // Add account to log in.json
        String username = loginText.getText();
        String password = passwordText.getText();

        if (userManager.addUser(username, password, "customer")) {
            vendingMachine.setAccount(new Account(username, password, "","","", "", null, "customer"));
            changeScene("customer");
            System.out.println("Successful creation");
        } else {
            invalidMessage.setText("Username already exists.");
        }
    }

    public void backLoginButtonAction() throws IOException {
        changeScene("login");
    }

    /**
     * Changes the scene to the next relevant scene
     *
     * @param type the type of scene to change to
     * @throws IOException if the scene cannot be changed
     */
    public void changeScene(String type) throws IOException {
        String sceneName = "gui/";
        // Changes scene fxml file based on type
        if (type.equalsIgnoreCase("login")) {
            sceneName += "Login.fxml";
        } else if (type.equalsIgnoreCase("create")) {
            sceneName += "CreateAccount.fxml";
        } else if (type.equalsIgnoreCase("customer")) {
            sceneName += "Selection.fxml";
        } else if (type.equalsIgnoreCase("cashier")) {
            sceneName += "CashierSelection.fxml";
        } else if (type.equalsIgnoreCase("seller")) {
            sceneName += "Modifications.fxml";
        } else if (type.equalsIgnoreCase("owner")) {
            sceneName += "OwnerSelection.fxml";
        } else {
            invalidPathText.setText("Invalid path");
            return;
        }

        // Loads next relevant scene
        vendingMachine.changeScene(sceneName);
    }

    private boolean checkLogin(String login, String pass) {

        return login.equals(userManager.getUsername(login)) && pass.equals(userManager.getPassword(login));
    }

    public void initialize(VendingMachine vm) {
        vendingMachine = vm;
    }
}
