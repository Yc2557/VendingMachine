package VendingApplication;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CompletedTransactionTest {

    @BeforeAll
    public static void build() throws IOException {

        JSONObject db = new JSONObject();
        JSONArray transactions = new JSONArray();

        JSONObject transac = new JSONObject();
        transac.put("date", "02-11-2022");
        transac.put("total", "12.00");
        transac.put("change", "8.0");
        transac.put("time", "16:25");
        transac.put("user", "m");
        transac.put("paymentType", "cash");

        JSONArray items = new JSONArray();
        JSONObject item1 = new JSONObject();
        item1.put("item", "Pringles");
        item1.put("quantity", "5");
        item1.put("price", "1.5");
        items.add(item1);

        transac.put("items", items);

        transactions.add(transac);
        db.put("transactions", transactions);

        File file = new File("src/test/resources/completedTransactionTest.json");
        file.createNewFile();
        FileWriter writer = new FileWriter(file);
        writer.write(db.toJSONString());

        writer.close();

    }

    @Test
    public void getTest() {
        TransactionHandler tH = new TransactionHandler("src/test/resources/completedTransactionTest.json",
                "");
        List<CompletedTransaction> list = tH.getCompletedTransactions();
        assertEquals(1, list.size());
        assertEquals("cash", list.get(0).getPaymentType());
        assertEquals("12.00", list.get(0).getPrice());
        assertEquals("8.0", list.get(0).getChange());
        list.get(0).setDateTime("1", "2");
        assertEquals("1", list.get(0).getDate());
        assertEquals("2", list.get(0).getTime());
    }


}
