package VendingApplication;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;

public class ModificationsController implements Controller {

    @FXML
    private TextField categoryModifier;

    @FXML
    private Text categoryText;

    @FXML
    private Text codeText;

    @FXML
    private ListView<String> itemList;

    @FXML
    private TextField nameModifier;

    @FXML
    private Button downloadCurrentInventory;

    @FXML
    private Button downloadItemSummary;

    @FXML
    private Button ownerBtn;

    @FXML
    private Button logoutBtn;

    @FXML
    private Text nameText;

    @FXML
    private TextField priceModifier;

    @FXML
    private Text priceText;

    @FXML
    private TextField quantityModifier;

    @FXML
    private Text quantityText;
    private VendingMachine vendingMachine;
    private Inventory inventory;
    private Seller seller;

    private Item item;

    @FXML
    void modifyProperties() {
        if (!nameModifier.getText().isEmpty()) {
            if (!seller.modifyName(nameText.getText(), nameModifier.getText())) {
                System.out.println("Invalid Name!");
            }
            nameModifier.clear();
        }

        if (!categoryModifier.getText().isEmpty()) {
            if (!seller.modifyCategory(nameText.getText(), categoryModifier.getText())) {
                System.out.println("Invalid Category!");
            }
            categoryModifier.clear();
        }
        if (!priceModifier.getText().isEmpty()) {
            if (!seller.modifyPrice(nameText.getText(), priceModifier.getText())) {
                System.out.println("Invalid Price!");
            }
            priceModifier.clear();
        }
        if (!quantityModifier.getText().isEmpty()) {
            if (!seller.modifyQuantity(nameText.getText(), quantityModifier.getText())) {
                System.out.println("Invalid Quantity - Must be between 0-15!");
            }
            quantityModifier.clear();
        }
        setList();
        fillText(item);
    }

    public void initialize(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
        this.inventory = vendingMachine.getInventory();
        this.seller = new Seller(this.inventory);
        setList();
        if (vendingMachine.getAccount() != null && vendingMachine.getAccount().getRole().equals("owner")) {
            ownerBtn.setVisible(true);
        } else {
            ownerBtn.setVisible(false);
        }

    }

    public void setList() {
        itemList.getItems().clear();

        // Setting Drinks
        for (String s : inventory.getDrinks()) {
            itemList.getItems().add(s);
        }

        // Setting Chips
        for (String s : inventory.getChips()) {
            itemList.getItems().add(s);
        }

        // Setting Chocolates
        for (String s : inventory.getChocolates()) {
            itemList.getItems().add(s);
        }

        // Setting Candies
        for (String s : inventory.getCandies()) {
            itemList.getItems().add(s);
        }
    }

    public void selectItem() {
        item = inventory.getItem(itemList.getSelectionModel().getSelectedItem(), "name");
        fillText(item);
    }

    public void fillText(Item item) {
        if (item != null) {
            nameText.setText(item.getName());
            codeText.setText(item.getItemid());
            priceText.setText(Double.toString(item.getPrice()));
            quantityText.setText(Integer.toString(item.getAmount()));
            categoryText.setText(item.getCategory());
        }
    }

    public void clickOwner() throws IOException {
        vendingMachine.changeScene("gui/OwnerSelection.fxml");
    }

    public void clickLogout() throws IOException {
        vendingMachine.logOut();
        vendingMachine.changeScene("gui/Selection.fxml");
    }

    public void downloadCurrentInventory() {
        ReportGenerator rg = new ReportGenerator();
        rg.exportCurrentInventoryReport(inventory);
        System.out.println("Downloaded Current Inventory Report!");
    }

    public void downloadItemSummary() {
        ReportGenerator rg = new ReportGenerator();
        rg.exportItemSummaryReport(inventory);
        System.out.println("Downloaded Item Summary Report!");
    }

}
