package VendingApplication;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class PayingCashController {

    @FXML
    private Text errorText;
    @FXML
    private TextField amountAdded;
    @FXML
    private TextField total;
    @FXML
    private TextField change;
    @FXML
    private Button payButton;
    @FXML
    private Button clearButton;
    @FXML
    private Button Coin5Cent;
    @FXML
    private Button Coin10Cent;
    @FXML
    private Button Coin20Cent;
    @FXML
    private Button Coin50Cent;
    @FXML
    private Button Coin1Dollar;
    @FXML
    private Button Coin2Dollar;
    @FXML
    private Button Note5Dollar;
    @FXML
    private Button Note10Dollar;
    @FXML
    private Button Note20Dollar;
    @FXML
    private Button Note50Dollar;
    @FXML
    private Button Note100Dollar;

    private  List<Button> buttons = new ArrayList<>();

    public void clickedOnMoney(Button button, PaymentHandler handler) {
        double currentAmount = Double.parseDouble(amountAdded.getText());

        Map<String, Long> cashAdded = handler.getCashAdded();
        String moneyStr = "";

        switch (button.getText()) {
            case "5c":
                currentAmount += 0.05;
                moneyStr = "0.05";
                break;
            case "10c":
                currentAmount += 0.10;
                moneyStr = "0.10";
                break;
            case "20c":
                currentAmount += 0.20;
                moneyStr = "0.20";
                break;
            case "50c":
                currentAmount += 0.50;
                moneyStr = "0.50";
                break;
            case "$1":
                currentAmount += 1;
                moneyStr = "1";
                break;
            case "$2":
                currentAmount += 2;
                moneyStr = "2";
                break;
            case "$5":
                currentAmount += 5;
                moneyStr = "5";
                break;
            case "$10":
                currentAmount += 10;
                moneyStr = "10";
                break;
            case "$20":
                currentAmount += 20;
                moneyStr = "20";
                break;
            case "$50":
                currentAmount += 50;
                moneyStr = "50";
                break;
            case "$100":
                currentAmount += 100;
                moneyStr = "100";
                break;
        }

        currentAmount = Math.round(currentAmount*100);
        currentAmount /= 100;

        String currentAmountStr = String.format("%.2f", currentAmount);
        amountAdded.setText(currentAmountStr);

        // Keeping track of the coins/notes that need to be added if a transaction is completed
        if (cashAdded.containsKey(moneyStr)) {
            cashAdded.put(moneyStr, cashAdded.get(moneyStr)+1);
        } else {
            cashAdded.put(moneyStr, (long) 1);
        }
    }

    public void clickedOnClear(PaymentHandler handler) {
        amountAdded.setText("0.00");
        handler.getCashAdded().clear();
    }

    public void clickedOnPay(PaymentHandler handler) {
        errorText.setText("");

        handler.processPayment(30.30, Double.parseDouble(amountAdded.getText()));

        if (!handler.getEnoughMoney()) {
            errorText.setText("Not enough money provided. Please enter the remaining amount or you can cancel the transaction.");
            errorText.setFill(Color.RED);
            handler.setEnoughMoney(true);
        }
        else if (!handler.getEnoughChange()) {
            errorText.setText("There is no available change for the inserted money. You can try a different set of notes/coins or cancel the transaction.");
            errorText.setFill(Color.RED);
            handler.setEnoughChange(true);
        }
        // If payment is successful
        else {
            double changeAmount = Double.parseDouble(amountAdded.getText()) - 30.30;
            String changeStr = String.format("%.2f", changeAmount);
            change.setText(changeStr);
        }
    }

    public void setup() {
        amountAdded.setText("0.00");
        total.setText("30.30");
        PaymentHandler handler = new PaymentHandler();

        payButton.setOnAction(event -> {
            clickedOnPay(handler);
        });

        clearButton.setOnAction(event -> {
            clickedOnClear(handler);
        });

        Collections.addAll(buttons, Coin5Cent, Coin10Cent, Coin20Cent, Coin50Cent, Coin1Dollar, Coin2Dollar,
                Note5Dollar, Note10Dollar, Note20Dollar, Note50Dollar, Note100Dollar);

        for (Button b: buttons) {
            //Define Button Action
            b.setOnAction(event -> {
                clickedOnMoney(b, handler);
            });
        }
    }
}
