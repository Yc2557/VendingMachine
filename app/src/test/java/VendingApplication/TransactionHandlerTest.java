/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package VendingApplication;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TransactionhandlerTest {

    @BeforeAll
    public static void buildJson() throws IOException {
        int i = 0;

        JSONObject database = new JSONObject();
        JSONArray transactions = new JSONArray();

        JSONObject transacObj = new JSONObject();
        transacObj.put("date", "31/10/2022");
        transacObj.put("time", "1020");
        transacObj.put("user", "anonymous");
        transacObj.put("reason", "timeout");

        transactions.add(transacObj);

        JSONObject transacObj2 = new JSONObject();

        transacObj2.put("date", "31/10/2022");
        transacObj2.put("time", "1050");
        transacObj2.put("user", "anonymous");
        transacObj2.put("reason", "user cancelled");

        transactions.add(transacObj2);

        database.put("transactions", transactions);

        File file = new File("src/test/resources/cancelledTransactionTest.json");
        file.createNewFile();
        FileWriter writer = new FileWriter(file);
        writer.write(database.toJSONString());

        writer.close();
    }

    @Test
    public void getInformationTest() {
        TransactionHandler transactionHandler = new TransactionHandler("",
                "src/test/resources/cancelledTransactionTest.json");
        List<CancelledTransaction> transactions = transactionHandler.getCancelledTransactions();
        assertEquals(2, transactions.size());
        assertEquals("31/10/2022", transactions.get(0).getDate());
        assertEquals("1020", transactions.get(0).getTime());
        assertEquals("anonymous", transactions.get(0).getUsername());
        assertEquals("timeout", transactions.get(0).getReason());
        assertEquals("31/10/2022", transactions.get(1).getDate());
        assertEquals("1050", transactions.get(1).getTime());
        assertEquals("anonymous", transactions.get(1).getUsername());
        assertEquals("user cancelled", transactions.get(1).getReason());
    }

}