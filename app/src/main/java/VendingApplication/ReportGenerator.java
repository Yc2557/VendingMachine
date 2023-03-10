package VendingApplication;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.List;
import java.util.ArrayList;

public class ReportGenerator {
    private String reportsFilePath;

    public ReportGenerator() {
        this.reportsFilePath = "src/main/reports";
    }

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

    public void exportUserRoleReport(List<Account> accounts) {
        String reportPath = reportsFilePath + "/user_role_report.csv";
        try {
            BufferedWriter writer = Files.newBufferedWriter(Paths.get(reportPath));
            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                    .withHeader("Username", "Role"));

            for (Account account : accounts) {
                csvPrinter.printRecord(account.getUsername(), account.getRole());
            }

            csvPrinter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void exportCurrentInventoryReport(Inventory inventory) {
        String reportPath = reportsFilePath + "/inventory_report.csv";

        try {
            BufferedWriter writer = Files.newBufferedWriter(Paths.get(reportPath));
            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                    .withHeader("Name", "ID", "Category", "Quantity", "Price"));

            for (Item item : inventory.getInventory()) {
                csvPrinter.printRecord(item.getName(), item.getItemid(), item.getCategory(),
                        item.getAmount(), item.getPrice());
            }

            csvPrinter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void exportItemSummaryReport(Inventory inventory) {
        String reportPath = reportsFilePath + "/item_summary_report.txt";

        try {
            FileWriter writer = new FileWriter(reportPath);

            for (Item item : inventory.getInventory()) {
                StringBuilder str = new StringBuilder();
                str.append(item.getName() + "; ");
                str.append(item.getItemid() + "; ");
                str.append(item.getAmount() + "\n");
                writer.write(String.valueOf(str));
            }

            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void exportCompletedTransactionReport(List<CompletedTransaction> transactions) {

        String reportPath = reportsFilePath += "/completed_transaction_report.csv";

        try {
            BufferedWriter writer = Files.newBufferedWriter(Paths.get(reportPath));
            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                    .withHeader("User", "Date","Time", "Payment Type", "Total", "Change", "Amountx Item@Price"));

            for (CompletedTransaction transaction : transactions) {
                String cartString = "";

                for (Item item: transaction.getCart().getCart()) {
                    cartString += item.getAmount()+ "x " +item.getName() +" @$"+item.getPrice()+" ea, ";
                }
                cartString = cartString.substring(0, cartString.length()-2);
                csvPrinter.printRecord(transaction.getUsername(), transaction.getDate(), transaction.getTime(),
                        transaction.getPaymentType(),transaction.getPrice(),  transaction.getChange(), cartString);
            }

            csvPrinter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void exportChangeQuantityReport() {
        String reportPath = reportsFilePath += "/change_quantity_report.csv";

        String[] amounts = {"5c", "10c", "20c", "50c", "$1", "$2", "$5", "$10", "$20", "$50", "$100"};
        String[] JSONAmounts = {"0.05", "0.10", "0.20", "0.50", "1", "2", "5", "10", "20", "50", "100"};

        Cashier cashier = new Cashier();
        try {
            BufferedWriter writer = Files.newBufferedWriter(Paths.get(reportPath));
            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                    .withHeader("Coin/Note", "Quantity"));

            String[] quantities = cashier.getQuantities(JSONAmounts);
            for (int i = 0; i < amounts.length; i++) {
                csvPrinter.printRecord(amounts[i], quantities[i]);
            }

            csvPrinter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
