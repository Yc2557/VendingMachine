package VendingApplication;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InventoryTest {

    @BeforeAll
    public static void buildJson() throws IOException {
        int i = 0;

        JSONObject database = new JSONObject();
        JSONArray products = new JSONArray();

        List<String> drinksList = List.of("Mineral Water", "Sprite");
        List<String> chocolateList = List.of("Mars", "M&M");
        List<String> chipsList = List.of("Smiths");
        List<String> candiesList = List.of("Skittles", "Sour Patch");

        for (String item: drinksList) {
            JSONObject itemObj = new JSONObject();
            itemObj.put("name", item);
            itemObj.put("id", String.valueOf(i));
            itemObj.put("price", 1.5);
            itemObj.put("quantity", 7);
            itemObj.put("category", "drinks");
            i++;
            products.add(itemObj);
        }

        for (String item: chocolateList) {
            JSONObject itemObj = new JSONObject();
            itemObj.put("name", item);
            itemObj.put("id", String.valueOf(i));
            itemObj.put("price", 1.5);
            itemObj.put("quantity", 7);
            itemObj.put("category", "chocolate");
            i++;
            products.add(itemObj);
        }

        for (String item: chipsList) {
            JSONObject itemObj = new JSONObject();
            itemObj.put("name", item);
            itemObj.put("id", String.valueOf(i));
            itemObj.put("price", 1.5);
            itemObj.put("quantity", 7);
            itemObj.put("category", "chips");
            i++;
            products.add(itemObj);
        }

        for (String item: candiesList) {
            JSONObject itemObj = new JSONObject();
            itemObj.put("name", item);
            itemObj.put("id", String.valueOf(i));
            itemObj.put("price", 1.5);
            itemObj.put("quantity", 7);
            itemObj.put("category", "candies");
            i++;
            products.add(itemObj);
        }

        database.put("products", products);

        File file = new File("src/test/resources/inventoryTest.json");
        file.createNewFile();
        FileWriter writer = new FileWriter(file);
        writer.write(database.toJSONString());

        writer.close();

    }

    @Test
    public void readJsonTest() {
        Inventory inventory = new Inventory();
        List<String> productsList = List.of("Mineral Water", "Sprite", "Mars", "M&M", "Smiths", "Skittles", "Sour Patch");
        int i = 0;

        inventory.readJsonFile("src/test/resources/inventoryTest.json");

        for (Item item: inventory.getInventory()) {
            assertEquals(productsList.get(i), item.getName());
            assertEquals(7, item.getAmount());
            assertEquals(1.5, item.getPrice());
            i++;
        }
    }

    @Test
    public void getItemTest() {
        Inventory inventory = new Inventory();
        inventory.readJsonFile("src/test/resources/inventoryTest.json");
        List<Item> inventoryList = inventory.getInventory();

        assertEquals(inventoryList.get(0), inventory.getItem("Mineral Water"));
        assertEquals(inventoryList.get(3), inventory.getItem("M&M"));
        assertEquals(inventoryList.get(6), inventory.getItem("Sour Patch"));
    }

    @Test
    public void addItemTest() {
        Inventory inventory = new Inventory();
        inventory.readJsonFile("src/test/resources/inventoryTest.json");
        List<Item> inventoryList = inventory.getInventory();

        Item newItem = new Item("8", "C", "drinks", 2, 7);
        inventory.addItem(newItem);
        assertEquals(newItem, inventoryList.get(7));
    }

    @Test
    public void removeItemTest() {
        Inventory inventory = new Inventory();
        inventory.readJsonFile("src/test/resources/inventoryTest.json");
        List<Item> inventoryList = inventory.getInventory();

        int size = inventoryList.size();
        inventory.removeItem(inventoryList.get(0));

        assertEquals(size - 1, inventoryList.size());
        assertEquals(inventoryList.get(0), inventory.getItem("Sprite"));
    }

    @Test
    public void getDrinksTest() {
        Inventory inventory = new Inventory();
        inventory.readJsonFile("src/test/resources/inventoryTest.json");

        List<String> drinksList = List.of("Mineral Water", "Sprite");

        assertEquals(drinksList, inventory.getDrinks());
    }

    @Test
    public void getChocolateTest() {
        Inventory inventory = new Inventory();
        inventory.readJsonFile("src/test/resources/inventoryTest.json");

        List<String> chocolateList = List.of("Mars", "M&M");

        assertEquals(chocolateList, inventory.getChocolates());
    }

    @Test
    public void getChipsTest() {
        Inventory inventory = new Inventory();
        inventory.readJsonFile("src/test/resources/inventoryTest.json");

        List<String> chipsList = List.of("Smiths");

        assertEquals(chipsList, inventory.getChips());
    }

    @Test
    public void getCandiesTest() {
        Inventory inventory = new Inventory();
        inventory.readJsonFile("src/test/resources/inventoryTest.json");

        List<String> candiesList = List.of("Skittles", "Sour Patch");

        assertEquals(candiesList, inventory.getCandies());
    }
}
