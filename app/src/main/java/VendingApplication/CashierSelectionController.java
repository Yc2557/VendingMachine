package VendingApplication;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.scene.control.Button;

import java.io.IOException;

public class CashierSelectionController implements Controller {

    @FXML
    private Text downloadedReport;

    @FXML
    private Text downloadedChangeReport;

    @FXML
    private Button ownerBtn;

    private VendingMachine vendingMachine;

    public void initialize(VendingMachine vm) {
        vendingMachine = vm;
        if (vendingMachine.getAccount() != null && vendingMachine.getAccount().getRole().equals("owner")) {
            ownerBtn.setVisible(true);
        } else {
            ownerBtn.setVisible(false);
        }
    }

    public void clickOnModifyChange() throws IOException {
        vendingMachine.changeScene("gui/ModifyChange.fxml");
    }

    public void clickOnChangeReport() throws IOException {
        ReportGenerator reportGenerator = new ReportGenerator();
        reportGenerator.exportChangeQuantityReport();
        downloadedChangeReport.setText("Downloaded!");
        downloadedChangeReport.setVisible(true);
    }

    public void clickLogOut() throws IOException {
        vendingMachine.logOut();
        vendingMachine.changeScene("gui/Selection.fxml");
    }

    public void clickOnTransactionReport() throws IOException {
        ReportGenerator reportGenerator = new ReportGenerator();
        reportGenerator.exportCompletedTransactionReport(new TransactionHandler().getCompletedTransactions());
        downloadedReport.setText("Downloaded!");
        downloadedReport.setVisible(true);
    }

    public void clickOwner() throws IOException {
        vendingMachine.changeScene("gui/OwnerSelection.fxml");
    }

}
