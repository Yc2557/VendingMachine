package VendingApplication;

import org.checkerframework.checker.units.qual.C;

public class VendingMachine {
    private Inventory inventory;
    private Cart cart;

    public boolean isLogin = false;
    private Account account = null;

    public VendingMachine() {
        this.inventory = new Inventory();
        this.cart = new Cart();

        inventory.readJsonFile("src/main/resources/data/inventory.json");
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

    public void addAccount(Account account) {
        this.isLogin = true;
    	this.account = account;
    }

    public Account getAccount() {
    	return this.account;
    }

    public void logOut() {
    	this.isLogin = false;
        this.account = null;
    }
}
