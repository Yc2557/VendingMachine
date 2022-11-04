package VendingApplication;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class VendingMachine {
    private final Inventory inventory;
    private final Cart cart;
    private final Stage stage;

    public boolean isLogin = false;
    private Account account = null;
    private int idleTime = 0;
    private final int idleTimeLimit = 120;
    private final Timeline timer;
    private final TransactionHandler transactionHandler;

    public VendingMachine(Stage stage) {
        this.stage = stage;
        this.inventory = new Inventory();
        this.cart = new Cart();
        this.transactionHandler = new TransactionHandler();

        this.timer = new Timeline(
                new KeyFrame(Duration.seconds(1), e -> {
                    idleTime++;
                    System.out.println(idleTime);
                    if (idleTime >= idleTimeLimit) {
                        System.out.println("Idle time limit reached");
                        idleTime = 0;
                        this.cart.clearCart();
                        try {
                            if (this.isLogin) {
                                transactionHandler.addCancelledTransaction(new CancelledTransaction(
                                        LocalDateTime.now().toLocalDate().toString(),
                                        LocalDateTime.now().toLocalTime().toString(),
                                        account.getUsername(),
                                        "timeout"));
                                this.logOut();
                            } else {
                                transactionHandler.addCancelledTransaction(new CancelledTransaction(
                                        LocalDateTime.now().toLocalDate().toString(),
                                        LocalDateTime.now().toLocalTime().toString(),
                                        "anonymous",
                                        "timeout"));
                            }

                            this.changeScene("gui/Selection.fxml");
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }));
        this.timer.setCycleCount(Timeline.INDEFINITE);

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
        if (!account.getUsername().equals("anon")) {
            this.isLogin = true;
        }
        this.account = account;
    }

    public Account getAccount() {
        return this.account;
    }

    public void logOut() {
        this.isLogin = false;
        this.account = null;
    }

    public void changeScene(String scene) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource(scene));
        Parent root = loader.load();

        Scene mainPanelView = new Scene(root);

        Controller controller = loader.getController();
        controller.initialize(this);

        this.stage.setScene(mainPanelView);
        this.stage.show();
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

    public TransactionHandler getTransactionHandler() {
        return transactionHandler;
    }

    public void resetIdleTime() {
        this.idleTime = 0;
    }

    public void startTimer() {
        if (!this.isTimerRunning()) {
            this.timer.play();
        }
    }

    public void stopTimer() {
        if (this.isTimerRunning()) {
            this.resetIdleTime();
            this.timer.stop();
        }
    }

    public boolean isTimerRunning() {
        return this.timer.getStatus() == Timeline.Status.RUNNING;
    }

    public void completeTransaction() {
        this.inventory.writeJsonFile("src/main/resources/data/inventory.json");
    }
}
