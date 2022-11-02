package VendingApplication;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SelectionController implements Controller {

    @FXML
    private ListView<String> latestView;

    @FXML
    private ListView<String> mainView;

    private ListView<String> currentView;

    @FXML
    private Button cartButton;
    @FXML
    private Button loginButton;
    @FXML
    private Text categoryText;
    @FXML
    private TextField amountText;

    @FXML
    private Text welcomeText;

    @FXML
    private Button logOutButton;

    private List<List<String>> lists;

    private List<String> latestList;

    private Inventory inventory;
    private List<String> selectedList;
    private int selectedListIndex;
    private List<String> listNames;
    private VendingMachine vendingMachine;

    public void initialize(VendingMachine vm) {

        vendingMachine = vm;
        lists = new ArrayList<>();
        latestList = new ArrayList<>();

        if (vendingMachine.isLogin) {
            loginButton.setDisable(true);
            loginButton.setVisible(false);
            logOutButton.setDisable(false);
            logOutButton.setVisible(true);
            welcomeText.setText(String.format("Welcome %s!", vendingMachine.getAccount().getUsername()));
            latestList = vendingMachine.getHistoryAsName();
        } else {
            loginButton.setDisable(false);
            loginButton.setVisible(true);
            logOutButton.setDisable(true);
            logOutButton.setVisible(false);
            welcomeText.setText("");
            latestList = Arrays.asList("Pringles", "Thins");
        }

        inventory = vendingMachine.getInventory();


        lists.add(inventory.getDrinks());
        lists.add(inventory.getChips());
        lists.add(inventory.getChocolates());
        lists.add(inventory.getCandies());

        cartButton.setText(String.format("Cart: %d Item(s)", vendingMachine.getCart().getCartSize()));

        selectedListIndex = 0;
        selectedList = lists.get(selectedListIndex);
        listNames = Arrays.asList("Drinks", "Chips", "Chocolates", "Candies");
        categoryText.setText(listNames.get(selectedListIndex));
        fillList();

        vendingMachine.stopTimer();
    }

    public void logOutButtonClicked() {
        vendingMachine.logOut();
        loginButton.setDisable(false);
        loginButton.setVisible(true);
        logOutButton.setDisable(true);
        logOutButton.setVisible(false);
        welcomeText.setText("");
        initialize(vendingMachine);
    }

    public void buyButtonClicked() {
        int amount = Integer.parseInt(amountText.getText());
        if (Integer.parseInt(amountText.getText()) > 0) {
            Cart cart = vendingMachine.getCart();
            Item item = inventory.getItem(selectedList.get(currentView.getSelectionModel().getSelectedIndex()), "name");
            if (item == null) {
                return;
            }
            Item checkCart = cart.getItem(item);
            if (checkCart != null) {
                checkCart.addAmount(amount);
            } else {
                cart.addItem(new Item(item.getItemid(),
                        item.getName(),
                        item.getCategory(),
                        item.getPrice(),
                        amount));
            }

            item.addAmount(-1*amount);
            if (item.getAmount() < Integer.parseInt(amountText.getText())) {
                amountText.setText(String.valueOf(item.getAmount()));
            }

            cartButton.setText(String.format("Cart: %d Item(s)", vendingMachine.getCart().getCartSize()));

        }
    }

    public void cartButtonClicked() throws IOException {
        vendingMachine.changeScene("gui/cart.fxml");
    }

    public void loginButtonClicked() throws IOException {
        vendingMachine.changeScene("gui/Login.fxml");
    }

    public void addAmount() {
        int amount = Integer.parseInt(amountText.getText());
        if (currentView == null) {
            return;
        }
        Item item = inventory.getItem(currentView.getSelectionModel().getSelectedItem(), "name");
        if (item == null) {
            return;
        }
        if (amount < item.getAmount()) {
            amount += 1;
            amountText.setText(Integer.toString(amount));
        }
    }

    public void subtractAmount() {
        if (currentView == null) {
            return;
        }
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
        amountText.setText(String.valueOf(0));
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
        amountText.setText(String.valueOf(0));
        fillList();
    }

    public void fillList() {

        mainView.getItems().clear();
        mainView.setItems(FXCollections.observableArrayList(selectedList));
        mainView.getSelectionModel().select(0);

        latestView.getItems().clear();
        latestView.setItems(FXCollections.observableArrayList(latestList));
    }

    public void onClick(MouseEvent event) {
        amountText.setText("0");

        Object source = event.getSource();
        if (source instanceof ListView<?> listView) {
            if (listView == mainView) {
                System.out.println("mainView");
                currentView = mainView;
                latestView.getSelectionModel().clearSelection();
            } else if (listView == latestView) {
                System.out.println("latestView");
                currentView = latestView;
                mainView.getSelectionModel().clearSelection();
            }
        }
    }
}
