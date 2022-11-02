package VendingApplication;

import java.io.IOException;

public class PaymentSelectorController implements Controller {

    private VendingMachine vendingMachine;

    public void changeScene(String type) throws IOException{

        String sceneName = "gui/";
        switch (type) {
            case "cash" -> sceneName += "PayingCash.fxml";
            case "card" -> sceneName += "PayingCard.fxml";
            case "back" -> sceneName += "cart.fxml";
        }

        vendingMachine.changeScene(sceneName);
    }

    public void cashButtonAction() throws IOException {
        vendingMachine.resetIdleTime();
        changeScene("cash");
    }

    public void cardButtonAction() throws IOException {
        vendingMachine.resetIdleTime();
        changeScene("card");
    }

    public void backButtonAction() throws IOException {
        vendingMachine.stopTimer();
        changeScene("back");
    }

    public void initialize(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
        vendingMachine.startTimer();
    }

}