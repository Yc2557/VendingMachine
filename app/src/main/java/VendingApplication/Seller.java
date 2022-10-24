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

    public void modifyName(String currentName, String newName) {
        JSONObject inventoryJSON = inventory.getJSON("src/main/resources/data/inventory.json");
        JSONArray products = (JSONArray) inventoryJSON.get("products");

//        System.out.println(currentName + "->" + newName);

        for (Object product : products) {
            JSONObject productObj = (JSONObject) product;
            if (currentName.equals(productObj.get("name"))) {
                System.out.println("BINGO!");
                productObj.put("name", newName);
                products.add(productObj);
                break;
            }
        }

        inventoryJSON.put("products", products);
        inventory.writeJsonFile("src/main/resources/data/inventory.json", inventoryJSON);
    }

//    public void modifyName(String currentName, String newName) {
//        JSONObject database = readJSON(filepath);
//        JSONArray products = (JSONArray) database.get("products");
//
//        JSONObject item = null;
//        for (int i=0; i<products.size(); i++) {
//            JSONObject product = (JSONObject) products.get(i);
//            if (currentName.equals(product.get("name"))) {
//                item = product;
//                break;
//            }
//        }
//
//        item.put("name", newName);
//    }

//    public void modifyCode(String currentCode, String newCode) {
//        JSONObject database = readJSON(filepath);
//        JSONArray products = (JSONArray) database.get("products");
//
//        JSONObject item = null;
//        for (int i=0; i<products.size(); i++) {
//            JSONObject product = (JSONObject) products.get(i);
//            if (currentCode.equals(product.get("code"))) {
//                item = product;
//                break;
//            }
//        }
//
//        item.put("code", newCode);
//    }
//
//    public void modifyCategory(String currentCategory, String newCategory) {
//        JSONObject database = readJSON(filepath);
//        JSONArray products = (JSONArray) database.get("products");
//
//        JSONObject item = null;
//        for (int i=0; i<products.size(); i++) {
//            JSONObject product = (JSONObject) products.get(i);
//            if (currentCategory.equals(product.get("category"))) {
//                item = product;
//                break;
//            }
//        }
//
//        item.put("code", newCategory);
//    }

}
