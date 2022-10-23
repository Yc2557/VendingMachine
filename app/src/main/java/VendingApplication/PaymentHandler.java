package VendingApplication;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PaymentHandler {

    private String file_name = "src/main/resources/data/cash.json";
    private boolean isEnoughMoney = true;
    private boolean hasEnoughChange = true;
    private Map<String,Long> cashAdded = new HashMap<>();

    public void processPayment(double totalAmount, double paidAmount) {
        try {
            if (paidAmount < totalAmount) {
                this.isEnoughMoney = false;
                return;
            }

            JSONParser jsonParser = new JSONParser();
            JSONObject database = (JSONObject) jsonParser.parse(new FileReader(file_name));

            String[] amounts = {"100", "50", "20", "10", "5", "2", "1", "0.50", "0.20", "0.10", "0.05"};

            // Calculate change
            double change = Math.round((paidAmount - totalAmount)*100);
            change /= 100;

            Map<String,Long> cashLeft = new HashMap<>();
            // Determine which coins/notes need to be used for the change and how many
            for (int i = 0; i < amounts.length; i++) {
                double amount = Double.parseDouble(amounts[i]);

                if (amount > change) {
                    continue;
                } else {
                    double numNotes = Math.min(Math.floor(change / amount), (long) database.get(amounts[i]));
                    change -= numNotes * amount;
                    change = Math.round(change*100);
                    change /= 100;
                    long numNotesLeft = (long) database.get(amounts[i]) - (long) numNotes;

                    cashLeft.put(amounts[i], numNotesLeft);
                }
            }

            // If there is no available change
            if (change != 0) {
                this.hasEnoughChange = false;
                return;
            } else {
                // Update database with new amounts for each coin/note
                for (Map.Entry<String,Long> entry : cashLeft.entrySet()) {
                    String amount = entry.getKey();
                    long numNotesLeft = entry.getValue();
                    database.put(amount, numNotesLeft);

                    FileWriter writer = new FileWriter(file_name);
                    writer.write(database.toJSONString());
                    writer.flush();
                    writer.close();
                }

                for (Map.Entry<String,Long> entry : cashAdded.entrySet()) {
                    String amount = entry.getKey();
                    long numNotesAdded = entry.getValue();
                    database.put(amount, (long) database.get(amount) + numNotesAdded);

                    FileWriter writer = new FileWriter(file_name);
                    writer.write(database.toJSONString());
                    writer.flush();
                    writer.close();
                }
            }

        }  catch (FileNotFoundException e) {
            System.out.println("File not found!");
            throw new RuntimeException(e);
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public void setEnoughMoney(boolean bool) {
        this.isEnoughMoney = bool;
    }
    public boolean getEnoughMoney() {
        return this.isEnoughMoney;
    }

    public void setEnoughChange(boolean bool) {
        this.hasEnoughChange = bool;
    }

    public boolean getEnoughChange() {
        return this.hasEnoughChange;
    }

    public Map<String,Long> getCashAdded() {
        return this.cashAdded;
    }

    public void setFilename(String name) {
        this.file_name = name;
    }
}
