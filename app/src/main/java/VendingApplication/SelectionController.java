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

        vendingMachine = vm;

        if (vendingMachine.isLogin) {
            loginButton.setDisable(true);
            loginButton.setVisible(false);
            logOutButton.setDisable(false);
            logOutButton.setVisible(true);
            welcomeText.setText(String.format("Welcome %s!", vendingMachine.getAccount().getUsername()));
        } else {
            loginButton.setDisable(false);
            loginButton.setVisible(true);
            logOutButton.setDisable(true);
            logOutButton.setVisible(false);
            welcomeText.setText("");
        }

        inventory = vendingMachine.getInventory();

        lists = new ArrayList<>();
        lists.add(inventory.getDrinks());
        lists.add(inventory.getChips());
        lists.add(inventory.getChocolates());
        lists.add(inventory.getCandies());

        selectedListIndex = 0;
        selectedList = lists.get(selectedListIndex);
        listNames = Arrays.asList("Drinks", "Chips", "Chocolates", "Candies");
        fillList();
    }

    public void logOutButtonClicked() {
        vendingMachine.logOut();
        loginButton.setDisable(false);
        loginButton.setVisible(true);
        logOutButton.setDisable(true);
        logOutButton.setVisible(false);
        welcomeText.setText("");
    }

    public void buyButtonClicked() {
        int amount = Integer.parseInt(amountText.getText());
        if (Integer.parseInt(amountText.getText()) > 0) {
            Cart cart = vendingMachine.getCart();
            Item item = inventory.getItem(selectedList.get(mainView.getSelectionModel().getSelectedIndex()));
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

        }
    }

    public void cartButtonClicked(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("gui/cart.fxml"));
        Parent root = loader.load();

        Scene mainPanelView = new Scene(root);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        CartController controller = loader.getController();
        controller.initialize(vendingMachine);

        window.setScene(mainPanelView);
        window.show();
    }

    public void loginButtonClicked(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("gui/Login.fxml"));
        Parent root = loader.load();

        Scene mainPanelView = new Scene(root);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        LoginController controller = loader.getController();
        controller.initialize(vendingMachine);

        window.setScene(mainPanelView);
        window.show();
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
