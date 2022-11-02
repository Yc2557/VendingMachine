package VendingApplication;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.io.IOException;

public class ListChangeController implements Controller {

    @FXML
    private ListView<String> moneyList;
    @FXML
    private ListView<String> quantityList;
    private VendingMachine vendingMachine;
    private Cashier cashier;

    String[] amounts = {"5c", "10c", "20c", "50c", "$1", "$2", "$5", "$10", "$20", "$50", "$100"};
    String[] JSONAmounts = {"0.05", "0.10", "0.20", "0.50", "1", "2", "5", "10", "20", "50", "100"};

    public void initialize(VendingMachine vm) {
        vendingMachine = vm;
        cashier = new Cashier();

        moneyList.setItems(FXCollections.observableArrayList(amounts));
        String[] quantities = cashier.getQuantities(JSONAmounts);
        quantityList.setItems(FXCollections.observableArrayList(quantities));
    }

    public void clickOnBackButton() throws IOException {
        vendingMachine.changeScene("gui/CashierSelection.fxml");
    }
}
