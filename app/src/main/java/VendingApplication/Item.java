package VendingApplication;

public class Item {

    private String itemid;
    private String name;
    private String category;
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

    public void setName(String newName) {
        this.name = newName;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setItemid(String itemid) {
        this.itemid = itemid;
    }

}
