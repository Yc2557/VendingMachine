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

public class PayingCardController implements Controller {

    @FXML
    private TextField cardName;

    @FXML
    private TextField cardNumber;

    @FXML
    private TextField totalText = new TextField("");

    @FXML
    private Button backButtonCard;

    @FXML
    private Button backButtonPayments;

    @FXML
    private Button payButton;

    @FXML
    private Text errorText;

    @FXML
    private Text foundCardName = new Text();

    @FXML
    private Button noButton;

    @FXML
    private Button yesButton;

    @FXML
    private Button existingCardButton = new Button();

    private final CardHandler handler = new CardHandler();
    private String nameText;
    private String numberText;

    private VendingMachine vendingMachine;

    public void payButtonAction(ActionEvent event) throws IOException {

        if (cardName.getText() == null || cardNumber.getText() == null) {
            // Invalid inputs
            errorText.setText("Please enter valid card details.");
            return;
        }

        this.nameText = cardName.getText();
        this.numberText = cardNumber.getText();

        handler.checkCreditCard(getCardName(), getCardNum());

        if (handler.isValidCard()) {
            // paid successfully
            if (vendingMachine.isLogin) {
                handler.saveCardDetails(this.vendingMachine.getAccount().getUsername(), getCardName(), getCardNum());
                vendingMachine.addHistory();
                vendingMachine.getCart().clearCart();
                changeScene(event, "validCard");
            } else {
                // not logged in, don't offer to save card
                vendingMachine.getCart().clearCart();
                changeScene(event, "completed");
            }
        } else {
            // card error
            errorText.setText("Card cannot be found: Please try a different card, or pay with cash.");
        }
    }

    public void suggestCard() {

        String foundCardString = handler.findCard(vendingMachine.getAccount().getUsername());
        if (foundCardString.equals("")) {
            return;
        } else {
            foundCardName.setText("Saved Card Found: " + foundCardString);
            foundCardName.setVisible(true);
            existingCardButton.setVisible(true);
        }
    }

    public void changeScene(ActionEvent event, String type) throws IOException {

        String sceneName = "gui/";
        switch (type) {
            case "validCard" -> sceneName += "SaveCard.fxml";
            case "backCard" -> sceneName += "PayingCard.fxml";
            case "backPay" -> sceneName += "PaymentSelector.fxml";
            case "completed" -> sceneName += "Selection.fxml";
        }

        vendingMachine.changeScene(event, sceneName);
    }

    public void useExistingCardAction(ActionEvent event) throws IOException {
        vendingMachine.getCart().clearCart();
        vendingMachine.logOut();
        changeScene(event, "completed");
    }

    public void backPaymentsButtonAction(ActionEvent event) throws IOException {
        // Go back from PayingCard to PaymentSelector
        changeScene(event, "backPay");
    }

    public void yesButtonAction(ActionEvent event) throws IOException {
        vendingMachine.logOut();
        changeScene(event, "completed");
    }

    public void noButtonAction(ActionEvent event) throws IOException {
        // Overwrites saved card details
        handler.saveCardDetails(vendingMachine.getAccount().getUsername(), "", "");
        vendingMachine.logOut();
        changeScene(event, "completed");
    }

    public String getCardName() {
        return this.nameText;
    }

    public String getCardNum() {
        return this.numberText;
    }

    public void initialize(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
        this.totalText.setText("$" + String.format("%.02f", this.vendingMachine.getCart().totalCartPrice()));
        this.foundCardName.setVisible(false);
        this.existingCardButton.setVisible(false);

        if (vendingMachine.isLogin) {
            suggestCard();
        }
    }
}