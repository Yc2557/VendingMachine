package VendingApplication;

public class Item {
    private String itemid;
    private String name;
    private double price;

    public Item(String itemid, String name, double price) {
        this.itemid = itemid;
        this.name = name;
        this.price = price;
    }

    public double totalPrice(int qty) {
        return qty * price;
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
}
