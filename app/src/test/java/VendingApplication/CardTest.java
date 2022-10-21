package VendingApplication;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class CardTest {

    @Test
    public void invalidCardTest() {
        CardHandler handler = new CardHandler();
        String cardName = "@invalid";
        String cardNum = "9876123";
        handler.checkCreditCard(cardName, cardNum);
        assertFalse(handler.isValidCard());
    }

    @Test
    public void validCardTest() {
        CardHandler handler = new CardHandler();
        String name = "m";
        String num = "1";
        handler.checkCreditCard(name, num);
        assertTrue(handler.isValidCard());
    }

    @Test
    public void overrideValidTest() {
        CardHandler handler = new CardHandler();
        handler.validateCard();
        assertTrue(handler.isValidCard());
    }

    @Test
    public void overrideInvalidTest() {
        CardHandler handler = new CardHandler();
        handler.invalidateCard();
        assertFalse(handler.isValidCard());
    }
}