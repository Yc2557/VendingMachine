package VendingApplication;

public class Item {

    private final String itemid;
    private final String name;
    private final String category;
    private int amount;
    private double price;


    public Item(String itemid, String name, String category, double price, int amount) {
        this.itemid = itemid;
        this.name = name;
        this.category = category;
        this.price = price;
        this.amount = amount;
    }

    public String getItemid() {
        return itemid;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public int getAmount() {
        return amount;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double totalPrice(int quantity) {
        return price * quantity;
    }

    public void addAmount(int amount) {
        this.amount += amount;
    }
}
