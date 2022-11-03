package VendingApplication;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

import java.io.IOException;

public class CashierSelectionController implements Controller {

    @FXML
    private Text downloadedReport;

    @FXML
    private Text downloadedChangeReport;

    private VendingMachine vendingMachine;

    public void initialize(VendingMachine vm) {
        vendingMachine = vm;
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

}
