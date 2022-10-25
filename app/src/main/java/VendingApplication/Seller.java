package VendingApplication;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
REQUIREMENTS
------------

- Able to fill and modify items
    - Must be able to select and modify item details (i.e. item name, code, category, quantity and price)
    - Error message if quantity added is over the limit (15 of each item/product) OR conflicting code/name/category
- Obtain two reports when logging in (either csv or text file)
    - List of current available items including item details
    - Summary including item codes and names, as well as total quantity sold for each (i.e. 1001; Mineral Water; 12)
 */

public class Seller {

    private Inventory inventory;

    public Seller() {
        this.inventory = new Inventory();
    }

    public JSONObject readJSON(String filepath) {
        try {
            JSONParser jsonParser = new JSONParser();
            JSONObject database = (JSONObject) jsonParser.parse(new FileReader(filepath));
            return database;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("File not found");
        }
        catch (IOException | ParseException e) {
            e.printStackTrace();
            System.out.println("Error reading file");
        }
        return null;
    }

    // No duplicate products test case
    public void modifyName(String currentName, String newName) {
        JSONObject inventoryJSON = inventory.getJSON("src/main/resources/data/inventory.json");
        JSONArray products = (JSONArray) inventoryJSON.get("products");

        for (Object product : products) {
            JSONObject productObj = (JSONObject) product;
            if (currentName.equals(productObj.get("name"))) {
                productObj.put("name", newName);
                products.add(productObj);
                break;
            }
        }

        inventoryJSON.put("products", products);
        inventory.writeJsonFile("src/main/resources/data/inventory.json", inventoryJSON);
    }

    // No duplicate ids test case
    public boolean modifyId(String currentId, String newId) {
        JSONObject inventoryJSON = inventory.getJSON("src/main/resources/data/inventory.json");
        JSONArray products = (JSONArray) inventoryJSON.get("products");
        String id;

        for (Object product : products) {
            JSONObject productObj = (JSONObject) product;
            id = Long.toString((Long) productObj.get("id"));
            if (currentId.equals(id)) {
                productObj.put("id", Long.valueOf(newId));
                products.add(productObj);
                break;
            }
        }

        inventoryJSON.put("products", products);
        inventory.writeJsonFile("src/main/resources/data/inventory.json", inventoryJSON);
        return true;
    }

    public boolean modifyCategory(String currentCategory, String newCategory) {
        JSONObject inventoryJSON = inventory.getJSON("src/main/resources/data/inventory.json");
        JSONArray products = (JSONArray) inventoryJSON.get("products");
        List<String> categories = Arrays.asList("drinks", "chips", "chocolates", "candies");

        if (!categories.contains(newCategory)) {
            return false;
        }

        for (Object product : products) {
            JSONObject productObj = (JSONObject) product;
            if (currentCategory.equals(productObj.get("category"))) {
                productObj.put("category", newCategory);
                products.add(productObj);
                break;
            }
        }

        inventoryJSON.put("products", products);
        inventory.writeJsonFile("src/main/resources/data/inventory.json", inventoryJSON);
        return true;
    }

    public boolean modifyPrice(String name, String newPrice) {
        JSONObject inventoryJSON = inventory.getJSON("src/main/resources/data/inventory.json");
        JSONArray products = (JSONArray) inventoryJSON.get("products");

        // Invalid price
        if (Double.valueOf(newPrice) <= 0) {
            return false;
        }

        for (Object product : products) {
            JSONObject productObj = (JSONObject) product;
            if (name.equals(productObj.get("name"))) {
                productObj.put("price", Double.valueOf(newPrice));
                products.add(productObj);
                break;
            }
        }

        inventoryJSON.put("products", products);
        inventory.writeJsonFile("src/main/resources/data/inventory.json", inventoryJSON);
        return true;
    }

    public boolean modifyQuantity(String name, String newQuantity) {
        JSONObject inventoryJSON = inventory.getJSON("src/main/resources/data/inventory.json");
        JSONArray products = (JSONArray) inventoryJSON.get("products");

        if (Long.valueOf(newQuantity) < 0 || Long.valueOf(newQuantity) > 15) {
            return false;
        }

        for (Object product : products) {
            JSONObject productObj = (JSONObject) product;
            if (name.equals(productObj.get("name"))) {
                productObj.put("quantity", Long.valueOf(newQuantity));
                products.add(productObj);
                break;
            }
        }

        inventoryJSON.put("products", products);
        inventory.writeJsonFile("src/main/resources/data/inventory.json", inventoryJSON);
        return true;
    }

    // Name Code Category Quantity Price

}
