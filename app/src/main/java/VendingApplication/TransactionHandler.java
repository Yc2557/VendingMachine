package VendingApplication;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.Date;
import java.text.SimpleDateFormat;

public class TransactionHandler {

    private final String filePath = "src/main/resources/data/completed_transactions.json";

    private VendingMachine vendingMachine;

    public TransactionHandler(VendingMachine vm) {
        this.vendingMachine = vm;
    }

    public void writeTransaction(String paymentType, String change) {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String dateString = format.format(date).toString();

        try {

            JSONParser parser = new JSONParser();
            JSONObject transactionsObject = (JSONObject) parser.parse(new FileReader(filePath));
            JSONArray transactionArray = (JSONArray) transactionsObject.get("transactions");

            JSONObject newTransaction = new JSONObject();
            newTransaction.put("date", dateString);
            newTransaction.put("paymentType", paymentType);
            newTransaction.put("price", Double.toString(vendingMachine.getCart().totalCartPrice()));
            newTransaction.put("change", change);

            JSONArray items = new JSONArray();
            for (Item item: vendingMachine.getCart().getCart()) {
                JSONObject itemQuantity = new JSONObject();
                itemQuantity.put(item.getName(),Integer.toString(item.getAmount()) );
                items.add(itemQuantity);
            }
            newTransaction.put("items", items);
            transactionArray.add(newTransaction);

            FileWriter file = new FileWriter(filePath);
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