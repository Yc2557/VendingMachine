package VendingApplication;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SelectionController {

    @FXML
    private Button buyButton;

    @FXML
    private ListView<String> mainView;

    @FXML
    private Button cartButton;
    @FXML
    private Button loginButton;
    @FXML
    private Text categoryText;
    @FXML
    private TextField amountText;
    private List<List<String>> lists;

    private Inventory inventory;
    private List<String> selectedList;
    private int selectedListIndex;
    private List<String> listNames;


    public SelectionController() {
        inventory = new Inventory();
        inventory.readJsonFile("src/main/resources/data/inventory.json");

        lists = new ArrayList<>();
        lists.add(inventory.getDrinks());
        lists.add(inventory.getChips());
        lists.add(inventory.getChocolates());
        lists.add(inventory.getCandies());

        selectedListIndex = 0;
        selectedList = lists.get(selectedListIndex);
        listNames = Arrays.asList("Drinks", "Chips", "Chocolates", "Candies");
    }

    public void initialize() {
        fillList();
    }

    public void buyButtonClicked() {
    }

    public void cartButtonClicked() {
    }

    public void loginButtonClicked() {
    }

    public void addAmount() {
        int amount = Integer.parseInt(amountText.getText());
        Item item = inventory.getItem(mainView.getSelectionModel().getSelectedItem());
        if (amount < item.getAmount()) {
            amount += 1;
            amountText.setText(Integer.toString(amount));
        }
    }

    public void subtractAmount() {
        int amount = Integer.parseInt(amountText.getText());
        if (amount > 0) {
            amount -= 1;
            amountText.setText(Integer.toString(amount));
        }
    }

    public void goLeft() {
        if (selectedListIndex > 0) {
            selectedListIndex--;
        } else {
            selectedListIndex = lists.size() - 1;
        }
        selectedList = lists.get(selectedListIndex);
        categoryText.setText(listNames.get(selectedListIndex));
        fillList();
    }

    public void goRight() {
        if (selectedListIndex < lists.size() - 1) {
            selectedListIndex++;
        } else {
            selectedListIndex = 0;
        }
        selectedList = lists.get(selectedListIndex);
        categoryText.setText(listNames.get(selectedListIndex));
        fillList();
    }

    public void fillList() {

        mainView.getItems().clear();
        mainView.setItems(FXCollections.observableArrayList(selectedList));
        mainView.getSelectionModel().select(0);
    }

    public void onClick() {
        System.out.println("clicked");
        amountText.setText("0");
    }

}
