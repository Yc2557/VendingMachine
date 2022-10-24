package VendingApplication;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.util.List;

public class ModificationsController {

    @FXML
    private TextField categoryModifier;

    @FXML
    private Text categoryText;

    @FXML
    private TextField codeModifier;

    @FXML
    private Text codeText;

    @FXML
    private ListView<String> itemList;

    @FXML
    private Button modifyBtn;

    @FXML
    private TextField nameModifier;

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
//    private List<List<String>> lists;
//    private List<String> selectedList;
    private VendingMachine vendingMachine;
    private Inventory inventory;
    private Seller seller;

    @FXML
    void modifyProperties(MouseEvent event) {
//        System.out.println(String.valueOf(nameText));
//        System.out.println(nameModifier.getText());
        System.out.println(nameText.getText());
        if (!nameModifier.getText().isEmpty()) {
            seller.modifyName(nameText.getText(), nameModifier.getText());
        }
    }

    public void initialize(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
        this.inventory = vendingMachine.getInventory();
        this.seller = new Seller();
        setList();
    }

    public void setList() {
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
        Item item = inventory.getItem(itemList.getSelectionModel().getSelectedItem());
        nameText.setText(item.getName());
        codeText.setText(item.getItemid());
        priceText.setText(Double.toString(item.getPrice()));
        quantityText.setText(Integer.toString(item.getAmount()));
        categoryText.setText(item.getCategory());
    }

}
