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

//takes in card details
//validates card
//offers to add card to user
//adds card to user json


public class PayingCardController {

    @FXML
    private TextField cardName;

    @FXML
    private TextField cardNumber;

    @FXML
    private TextField total;

    @FXML
    private Button clearButton;

    @FXML
    private Button payButton;

    @FXML
    private Text errorText;

    public void clickedPay(CardHandler handler) {

        String nameText = cardName.getText();
        Integer numberText = Integer.parseInt(cardNumber.getText());

        handler.checkCreditCard(nameText, numberText);
        handler.validateCard();

        if (handler.isValidCard()) {
            //paid successfully
            sceneSaveCard();

            //needs to know the username
            //handler.saveCardDetails()

        } else {
            //card error
            errorText.setText("Card Details Invalid: Please Try A Different Card.");
        }

    }

    public void sceneSaveCard() {
        
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("gui/SaveCard.fxml"));
        Parent root = loader.load();
        Scene panelView = new Scene(root);

        Controller controller = loader.getController();

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(panelView);
        window.show();


    }

    public void setup() {
        CardHandler handler = new CardHandler();

        payButton.setOnAction(event -> {
            clickedPay(handler);
        });



    }




}