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
    private TextField total;

    @FXML
    private Button backButtonCard;

    @FXML
    private Button backButtonPayments;

    @FXML
    private Button payButton;

    @FXML
    private Text errorText;

    @FXML
    private Button noButton;

    @FXML
    private Button yesButton;

    private CardHandler handler = new CardHandler();
    private String nameText;
    private int numberText;

    public void payButtonAction(ActionEvent event) throws IOException {

        if (cardName.getText() == null || cardNumber.getText() == null || !isNumeric(cardNumber.getText())) {
            //Invalid inputs
            errorText.setText("Please enter valid card details.");
            return;
        }

        this.nameText = cardName.getText();
        this.numberText = Integer.parseInt(cardNumber.getText());

        handler.checkCreditCard(this.nameText, this.numberText);

        if (handler.isValidCard()) {
            //paid successfully
            changeScene(event, "validCard");
            handler.saveCardDetails("test",getCardName(), getCardNum());
        } else {
            //card error
            errorText.setText("Card cannot be found: Please try a different card, or pay with cash.");
        }
    }

    public void changeScene(ActionEvent event, String type) throws IOException{

        String sceneName = "gui/";
        if (type.equals("validCard")) {
            sceneName += "SaveCard.fxml";
        } else if (type.equals("backCard")) {
            sceneName += "PayingCard.fxml";
        } else if (type.equals("backPay")) {
            sceneName += "PaymentsSelector.fxml";
        }

        //Same controller
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource(sceneName));


        Parent root = loader.load();
        Scene panelView = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(panelView);
        window.show();
    }

    public void backCardButtonAction(ActionEvent event) throws IOException {
        //back from save card to card entry
        changeScene(event, "backCard");
    }
    public void backPaymentsButtonAction(ActionEvent event) throws IOException {
        //back from pay with card to pay options
        changeScene(event, "backPay");
    }

    public void yesButtonAction(ActionEvent event) throws IOException {
        noButtonAction(event);
        //Transaction completed
    }

    public void noButtonAction(ActionEvent event) throws IOException {
        //Overwrites saved card details
        handler.saveCardDetails("test", null, 0);
        //continues on to dispense items...?
    }

    public static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    public void setup() throws IOException {

    }

    public String getCardName() {return this.nameText;}
    public int getCardNum() {return this.numberText;}


}