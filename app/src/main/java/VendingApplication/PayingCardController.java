package VendingApplication;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.text.Text;

import java.io.IOException;

public class PayingCardController implements Controller {

    @FXML
    private TextField cardName;

    @FXML
    private PasswordField cardNumber = new PasswordField();

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

    public void payButtonAction() throws IOException {
        vendingMachine.resetIdleTime();

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
            vendingMachine.completeTransaction();

            if (vendingMachine.isLogin) {
                handler.saveCardDetails(this.vendingMachine.getAccount().getUsername(), getCardName(), getCardNum(), getCVV(), getExpiryDate());
                vendingMachine.addHistory();
                vendingMachine.getCart().clearCart();
                changeScene("validCard");
            } else {
                // not logged in, don't offer to save card
                vendingMachine.addHistory();
                vendingMachine.getCart().clearCart();
                changeScene("completed");
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

    public void changeScene(String type) throws IOException {

        String sceneName = "gui/";
        switch (type) {
            case "validCard" -> sceneName += "SaveCard.fxml";
            case "backPay" -> sceneName += "PaymentSelector.fxml";
            case "completed" -> sceneName += "Selection.fxml";
        }

        vendingMachine.changeScene(sceneName);
    }

    public void useExistingCardAction() throws IOException {
        vendingMachine.resetIdleTime();
        createWriteTransaction();
        vendingMachine.getCart().clearCart();
        vendingMachine.logOut();
        changeScene("completed");
    }

    public void backPaymentsButtonAction() throws IOException {
        vendingMachine.resetIdleTime();
        // Go back from PayingCard to PaymentSelector
        changeScene("backPay");
    }

    public void yesButtonAction() throws IOException {
        vendingMachine.resetIdleTime();
        vendingMachine.logOut();
        changeScene("completed");
    }

    public void noButtonAction() throws IOException {
        vendingMachine.resetIdleTime();
        // Overwrites saved card details
        handler.saveCardDetails(vendingMachine.getAccount().getUsername(), "", "", "", "");
        vendingMachine.logOut();
        changeScene("completed");
    }

    public void fieldOnAction() {
        vendingMachine.resetIdleTime();
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
        String username = "";
        if (vendingMachine.isLogin) {username = vendingMachine.getAccount().getUsername();}
        CompletedTransaction ct = new CompletedTransaction(username, vendingMachine.getCart(), "card", Double.toString(vendingMachine.getCart().totalCartPrice()), "");
        transactionHandler.addCompletedTransaction(ct);
    }

    public void initialize(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
        this.transactionHandler = vendingMachine.getTransactionHandler();
        this.handler = new CardHandler("src/main/resources/data/credit_cards.json");
        this.totalText.setText("$" + String.format("%.02f", this.vendingMachine.getCart().totalCartPrice()));
        this.cardNumber.setSkin(new PasswordFieldSkin(cardNumber));

        if (vendingMachine.isLogin) {
            suggestCard();
        } else {
            this.foundCardName.setVisible(false);
            this.existingCardButton.setDisable(true);
            this.existingCardButton.setVisible(false);
        }

    }
}