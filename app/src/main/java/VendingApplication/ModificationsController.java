package VendingApplication;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class ModificationsController {

    @FXML
    private TextField categoryModifier;

    @FXML
    private Text categoryText;

    @FXML
    private TextField codeModifier;

    @FXML
    private Text codeText;

    @FXML
    private ListView<?> itemList;

    @FXML
    private Button modifyBtn;

    @FXML
    private TextField nameModifier;

    @FXML
    private Text nameText;

    @FXML
    private TextField priceModifier;

    @FXML
    private Text priceText;

    @FXML
    private TextField quantityModifier;

    @FXML
    private Text quantityText;
    private VendingMachine vendingMachine;

    @FXML
    void modifyProperties(MouseEvent event) {

    }

    @FXML
    void onClick(MouseEvent event) {

    }

    public void initialize(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
    }

}
