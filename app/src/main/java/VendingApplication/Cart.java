package VendingApplication;

import java.util.List;
import java.util.ArrayList;

public class Cart {
    private List<Item> cart;

    public Cart() {
        this.cart = new ArrayList<Item>();
    }

    public double totalCartPrice() {
        double price = 0;

        for (Item i : cart) {
            price += i.getPrice() * i.getAmount();
        }

        return price;
    }

    public void addItem(Item item) {
        cart.add(item);
    }

    public void removeItem(Item item) {
        cart.remove(item);
    }

    public List<Item> getCart() {
        return cart;
    }

    public Item getItem(Item item) {
        for (Item i : cart) {
            if (i.getItemid().equals(item.getItemid())) {
                return i;
            }
        }
        return null;
    }

    public void clearCart() {
        cart.clear();
    }
}
