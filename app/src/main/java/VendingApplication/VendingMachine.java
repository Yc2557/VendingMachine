package VendingApplication;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class VendingMachine {
    private Inventory inventory;
    private Cart cart;

    public boolean isLogin = false;
    private Account account = null;

    public VendingMachine() {
        this.inventory = new Inventory();
        this.cart = new Cart();

        inventory.readJsonFile("src/main/resources/data/inventory.json");
    }

    /*
     * Methods:
     * - Adds to cart from inventory
     * - Remove from cart
     */

    public Inventory getInventory() {
        return inventory;
    }

    public Cart getCart() {
        return cart;
    }

    public void setAccount(Account account) {
        this.isLogin = true;
        this.account = account;
    }

    public Account getAccount() {
        return this.account;
    }

    public void logOut() {
        this.isLogin = false;
        this.account = null;
    }

    public void changeScene(ActionEvent event, String scene) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource(scene));
        Parent root = loader.load();

        Scene mainPanelView = new Scene(root);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        Controller controller = loader.getController();
        controller.initialize(this);

        window.setScene(mainPanelView);
        window.show();
    }

    /**
     * Adds first 5 items of cart into user history
     */
    public void addHistory() {
        List<Item> cartItems = this.cart.getCart();

        for (Item item : cartItems) {
            if (!this.account.getHistory().contains(item.getItemid())) {
                if (this.account.getHistory().size() < 5) {
                    this.account.getHistory().add(item.getItemid());
                } else {
                    this.account.getHistory().remove(0);
                    this.account.getHistory().add(item.getItemid());
                }
            }
        }

        UserManager userManager = new UserManager();
        userManager.addHistory(this.account.getUsername(), this.account.getHistory());
    }

    public List<String> getHistoryAsName() {
        List<String> nameList = new ArrayList<>();
        for (String id : account.getHistory()) {
            Item item = inventory.getItem(id, "id");
            if (item != null) {
                nameList.add(item.getName());
            }
        }
        return nameList;
    }
}
