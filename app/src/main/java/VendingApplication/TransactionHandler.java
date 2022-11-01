package VendingApplication;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.*;
import java.util.List;
import java.util.ArrayList;

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

    public List<CancelledTransaction> getCancelledTransactions() {
        List<CancelledTransaction> transactions = new ArrayList<>();
        try {
            FileReader reader = new FileReader(cancelledFilePath);
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
            JSONArray transactionsArray = (JSONArray) jsonObject.get("transactions");

            for (Object transaction : transactionsArray) {
                JSONObject transactionObject = (JSONObject) transaction;

                String date = (String) transactionObject.get("date");
                String time = (String) transactionObject.get("time");
                String user = (String) transactionObject.get("user");
                String reason = (String) transactionObject.get("reason");
                CancelledTransaction newTransaction = new CancelledTransaction(date, time, user, reason);
                transactions.add(newTransaction);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
            throw new RuntimeException(e);
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }

        return transactions;
    }

    public void addCancelledTransaction(CancelledTransaction transaction) {
        try {
            FileReader reader = new FileReader(cancelledFilePath);
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
            JSONArray transactions = (JSONArray) jsonObject.get("transactions");

            JSONObject newTransaction = new JSONObject();
            newTransaction.put("date", transaction.getDate());
            newTransaction.put("time", transaction.getTime());
            newTransaction.put("user", transaction.getUsername());
            newTransaction.put("reason", transaction.getReason());
            transactions.add(newTransaction);

            FileWriter file = new FileWriter(cancelledFilePath);
            file.write(jsonObject.toJSONString());
            file.flush();
            file.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
            throw new RuntimeException(e);
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public void addCompletedTransaction(CompletedTransaction transaction) {

        try {
            JSONParser parser = new JSONParser();
            JSONObject transactionsObject = (JSONObject) parser.parse(new FileReader(normalFilePath));
            JSONArray transactionArray = (JSONArray) transactionsObject.get("transactions");

            JSONObject newTransaction = new JSONObject();
            newTransaction.put("date", transaction.getDate());
            newTransaction.put("time", transaction.getTime());
            newTransaction.put("paymentType", transaction.getPaymentType());
            newTransaction.put("price", transaction.getPrice());
            newTransaction.put("change", transaction.getChange());

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
