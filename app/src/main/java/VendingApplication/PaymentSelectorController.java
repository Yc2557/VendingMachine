package VendingApplication;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;

public class PaymentSelectorController {

    @FXML
    private Button cashButton;

    @FXML
    private Button cardButton;

    @FXML
    private Button backButton;

    @FXML
    private Text instructions;

    private VendingMachine vendingMachine;

    public void changeScene(ActionEvent event, String type) throws IOException{

        String sceneName = "gui/";
        switch (type) {
            case "cash" -> sceneName += "PayingCash.fxml";
            case "card" -> sceneName += "PayingCard.fxml";
            case "back" -> sceneName += "cart.fxml";
        }

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource(sceneName));
        Parent root = loader.load();
        Scene panelView = new Scene(root);
        switch (type) {
            case "back" -> {
                CartController controller = loader.getController();
                controller.initialize(vendingMachine);
            }
            case "cash" -> {
                PayingCashController controller = loader.getController();
                controller.setup(vendingMachine, vendingMachine.getCart().totalCartPrice());
            }
            case "card" -> {
                PayingCardController controller = loader.getController();
                controller.initialize(vendingMachine);
            }
        }


        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(panelView);
        window.show();
    }

    public void cashButtonAction(ActionEvent event) throws IOException {
        changeScene(event, "cash");
    }

    public void cardButtonAction(ActionEvent event) throws IOException {
        changeScene(event, "card");
    }

    public void backButtonAction(ActionEvent event) throws IOException {
        changeScene(event, "back");
    }

    public void initialize(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
    }

}