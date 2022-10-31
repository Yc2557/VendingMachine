package VendingApplication;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.List;

public class OwnerController implements Controller {

    @FXML
    private TextField codeModifier;

    @FXML
    private Text codeText;

    @FXML
    private ListView<String> userList;

    @FXML
    private Button modifyBtn;

    @FXML
    private Button cancelDownloadBtn;

    @FXML
    private Button roleDownloadBtn;

    @FXML
    private Button sellerBtn;

    @FXML
    private Button cashierBtn;

    @FXML
    private TextField roleModifier;

    @FXML
    private Text roleText;

    @FXML
    private Text errorText;

    @FXML
    private Text downloadText;

    @FXML
    private Text downloadText2;

    private VendingMachine vendingMachine;
    private TransactionHandler transactionHandler;
    private List<Account> accounts;
    private Account selectedAccount;

    @FXML
    void modifyProperties(ActionEvent event) {
        if (!roleModifier.getText().isEmpty()) {
            if (!modifyRole(roleText.getText(), roleModifier.getText())) {
                errorText.setText("Role is invalid!");
            }
            roleModifier.clear();
        }

        setList();
        fillText(selectedAccount);
    }

    public void initialize(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
        this.transactionHandler = new TransactionHandler();
        UserManager manager = new UserManager();
        this.accounts = manager.getAllUsers();
        setList();

    }

    public void setList() {
        userList.getItems().clear();

        // Setting usernames
        for (Account acc : accounts) {
            String username = acc.getUsername();
            userList.getItems().add(username);
        }
    }

    public void selectItem() {
        for (Account acc : accounts) {
            if (acc.getUsername().equals(userList.getSelectionModel().getSelectedItem())) {
                selectedAccount = acc;
                fillText(acc);
            }
        }
    }

    public void fillText(Account account) {
        if (account != null) {
            roleText.setText(selectedAccount.getRole());
        }
    }

    public void clickLogOut(ActionEvent event) throws IOException {
        vendingMachine.logOut();
        vendingMachine.changeScene(event, "gui/Selection.fxml");
    }

    public void clickSeller(ActionEvent event) throws IOException {
        // TODO: may need additional changes
        vendingMachine.changeScene(event, "gui/Seller.fxml");
    }

    public void clickCashier(ActionEvent event) throws IOException {
        // TODO: may need additional changes
        vendingMachine.changeScene(event, "gui/Cashier.fxml");
    }

    public void clickCancelDownload(ActionEvent event) throws IOException {
        // TO DO: Download list of cancelled transactions
        transactionHandler.exportCancelledTransactionReport(transactionHandler.getCancelledTransactions());
        downloadText.setText("Downloaded!");
    }

    public void clickRoleDownload(ActionEvent event) throws IOException {
        // TO DO: Download list of current roles
        downloadText2.setText("Downloaded!");
    }

    private boolean modifyRole(String currentRole, String inputText) {
        if (!currentRole.equals(inputText)) {
            if (inputText.equals("cashier") || inputText.equals("seller") || inputText.equals("customer")
                    || inputText.equals("owner")) {
                selectedAccount.setRole(inputText);
                return true;
            }
        }
        return false;
    }

}
