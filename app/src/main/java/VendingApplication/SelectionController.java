package VendingApplication;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EventObject;
import java.util.List;

public class SelectionController implements Controller {

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

    @FXML
    private Text welcomeText;

    @FXML
    private Button logOutButton;

    private List<List<String>> lists;

    private Inventory inventory;
    private List<String> selectedList;
    private int selectedListIndex;
    private List<String> listNames;
    private VendingMachine vendingMachine;

    public void initialize(VendingMachine vm) {

        String StartText = "Latest Items Bought";
        vendingMachine = vm;
        lists = new ArrayList<>();

        if (vendingMachine.isLogin) {
            loginButton.setDisable(true);
            loginButton.setVisible(false);
            logOutButton.setDisable(false);
            logOutButton.setVisible(true);
            welcomeText.setText(String.format("Welcome %s!", vendingMachine.getAccount().getUsername()));
            StartText = "Latest Items Bought by You!";
            lists.add(vendingMachine.getHistoryAsName());
        } else {
            loginButton.setDisable(false);
            loginButton.setVisible(true);
            logOutButton.setDisable(true);
            logOutButton.setVisible(false);
            welcomeText.setText("");

            List<String> historyDisplayed = new ArrayList<>();

            if (vendingMachine.getAnonymousHistory().size() <= 5) {
                lists.add(vendingMachine.getAnonymousHistory());
            } else {
                int size = vendingMachine.getAnonymousHistory().size();

                for (int i = size-1; i > size-6; i--) {
                    historyDisplayed.add(vendingMachine.getAnonymousHistory().get(i));
                }
                lists.add(historyDisplayed);
            }
        }

        inventory = vendingMachine.getInventory();

        lists.add(inventory.getDrinks());
        lists.add(inventory.getChips());
        lists.add(inventory.getChocolates());
        lists.add(inventory.getCandies());

        cartButton.setText(String.format("Cart: %d Item(s)", vendingMachine.getCart().getCartSize()));

        selectedListIndex = 0;
        selectedList = lists.get(selectedListIndex);
        listNames = Arrays.asList(StartText, "Drinks", "Chips", "Chocolates", "Candies");
        categoryText.setText(listNames.get(selectedListIndex));
        fillList();
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
            Item item = inventory.getItem(selectedList.get(mainView.getSelectionModel().getSelectedIndex()), "name");
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

    public void cartButtonClicked(ActionEvent event) throws IOException {
        vendingMachine.changeScene(event, "gui/cart.fxml");
    }

    public void loginButtonClicked(ActionEvent event) throws IOException {
        vendingMachine.changeScene(event, "gui/Login.fxml");
    }

    public void addAmount() {
        int amount = Integer.parseInt(amountText.getText());
        Item item = inventory.getItem(mainView.getSelectionModel().getSelectedItem(), "name");
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
