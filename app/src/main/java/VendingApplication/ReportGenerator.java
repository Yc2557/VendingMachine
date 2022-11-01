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
}
