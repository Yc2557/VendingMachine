import java.util.HashMap;

public class Inventory {
    private HashMap<Item, Integer> inventory;

    public Inventory() {
        inventory = new HashMap<>();
    }

    public void readJsonFile(String filePath) { //get inventory data

    }

    public void writeJsonFile(String filePath) { //write inventory data


    }

    public void addAmount(Item item, int quantity) {
        if (inventory.containsKey(item)) {
            inventory.put(item, inventory.get(item) + quantity);
        } else {
            raiseError("Item not found");
        }
    }

    public void addItem(Item item, int quantity) {
        if (inventory.containsKey(item)) {
            raiseError("Item already exists");
        } else {
            inventory.put(item, quantity);
        }
    }
}
