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

public class CashierTest {

    Cashier cashier;
    @BeforeEach
    public void setupJSON() {
        cashier = new Cashier();
        cashier.setFilename("src/test/resources/cashierTest.json");
        cashier.modifyChange("1", "5c");
        cashier.modifyChange("2", "10c");
        cashier.modifyChange("3", "20c");
        cashier.modifyChange("4", "50c");
        cashier.modifyChange("5", "$1");
        cashier.modifyChange("6", "$2");
        cashier.modifyChange("7", "$5");
        cashier.modifyChange("8", "$10");
        cashier.modifyChange("9", "$20");
        cashier.modifyChange("10", "$50");
        cashier.modifyChange("0", "$100");
    }

    @Test
    public void testGetChangeReport() {
        String[] JSONAmounts = {"0.05", "0.10", "0.20", "0.50", "1", "2", "5", "10", "20", "50", "100"};

        String[] quantities = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "0"};
        assertArrayEquals(quantities, cashier.getQuantities(JSONAmounts));
    }

    @Test
    public void testModifyChange() {
        cashier.modifyChange("3", "5c");
        assertEquals("3", cashier.getCashQuantity("5c"));
        cashier.modifyChange("0", "5c");
        assertEquals("0", cashier.getCashQuantity("5c"));
        cashier.modifyChange("0", "10c");
        assertEquals("0", cashier.getCashQuantity("10c"));
        cashier.modifyChange("0", "20c");
        assertEquals("0", cashier.getCashQuantity("20c"));
        cashier.modifyChange("0", "50c");
        assertEquals("0", cashier.getCashQuantity("50c"));
        cashier.modifyChange("0", "$1");
        assertEquals("0", cashier.getCashQuantity("$1"));
        cashier.modifyChange("0", "$2");
        assertEquals("0", cashier.getCashQuantity("$2"));
        cashier.modifyChange("0", "$5");
        assertEquals("0", cashier.getCashQuantity("$5"));
        cashier.modifyChange("0", "$10");
        assertEquals("0", cashier.getCashQuantity("$10"));
        cashier.modifyChange("0", "$20");
        assertEquals("0", cashier.getCashQuantity("$20"));
        cashier.modifyChange("0", "$50");
        assertEquals("0", cashier.getCashQuantity("$50"));
        cashier.modifyChange("0", "$100");
        assertEquals("0", cashier.getCashQuantity("$100"));
    }
}
