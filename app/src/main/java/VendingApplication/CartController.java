package VendingApplication;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CartController {

    @FXML
    private ListView<String> nameView;

    @FXML
    private ListView<String> priceView;

    @FXML
    private ListView<String> quantityView;

    @FXML
    private ListView<String> subtotalView;

    @FXML
    private TextField totalText;

    private VendingMachine vendingMachine;

    private int selectedItemIndex = -1;



    public void initialize(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
        updateCart();
    }

    public void updateCart() {
        Cart cart = vendingMachine.getCart();
        List<Item> items = cart.getCart();

        nameView.getItems().clear();
        priceView.getItems().clear();
        quantityView.getItems().clear();
        subtotalView.getItems().clear();

        nameView.setItems(FXCollections.observableArrayList(getNames(items)));
        priceView.setItems(FXCollections.observableArrayList(getPrices(items)));
        quantityView.setItems(FXCollections.observableArrayList(getQuantities(items)));
        subtotalView.setItems(FXCollections.observableArrayList(getSubtotals(items)));

        totalText.setText(String.format("$%.02f", cart.totalCartPrice()));
    }

    public void backAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("gui/Selection.fxml"));
        Parent root = loader.load();

        Scene mainPanelView = new Scene(root);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        SelectionController controller = loader.getController();
        controller.initialize(vendingMachine);

        window.setScene(mainPanelView);
        window.show();
    }

    public void cancelAction() {
        selectedItemIndex = nameView.getSelectionModel().getSelectedIndex();
        System.out.println(selectedItemIndex);
        if (selectedItemIndex >= 0) {
            Cart cart = vendingMachine.getCart();
            List<Item> list = cart.getCart();
            Item item = cart.getItem(list.get(selectedItemIndex));
            Inventory inventory = vendingMachine.getInventory();
            inventory.addAmount(inventory.getItem(item.getName()), item.getAmount());
            cart.removeItem(item);
            updateCart();

            selectedItemIndex = -1;
        }
    }

    public void purchaseAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("gui/PayingCash.fxml"));
        Parent root = loader.load();

        Scene mainPanelView = new Scene(root);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        PayingCashController controller = loader.getController();
        Cart cart = vendingMachine.getCart();
        controller.setup(vendingMachine, cart.totalCartPrice());

        window.setScene(mainPanelView);
        window.show();
    }

    public List<String> getNames(List<Item> items) {
        List<String> names = new ArrayList<String>();
        for (Item i : items) {
            names.add(i.getName());
        }
        return names;
    }

    public List<String> getPrices(List<Item> items) {
        List<String> prices = new ArrayList<String>();
        for (Item i : items) {
            prices.add(String.format("$%.02f", i.getPrice()));
        }
        return prices;
    }

    public List<String> getQuantities(List<Item> items) {
        List<String> quantities = new ArrayList<String>();
        for (Item i : items) {
            quantities.add(Integer.toString(i.getAmount()));
        }
        return quantities;
    }

    public List<String> getSubtotals(List<Item> items) {
        List<String> subtotals = new ArrayList<String>();
        for (Item i : items) {
            subtotals.add(String.format("$%.02f", i.totalPrice(i.getAmount())));
        }

        return subtotals;
    }
}