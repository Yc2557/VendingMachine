package VendingApplication;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class TransactionHandler {

    private String cancelledFilePath;
    private String normalFilePath;
    public TransactionHandler(String normalFilePath, String cancelledFilePath) {
        this.normalFilePath = normalFilePath;
        this.cancelledFilePath = cancelledFilePath;
    }

    public TransactionHandler() {
        this.normalFilePath = "src/main/resources/data/completed_transactions.json";
        this.cancelledFilePath = "src/main/resources/data/cancelled_transaction.json";
    }

    public void writeTransaction(CompletedTransaction transaction) {

        try {

            JSONParser parser = new JSONParser();
            JSONObject transactionsObject = (JSONObject) parser.parse(new FileReader(normalFilePath));
            JSONArray transactionArray = (JSONArray) transactionsObject.get("transactions");

            JSONObject newTransaction = new JSONObject();
            newTransaction.put("date", transaction.getDate());
            newTransaction.put("paymentType", transaction.getPaymentType());
            newTransaction.put("price", transaction.getPrice());
            newTransaction.put("change", transaction.getPrice());

            JSONArray items = new JSONArray();
            for (Item item: transaction.getCart().getCart()) {
                JSONObject itemQuantity = new JSONObject();
                itemQuantity.put(item.getName(),Integer.toString(item.getAmount()) );
                items.add(itemQuantity);
            }
            newTransaction.put("items", items);
            transactionArray.add(newTransaction);

            FileWriter file = new FileWriter(normalFilePath);
            file.write(transactionsObject.toJSONString());
            file.flush();
            file.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
            throw new RuntimeException(e);
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }

    }




}