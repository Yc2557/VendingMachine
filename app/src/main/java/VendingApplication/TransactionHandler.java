package VendingApplication;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class TransactionHandler {
    private String cancelledFilePath;
    private String normalFilePath;
    private String reportsFilePath;

    public TransactionHandler(String normalFilePath, String cancelledFilePath, String reportsFilePath) {
        this.normalFilePath = normalFilePath;
        this.cancelledFilePath = cancelledFilePath;
        this.reportsFilePath = reportsFilePath;
    }

    public TransactionHandler() {
        this.normalFilePath = "";
        this.cancelledFilePath = "src/main/resources/data/cancelled_transaction.json";
        this.reportsFilePath = "src/main/reports";
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

    /** Print out csv of cancelled transactions */
    public void exportCancelledTransactionReport(List<CancelledTransaction> transactions) {
        String reportPath = reportsFilePath + "/cancelled_transaction_report.csv";
        try {
            BufferedWriter writer = Files.newBufferedWriter(Paths.get(reportPath));
            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                    .withHeader("Date", "Time", "User", "Reason"));

            for (CancelledTransaction transaction : transactions) {
                csvPrinter.printRecord(transaction.getDate(), transaction.getTime(), transaction.getUsername(),
                        transaction.getReason());
            }

            csvPrinter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
