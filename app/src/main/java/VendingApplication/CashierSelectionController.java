package VendingApplication;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class CashierSelectionController implements Controller {

    private VendingMachine vendingMachine;

    public void initialize(VendingMachine vm) {
        vendingMachine = vm;
    }

    public void clickOnModifyChange(ActionEvent event) throws IOException {
        vendingMachine.changeScene(event, "gui/ModifyChange.fxml");
    }

    public void clickOnChangeReport(ActionEvent event) throws IOException {
        vendingMachine.changeScene(event, "gui/ListChange.fxml");
    }

    public void clickLogOut(ActionEvent event) throws IOException {
        vendingMachine.logOut();
        vendingMachine.changeScene(event, "gui/Selection.fxml");
    }
}
