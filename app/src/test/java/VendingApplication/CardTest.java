package VendingApplication;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;

public class CardTest {

    @BeforeAll
    public static void setupJSON() throws IOException {

        JSONObject db = new JSONObject();
        JSONArray users = new JSONArray();

        JSONObject user1 = new JSONObject();
        user1.put("username", "user1");
        user1.put("password", "pass");
        user1.put("purchaseHistory", "");
        user1.put("cardName", "m");
        user1.put("cardNumber", "1");
        user1.put("CVV", "123");
        user1.put("expiryDate", "12/2");
        user1.put("userRole", "customer");
        users.add(user1);

        JSONObject user2 = new JSONObject();
        user2.put("username", "user2");
        user2.put("password", "pass");
        user2.put("purchaseHistory", "");
        user2.put("cardName", "");
        user2.put("cardNumber", "");
        user2.put("CVV", "");
        user2.put("expiryDate", "");
        user2.put("userRole", "customer");
        users.add(user2);

        JSONObject user3 = new JSONObject();
        user3.put("username", "user3");
        user3.put("password", "pass");
        user3.put("purchaseHistory", "");
        user3.put("cardName", "bananas");
        user3.put("cardNumber", "no");
        user3.put("CVV", "0");
        user3.put("expiryDate", "22/2");
        user3.put("userRole", "customer");
        users.add(user3);

        db.put("users", users);
        File file = new File("src/test/resources/usersCardTest.json");
        file.createNewFile();
        FileWriter writer = new FileWriter(file);
        writer.write(db.toJSONString());
        writer.close();
    }

    @Test
    public void invalidCardTest() {
        CardHandler handler = new CardHandler("src/test/resources/credit_cardsTest.json");
        handler.setUserManager("src/test/resources/usersCardTest.json");
        String cardName = "@invalid";
        String cardNum = "9876123";
        String CVV = "122";
        String expiryDate = "12/1";
        handler.checkCreditCard(cardName, cardNum, expiryDate, CVV);
        assertFalse(handler.isValidCard());
    }

    @Test
    public void validCardTest() {
        CardHandler handler = new CardHandler("src/test/resources/credit_cardsTest.json");
        handler.setUserManager("src/test/resources/usersCardTest.json");
        handler.addNewValidCard("mkmky", "12345", "1/10", "999");
        handler.checkCreditCard("mkmky", "12345", "1/10", "999");
        assertTrue(handler.isValidCard());
    }

    @Test
    public void overrideValidTest() {
        CardHandler handler = new CardHandler("src/test/resources/credit_cardsTest.json");
        handler.setUserManager("src/test/resources/usersCardTest.json");
        handler.validateCard();
        assertTrue(handler.isValidCard());
    }

    @Test
    public void overrideInvalidTest() {
        CardHandler handler = new CardHandler("src/test/resources/credit_cardsTest.json");
        handler.setUserManager("src/test/resources/usersCardTest.json");
        handler.invalidateCard();
        assertFalse(handler.isValidCard());
    }

    @Test
    public void suggestNoneTest() {
        CardHandler handler = new CardHandler("src/test/resources/credit_cardsTest.json");
        handler.setUserManager("src/test/resources/usersCardTest.json");
        String name = "user-0";
        assertNull(handler.findCard(name));
    }

    @Test
    public void suggestCardTest() {
        CardHandler handler = new CardHandler("src/test/resources/credit_cardsTest.json");
        handler.setUserManager("src/test/resources/usersCardTest.json");
        assertTrue(handler.addNewValidCard("m", "1", "12/2", "123"));
        String name = "user1";
        assertEquals("m", handler.findCard(name));
    }

    @Test
    public void saveCardTest() {
        CardHandler handler = new CardHandler("src/test/resources/credit_cardsTest.json");
        handler.setUserManager("src/test/resources/usersCardTest.json");
        handler.saveCardDetails("user3", "newcard", "123", "12/2", "321");
        assertEquals("newcard", handler.findCard("user3"));
    }

    @Test
    public void checkCVVTest() {
        CardHandler handler = new CardHandler("src/test/resources/credit_cardsTest.json");
        handler.setUserManager("src/test/resources/usersCardTest.json");
        assertTrue(handler.checkCVV("123"));
        assertFalse(handler.checkCVV("MKMKY"));
    }

    @Test
    public void checkExpiryTest() {
        CardHandler handler = new CardHandler("src/test/resources/credit_cardsTest.json");
        handler.setUserManager("src/test/resources/usersCardTest.json");
        assertTrue(handler.checkExpiry("12/11"));
        assertFalse(handler.checkExpiry(""));
    }

    @Test
    public void checkNumberTest() {
        CardHandler handler = new CardHandler("src/test/resources/credit_cardsTest.json");
        handler.setUserManager("src/test/resources/usersCardTest.json");
        assertTrue(handler.checkCardNumber("01234"));
        assertFalse(handler.checkCardNumber("abc123"));
    }

}