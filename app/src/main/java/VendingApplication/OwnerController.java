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
    private Button removeBtn;

    @FXML
    private Button addBtn;

    @FXML
    private Button sellerBtn;

    @FXML
    private Button cashierBtn;

    @FXML
    private TextField roleModifier;

    @FXML
    private TextField passModifier;

    @FXML
    private TextField usernameModifier;

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
    private ReportGenerator reportGenerator;
    private UserManager userManager;
    private List<Account> accounts;
    private Account selectedAccount;
    private Account newAccount;

    // @FXML
    // void modifyProperties(ActionEvent event) {
    // if (!roleModifier.getText().isEmpty()) {
    // if (!modifyRole(roleText.getText(), roleModifier.getText())) {
    // errorText.setText("Role is invalid!");
    // }
    // roleModifier.clear();
    // }

    // setList();
    // fillText(selectedAccount);
    // }

    @FXML
    void clickAdd() {
        if (!usernameModifier.getText().isEmpty() && !passModifier.getText().isEmpty()
                && !roleModifier.getText().isEmpty()) {
            if (roleModifier.getText().equals("seller") || roleModifier.getText().equals("cashier")
                    || roleModifier.getText().equals("customer") || roleModifier.getText().equals("owner")) {
                if (!userManager.addUser(usernameModifier.getText(), passModifier.getText(), roleModifier.getText())) {
                    errorText.setText("User already exists!");
                    return;
                }
                this.accounts = userManager.getAllUsers();
                setList();
                usernameModifier.clear();
                passModifier.clear();
                roleModifier.clear();
            } else {
                errorText.setText("Role is invalid!");
            }
        } else {
            errorText.setText("Please fill all fields!");
        }
    }

    @FXML
    void clickRemove() {
        if (selectedAccount != null) {
            userManager.removeUser(selectedAccount.getUsername());
            accounts.remove(selectedAccount);
            setList();
        }
    }

    public void initialize(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
        this.transactionHandler = new TransactionHandler();
        this.reportGenerator = new ReportGenerator();
        this.userManager = new UserManager();
        this.accounts = userManager.getAllUsers();
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
            }
        }
    }

    public void clickLogOut(ActionEvent event) throws IOException {
        vendingMachine.logOut();
        vendingMachine.changeScene("gui/Selection.fxml");
    }

    public void clickSeller() throws IOException {
        // TODO: may need additional changes
        vendingMachine.changeScene("gui/Seller.fxml");
    }

    public void clickCashier() throws IOException {
        // TODO: may need additional changes
        vendingMachine.changeScene("gui/Cashier.fxml");
    }

    public void clickCancelDownload() throws IOException {
        // TO DO: Download list of cancelled transactions
        List<CancelledTransaction> cancelledTransactions = transactionHandler.getCancelledTransactions();
        reportGenerator.exportCancelledTransactionReport(cancelledTransactions);
        downloadText.setText("Downloaded!");
    }

    public void clickRoleDownload() throws IOException {
        // TO DO: Download list of current roles
        reportGenerator.exportUserRoleReport(accounts);
        downloadText2.setText("Downloaded!");
    }

    // private boolean modifyRole(String currentRole, String inputText) {
    // if (!currentRole.equals(inputText)) {
    // if (inputText.equals("cashier") || inputText.equals("seller") ||
    // inputText.equals("customer")
    // || inputText.equals("owner")) {
    // selectedAccount.setRole(inputText);
    // return true;
    // }
    // }
    // return false;
    // }

}
