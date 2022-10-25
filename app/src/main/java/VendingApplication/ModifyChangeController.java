package VendingApplication;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.IOException;

public class ModifyChangeController implements Controller {

    @FXML
    private ListView<String> moneyList;
    @FXML
    private TextField currentQuantity;
    @FXML
    private TextField newQuantity;
    @FXML
    private Text errorText;
    private VendingMachine vendingMachine;
    private Cashier cashier;
    private String JSONAmount;
    String[] amounts = {"5c", "10c", "20c", "50c", "$1", "$2", "$5", "$10", "$20", "$50", "$100"};

    public void initialize(VendingMachine vm) {
        vendingMachine = vm;
        cashier = new Cashier();

        moneyList.setItems(FXCollections.observableArrayList(amounts));

        moneyList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
                String amount = moneyList.getSelectionModel().getSelectedItem();
                JSONAmount = amount;
                String quantity = cashier.getCashQuantity(amount);
                currentQuantity.setText(quantity);
            }
        });
    }

    public void changeButtonClicked() {
        if (!currentQuantity.getText().isEmpty()) {
            cashier.checkChange(newQuantity.getText());

            if (!cashier.getIsQuantityValid()) {
                errorText.setText("Please enter an integer value.");
                errorText.setFill(Color.RED);
            }
            else if (!cashier.getIsQuantityPositive()) {
                errorText.setText("Please enter a positive integer.");
                errorText.setFill(Color.RED);
            }
            else {
                cashier.modifyChange(newQuantity.getText(), JSONAmount);
                currentQuantity.setText("");
                newQuantity.setText("");
                errorText.setText("");
            }
        } else {
            errorText.setText("Please select a coin or note.");
            errorText.setFill(Color.RED);
        }
    }

    public void clickOnBackButton(ActionEvent event) throws IOException {
        vendingMachine.changeScene(event, "gui/CashierSelection.fxml");
    }
}
