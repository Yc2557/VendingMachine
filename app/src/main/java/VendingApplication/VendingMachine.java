package VendingApplication;

import org.checkerframework.checker.units.qual.C;

public class VendingMachine {
    private Inventory inventory;
    private Cart cart;

    public VendingMachine() {
        this.inventory = new Inventory();
        this.cart = new Cart();
    }

    /*
    Methods:
    - Adds to cart from inventory
    - Remove from cart
     */

    public Inventory getInventory() {
        return inventory;
    }

    public Cart getCart() {
        return cart;
    }
}
