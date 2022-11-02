package VendingApplication;

import java.io.IOException;

public class CashierSelectionController implements Controller {

    private VendingMachine vendingMachine;

    public void initialize(VendingMachine vm) {
        vendingMachine = vm;
    }

    public void clickOnModifyChange() throws IOException {
        vendingMachine.changeScene("gui/ModifyChange.fxml");
    }

    public void clickOnChangeReport() throws IOException {
        vendingMachine.changeScene("gui/ListChange.fxml");
    }

    public void clickLogOut() throws IOException {
        vendingMachine.logOut();
        vendingMachine.changeScene("gui/Selection.fxml");
    }
}
