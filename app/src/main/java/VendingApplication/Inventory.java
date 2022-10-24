package VendingApplication;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Inventory {
    private List<Item> inventory;
    private List<String> drinks;
    private List<String> chips;
    private List<String> chocolates;
    private List<String> candies;

    private HashMap<String, List<String>> categories;

    public Inventory() {
        inventory = new ArrayList<>();
        drinks = new ArrayList<>();
        chips = new ArrayList<>();
        chocolates = new ArrayList<>();
        candies = new ArrayList<>();

        categories = new HashMap<>();
        categories.put("drinks", drinks);
        categories.put("chips", chips);
        categories.put("chocolate", chocolates);
        categories.put("candies", candies);
    }

    public void readJsonFile(String filePath) { //get inventory data
        JSONObject database = null;
        try {
            JSONParser jsonParser = new JSONParser();
            database = (JSONObject) jsonParser.parse(new FileReader(filePath));

        } catch (IOException | ParseException e) {
            e.printStackTrace();
            System.out.println("Error reading file");
            return;
        }

        JSONArray products = (JSONArray) database.get("products");
        for (Object product: products) {
            JSONObject productObject = (JSONObject) product;
            Long itemidLong = (Long) productObject.get("id");
            String itemid = itemidLong.toString();
            String name = (String) productObject.get("name");
            String category = (String) productObject.get("category");
            double price = (double) productObject.get("price");
            Long quantityLong = (Long) productObject.get("quantity");
            int quantity = quantityLong.intValue();

            Item item = new Item(itemid, name, category, price, quantity);
            inventory.add(item);
            List<String> list = categories.get(category);
            list.add(name);
        }
    }

    public void writeJsonFile(String filePath, JSONObject inventory) { //write inventory data
        try {
            FileWriter writer = new FileWriter(filePath);
            writer.write(inventory.toJSONString());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error reading file");
        }

    }

    public JSONObject getJSON(String filepath) {
        try {
            JSONParser jsonParser = new JSONParser();
            JSONObject inventory = (JSONObject) jsonParser.parse(new FileReader(filepath));
            return inventory;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            System.out.println("Error reading file");
            return null;
        }
    }

    public void addAmount(Item item, int amount) { //add amount to inventory
        if (inventory.contains(item)) {
            item.addAmount(amount);
        }
    }

    private void raiseError(String message) {
        System.out.println(message);
    }

    public void addItem(Item item) {
        if (inventory.contains(item)) {
            raiseError("Item already exists");
        } else {
            inventory.add(item);
        }
    }

    public void removeItem(Item item) {
        if (inventory.contains(item)) {
            inventory.remove(item);
        } else {
            raiseError("Item not found");
        }
    }

    public Item getItem(String name) {
        for (Item item: inventory) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }

    public List<Item> getInventory() {
        return inventory;
    }

    public List<String> getDrinks() {
        return drinks;
    }

    public List<String> getChips() {
        return chips;
    }

    public List<String> getChocolates() {
        return chocolates;
    }

    public List<String> getCandies() {
        return candies;
    }
}
