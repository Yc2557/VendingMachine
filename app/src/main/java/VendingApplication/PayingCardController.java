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
import org.checkerframework.checker.units.qual.C;
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
    private PasswordField cardNumber;

    @FXML
    private TextField totalText = new TextField("");
  
    @FXML
    private TextField CVV;

    @FXML
    private TextField expiryDate;

    @FXML
    private Text errorText;

    @FXML
    private Text foundCardName = new Text();

    @FXML
    private Text namePrompt = new Text();

    @FXML
    private Text numberPrompt = new Text();

    @FXML
    private Text expiryPrompt = new Text();

    @FXML
    private Text CVVPrompt = new Text();

    @FXML
    private Button existingCardButton = new Button();

    private CardHandler handler;

    private TransactionHandler transactionHandler;
    private String nameText;
    private String numberText;

    private String expiryDateText;

    private String CVVText;

    private VendingMachine vendingMachine;

    public void payButtonAction(ActionEvent event) throws IOException {


        if (cardName.getText().equals("") || cardNumber.getText().equals("") || CVV.getText().equals("") || expiryDate.getText().equals("")) {
            // Invalid inputs
            errorText.setText("Please enter valid card details.");
            return;
        }

        this.nameText = cardName.getText();
        this.numberText = cardNumber.getText();
        this.expiryDateText = expiryDate.getText();
        this.CVVText = CVV.getText();

        if (!handler.checkCVV(getCVV()) || !handler.checkExpiry(getExpiryDate())) {
            errorText.setText("Please enter valid card details.");
            return;
        }

        handler.checkCreditCard(getCardName(), getCardNum(), getExpiryDate(), getCVV());

        if (handler.isValidCard()) {
            // paid successfully
            createWriteTransaction();

            if (vendingMachine.isLogin) {
                handler.saveCardDetails(this.vendingMachine.getAccount().getUsername(), getCardName(), getCardNum(), getCVV(), getExpiryDate());
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

        if (!foundCardString.equals("")) {
            this.foundCardName.setVisible(true);
            this.foundCardName.setText("Saved Card Found: " + foundCardString);
            this.namePrompt.setText("New Card Name");
            this.numberPrompt.setText("New Card Number");
            this.expiryPrompt.setText("New Expiry Date");
            this.CVVPrompt.setText("New CVV");
            this.existingCardButton.setDisable(false);
            this.existingCardButton.setVisible(true);
        }
    }

    public void changeScene(ActionEvent event, String type) throws IOException {

        String sceneName = "gui/";
        switch (type) {
            case "validCard" -> sceneName += "SaveCard.fxml";
            case "backPay" -> sceneName += "PaymentSelector.fxml";
            case "completed" -> sceneName += "Selection.fxml";
        }

        vendingMachine.changeScene(event, sceneName);
    }

    public void useExistingCardAction(ActionEvent event) throws IOException {
        createWriteTransaction();
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
        handler.saveCardDetails(vendingMachine.getAccount().getUsername(), "", "", "", "");
        vendingMachine.logOut();
        changeScene(event, "completed");
    }

    public String getCardName() {
        return this.nameText;
    }

    public String getCardNum() {
        return this.numberText;
    }

    public String getCVV(){
        return this.CVVText;
    }

    public String getExpiryDate() {
        return this.expiryDateText;
    }

    public void createWriteTransaction() {
        CompletedTransaction ct = new CompletedTransaction(vendingMachine.getAccount().getUsername(), vendingMachine.getCart(), "card", Double.toString(vendingMachine.getCart().totalCartPrice()), "");
        transactionHandler.addCompletedTransaction(ct);
    }

    public void initialize(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
        this.transactionHandler = new TransactionHandler();
        this.handler = new CardHandler("src/main/resources/data/credit_cards.json");
        this.totalText.setText("$" + String.format("%.02f", this.vendingMachine.getCart().totalCartPrice()));

        if (vendingMachine.isLogin) {
            suggestCard();
        } else {
            this.foundCardName.setVisible(false);
            this.existingCardButton.setDisable(true);
            this.existingCardButton.setVisible(false);
        }

    }
}