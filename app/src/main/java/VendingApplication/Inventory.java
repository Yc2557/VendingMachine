package VendingApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.awt.event.ActionEvent;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Inventory {
    private final List<Item> inventory;
    private final List<String> drinks;
    private final List<String> chips;
    private final List<String> chocolates;
    private final List<String> candies;

    private final HashMap<String, List<String>> categories;

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
        JSONObject database;
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
            String itemid = (String) productObject.get("id");
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

    public void writeJsonFile(String filePath) { //write inventory data
        JSONObject database = new JSONObject();
        JSONArray products = new JSONArray();

        for (Item item: inventory) {
            JSONObject product = new JSONObject();
            product.put("id", item.getItemid());
            product.put("name", item.getName());
            product.put("category", item.getCategory());
            product.put("price", item.getPrice());
            product.put("quantity", item.getAmount());
            products.add(product);
        }

        database.put("products", products);

        try (FileWriter file = new FileWriter(filePath)) {
            file.write(database.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error writing to file");
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
        drinks.clear();
        for (Item item: inventory) {
            if (item.getCategory().equalsIgnoreCase("drinks")) {
                drinks.add(item.getName());
            }
        }

        return drinks;
    }

    public List<String> getChips() {
        chips.clear();
        for (Item item: inventory) {
            if (item.getCategory().equalsIgnoreCase("chips")) {
                chips.add(item.getName());
            }
        }

        return chips;
    }

    public List<String> getChocolates() {
        chocolates.clear();
        for (Item item: inventory) {
            if (item.getCategory().equalsIgnoreCase("chocolate")) {
                chocolates.add(item.getName());
            }
        }

        return chocolates;
    }

    public List<String> getCandies() {
        candies.clear();
        for (Item item: inventory) {
            if (item.getCategory().equalsIgnoreCase("candies")) {
                candies.add(item.getName());
            }
        }

        return candies;
    }

    public HashMap<String, List<String>> getCategories() {
        return categories;
    }
}
