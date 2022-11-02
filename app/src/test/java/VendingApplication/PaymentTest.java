package VendingApplication;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentTest {

    @BeforeEach
    public void setupJSON() {
        Cashier cashier = new Cashier();
        cashier.setFilename("src/test/resources/cashTest.json");
        cashier.modifyChange("5", "5c");
        cashier.modifyChange("5", "10c");
        cashier.modifyChange("5", "20c");
        cashier.modifyChange("5", "50c");
        cashier.modifyChange("5", "$1");
        cashier.modifyChange("5", "$2");
        cashier.modifyChange("5", "$5");
        cashier.modifyChange("5", "$10");
        cashier.modifyChange("5", "$20");
        cashier.modifyChange("5", "$50");
        cashier.modifyChange("5", "$100");
    }

    @Test
    public void testCorrectQuantities() {
        try {
            PaymentHandler handler = new PaymentHandler();
            handler.setFilename("src/test/resources/cashTest.json");
            handler.processPayment(30.00, 35.45);

            JSONParser jsonParser = new JSONParser();
            JSONObject database = (JSONObject) jsonParser.parse(new FileReader("src/test/resources/cashTest.json"));
            assertEquals((long) database.get("5"),2);
            assertEquals((long) database.get("0.20"), 0);
            assertEquals((long) database.get("0.05"), 2);

        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
            throw new RuntimeException(e);
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
