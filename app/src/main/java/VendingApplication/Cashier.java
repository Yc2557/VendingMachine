package VendingApplication;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class Cashier {

    private boolean isQuantityValid = true;
    private boolean isQuantityPositive = true;
    private String file_name = "src/main/resources/data/cash.json";

    public void checkChange(String quantity) {
        try {
            // Check if it's a float
            long newQuantity = Long.parseLong(quantity);

            // If it reaches this statement then it must be an integer so we can set boolean to true
            if (newQuantity < 0) {
                setIsQuantityValid(true);
                isQuantityPositive = false;
                return;
            }
        } catch (NumberFormatException e) {
            isQuantityValid = false;
            return;
        }
        // Passed both checks
        setIsQuantityValid(true);
        setIsQuantityPositive(true);
    }

    public void modifyChange(String quantity, String JSONAmount) {
        long newQuantity = Long.parseLong(quantity);

        try {
            JSONParser jsonParser = new JSONParser();
            JSONObject database = (JSONObject) jsonParser.parse(new FileReader(file_name));

            switch (JSONAmount) {
                case "5c" -> {
                    database.put("0.05", newQuantity);
                }
                case "10c" -> {
                    database.put("0.10", newQuantity);
                }
                case "20c" -> {
                    database.put("0.20", newQuantity);
                }
                case "50c" -> {
                    database.put("0.50", newQuantity);
                }
                case "$1" -> {
                    database.put("1", newQuantity);
                }
                case "$2" -> {
                    database.put("2", newQuantity);
                }
                case "$5" -> {
                    database.put("5", newQuantity);
                }
                case "$10" -> {
                    database.put("10", newQuantity);
                }
                case "$20" -> {
                    database.put("20", newQuantity);
                }
                case "$50" -> {
                    database.put("50", newQuantity);
                }
                case "$100" -> {
                    database.put("100", newQuantity);
                }
            }

            FileWriter writer = new FileWriter(file_name);
            writer.write(database.toJSONString());
            writer.flush();
            writer.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public String getCashQuantity(String amount) {
        String quantity = null;
        try {
            JSONParser jsonParser = new JSONParser();
            JSONObject database = (JSONObject) jsonParser.parse(new FileReader(file_name));
            switch (amount) {
                case "5c" -> {
                    quantity = String.valueOf((long) database.get("0.05"));
                }
                case "10c" -> {
                    quantity = String.valueOf((long) database.get("0.10"));
                }
                case "20c" -> {
                    quantity = String.valueOf((long) database.get("0.20"));
                }
                case "50c" -> {
                    quantity = String.valueOf((long) database.get("0.50"));
                }
                case "$1" -> {
                    quantity = String.valueOf((long) database.get("1"));
                }
                case "$2" -> {
                    quantity = String.valueOf((long) database.get("2"));
                }
                case "$5" -> {
                    quantity = String.valueOf((long) database.get("5"));
                }
                case "$10" -> {
                    quantity = String.valueOf((long) database.get("10"));
                }
                case "$20" -> {
                    quantity = String.valueOf((long) database.get("20"));
                }
                case "$50" -> {
                    quantity = String.valueOf((long) database.get("50"));
                }
                case "$100" -> {
                    quantity = String.valueOf((long) database.get("100"));
                }
            }
            return quantity;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public String[] getQuantities(String[] JSONAmounts) {
        try {
            JSONParser jsonParser = new JSONParser();
            JSONObject database = (JSONObject) jsonParser.parse(new FileReader(file_name));

            String[] quantities = new String[11];
            int count = 0;
            for (String s : JSONAmounts) {
                String quantity = String.valueOf(database.get(s));
                quantities[count] = quantity;
                count += 1;
            }
            return quantities;

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean getIsQuantityValid() {
        return isQuantityValid;
    }

    public boolean getIsQuantityPositive() {
        return isQuantityPositive;
    }

    public void setIsQuantityValid(boolean bool) {
        this.isQuantityValid = bool;
    }

    public void setIsQuantityPositive(boolean bool) {
        this.isQuantityPositive = bool;
    }

    public void setFilename(String name) {
        this.file_name = name;
    }
}
