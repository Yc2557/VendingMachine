public class Item {

    private String itemid;
    private String name;
    private double price;


    public Item(String itemid, String name, double price, int quantity) {
        this.itemid = itemid;
        this.name = name;
        this.price = price;
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

    public void setPrice(double price) {
        this.price = price;
    }

    public double totalPrice(int quantity) {
        return price * quantity;
    }
}
