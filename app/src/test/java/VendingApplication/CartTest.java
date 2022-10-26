package VendingApplication;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class CartTest {
    @Test
    public void addItemTest() {
        Cart cart = new Cart();
        Item item = new Item("1001", "Mineral Water", "Drinks", 1, 4);
        cart.addItem(item);
        assertEquals(cart.getCart(), List.of(item));
    }

    @Test
    public void removeItemTest() {
        // Single Item Removal
        Cart cart = new Cart();
        Item mineralWater = new Item("1001", "Mineral Water", "Drinks", 3, 4);
        cart.addItem(mineralWater);
        cart.removeItem(mineralWater);
        assertEquals(cart.getCart(), List.of());
        // Going to have to re-assess how amounts are stored in the cart

    }

    @Test
    public void clearCartTest() {
        Cart cart = new Cart();
        Item mineralWater = new Item("1001", "Mineral Water", "Drinks", 3, 4);
        // Mass Item Removal through .clearCart()
        Item sprite = new Item("1002", "Sprite", "Drinks", 3, 5);
        cart.addItem(sprite);
        cart.addItem(mineralWater);
        // Checking that they have been added in
        assertEquals(cart.getCart(), Arrays.asList(sprite, mineralWater));
        cart.clearCart();
        // Checking their removal
        assertEquals(cart.getCart(), List.of());
    }

    @Test
    public void getItemTest() {
        Cart cart = new Cart();
        Item mineralWater = new Item("1001", "Mineral Water", "Drinks", 3, 4);
        cart.addItem(mineralWater);
        assertEquals(mineralWater, cart.getItem(mineralWater));
    }

    @Test
    public void totalCartPriceTest() {
        Cart cart = new Cart();
        Item mineralWater = new Item("1001", "Mineral Water", "Drinks", 3, 4);
        Item sprite = new Item("1002", "Sprite", "Drinks", 3, 5);
        cart.addItem(mineralWater);
        cart.addItem(sprite);
        assertEquals(27, cart.totalCartPrice());
    }
}
