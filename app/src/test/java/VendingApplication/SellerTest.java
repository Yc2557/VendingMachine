package VendingApplication;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

public class SellerTest {

    // Reads in the inventory JSON file
    @BeforeAll
    public static void writeJsonFile() {
        try {
            JSONObject inventoryJson = new JSONObject();
            JSONArray products = new JSONArray();

            // Mineral Water
            JSONObject mineralWater = new JSONObject();
            mineralWater.put("quantity", 7);
            mineralWater.put("price", 1.5);
            mineralWater.put("name", "Mineral Water");
            mineralWater.put("id", "0");
            mineralWater.put("category", "drinks");

            // Sprite
            JSONObject sprite = new JSONObject();
            sprite.put("quantity", 7);
            sprite.put("price", (double) 1.5);
            sprite.put("name", "Sprite");
            sprite.put("id", "1");
            sprite.put("category", "drinks");

            // M&M
            JSONObject mnm = new JSONObject();
            mnm.put("quantity", 7);
            mnm.put("price", (double) 2);
            mnm.put("name", "M&M");
            mnm.put("id", "2");
            mnm.put("category", "chocolate");

            // Smiths
            JSONObject smiths = new JSONObject();
            smiths.put("quantity", 7);
            smiths.put("price", (double) 1);
            smiths.put("name", "Smiths");
            smiths.put("id", "3");
            smiths.put("category", "chips");

            products.add(mineralWater);
            products.add(sprite);
            products.add(mnm);
            products.add(smiths);

            inventoryJson.put("products", products);

            File file = new File("src/test/resources/sellerTest.json");
            file.createNewFile();
            FileWriter writer = new FileWriter(file);
            writer.write(inventoryJson.toJSONString());
            writer.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    public void nameModifyTest() {
        Inventory inventory = new Inventory();
        inventory.readJsonFile("src/test/resources/sellerTest.json");
        Seller seller = new Seller(inventory);
        seller.setInventoryFilePath("src/test/resources/sellerTest.json");

        // Changing a nameass
        assertFalse(inventory.exists("Pepsi", "name"));
        seller.modifyName("Sprite", "Pepsi");
        assertTrue(inventory.exists("Pepsi", "name"));

        // Preventing duplicates
        assertFalse(seller.modifyName("M&M", "M&M"));
    }

    @Test
    public void categoryModifyTest() {
        Inventory inventory = new Inventory();
        inventory.readJsonFile("src/test/resources/sellerTest.json");
        Seller seller = new Seller(inventory);
        seller.setInventoryFilePath("src/test/resources/sellerTest.json");

        // Changing the category
        assertTrue(inventory.getItem("M&M", "name").getCategory().equals("chocolate"));
        seller.modifyCategory("M&M", "drinks");
        assertTrue(inventory.getItem("M&M", "name").getCategory().equals("drinks"));

        // Invalid category
        assertFalse(seller.modifyCategory("M&M", "paper"));
    }

    @Test
    public void quantityModifyTest() {
        Inventory inventory = new Inventory();
        inventory.readJsonFile("src/test/resources/sellerTest.json");
        Seller seller = new Seller(inventory);
        seller.setInventoryFilePath("src/test/resources/sellerTest.json");

        // Changing the quantity
        assertTrue(inventory.getItem("Smiths", "name").getAmount() == 7);
        seller.modifyQuantity("Smiths", "10");
        assertTrue(inventory.getItem("Smiths", "name").getAmount() == 10);

        // Invalid quantities
        assertFalse(seller.modifyQuantity("Smiths", "19"));
        assertFalse(seller.modifyQuantity("Smiths", "-2"));
    }

    @Test
    public void priceModifyTest() {
        Inventory inventory = new Inventory();
        inventory.readJsonFile("src/test/resources/sellerTest.json");
        Seller seller = new Seller(inventory);
        seller.setInventoryFilePath("src/test/resources/sellerTest.json");

        // Changing the price
        assertTrue(inventory.getItem("Mineral Water", "name").getPrice() == 1.5);
        seller.modifyPrice("Mineral Water", "2.0");
        assertTrue(inventory.getItem("Mineral Water", "name").getPrice() == 2.0);

        // Invalid price
        assertFalse(seller.modifyPrice("Mineral Water", "-1.0"));
    }
}
