package VendingApplication;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

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

    public Seller(Inventory inventory) {
        this.inventory = inventory;
    }

    public boolean modifyName(String currentName, String newName) {
        Item item = inventory.getItem(currentName);

        if (inventory.exists(newName, "name")) {
            return false;
        }
        if (item != null) {
            item.setName(newName);
        }
        inventory.writeJsonFile("src/main/resources/data/inventory.json");
        return true;
    }

    public boolean modifyId(String currentName, String newCode) {
        Item item = inventory.getItem(currentName);

        if (inventory.exists(newCode, "id")) {
            return false;
        }
        if (item != null) {
            item.setItemid(newCode);
        }
        inventory.writeJsonFile("src/main/resources/data/inventory.json");
        return true;
    }

    public boolean modifyCategory(String currentName, String newCategory) {

        if (!inventory.getCategories().containsKey(newCategory)) {
            return false;
        }

        Item item = inventory.getItem(currentName);
        if (item != null) {
            item.setCategory(newCategory);
        }
        inventory.writeJsonFile("src/main/resources/data/inventory.json");
        return true;
    }

    public boolean modifyQuantity(String currentName, String newQuantity) {
        if (Integer.parseInt(newQuantity) > 15 || Integer.parseInt(newQuantity) < 0) {
            return false;
        }

        Item item = inventory.getItem(currentName);
        if (item != null) {
            item.setAmount(Integer.parseInt(newQuantity));
        }
        inventory.writeJsonFile("src/main/resources/data/inventory.json");
        return true;
    }

    public boolean modifyPrice(String currentName, String newPrice) {
        if (Double.parseDouble(newPrice) < 0) {
            return false;
        }

        Item item = inventory.getItem(currentName);
        if (item != null) {
            item.setPrice(Double.parseDouble(newPrice));
        }
        inventory.writeJsonFile("src/main/resources/data/inventory.json");

        return true;
    }

}
