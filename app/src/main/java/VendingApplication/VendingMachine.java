package VendingApplication;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.checkerframework.checker.units.qual.C;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

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

    public void addAccount(Account account) {
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
        List<String> history = new ArrayList();
        if (cartItems.size() > 5) {
            for (int i = 0; i < 5; i++) {
                history.add(cartItems.get(i).getName());
            }
        } else {
            for (int i = 0; i < cartItems.size(); i++) {
                history.add(cartItems.get(i).getName());
            }
        }

        if (isLogin) {
            UserManager userManager = new UserManager();
            userManager.addHistory(this.account.getUsername(), history);
        }
    }
}
