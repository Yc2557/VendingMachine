package VendingApplication;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

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
        user1.put("userRole", "customer");
        users.add(user1);

        JSONObject user2 = new JSONObject();
        user2.put("username", "user2");
        user2.put("password", "pass");
        user2.put("purchaseHistory", "");
        user2.put("cardName", "");
        user2.put("cardNumber", "");
        user2.put("userRole", "customer");
        users.add(user2);

        JSONObject user3 = new JSONObject();
        user3.put("username", "user3");
        user3.put("password", "pass");
        user3.put("purchaseHistory", "");
        user3.put("cardName", "bananas");
        user3.put("cardNumber", "no");
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
        CardHandler handler = new CardHandler("src/test/resources/credit_cardsTest.json", "src/test/resources/usersCardTest.json");
        String cardName = "@invalid";
        String cardNum = "9876123";
        handler.checkCreditCard(cardName, cardNum);
        assertFalse(handler.isValidCard());
    }

    @Test
    public void validCardTest() {
        CardHandler handler = new CardHandler("src/test/resources/credit_cardsTest.json", "src/test/resources/usersCardTest.json");
        String name = "m";
        String num = "1";
        handler.checkCreditCard(name, num);
        assertTrue(handler.isValidCard());
    }

    @Test
    public void overrideValidTest() {
        CardHandler handler = new CardHandler("src/test/resources/credit_cardsTest.json", "src/test/resources/usersCardTest.json");
        handler.validateCard();
        assertTrue(handler.isValidCard());
    }

    @Test
    public void overrideInvalidTest() {
        CardHandler handler = new CardHandler("src/test/resources/credit_cardsTest.json", "src/test/resources/usersCardTest.json");
        handler.invalidateCard();
        assertFalse(handler.isValidCard());
    }

    @Test
    public void suggestNoneTest() {
        CardHandler handler = new CardHandler("src/test/resources/credit_cardsTest.json", "src/test/resources/usersCardTest.json");
        String name = "user-0";
        assertNull(handler.findCard(name));
    }

    @Test
    public void suggestCardTest() {
        CardHandler handler = new CardHandler("src/test/resources/credit_cardsTest.json", "src/test/resources/usersCardTest.json");
        String name = "user1";
        assertEquals("m", handler.findCard(name));
    }

    @Test
    public void saveCardTest() {
        CardHandler handler = new CardHandler("src/test/resources/credit_cardsTest.json", "src/test/resources/usersCardTest.json");
        handler.saveCardDetails("user3", "newcard", "123");
        assertEquals("newcard", handler.findCard("user3"));
    }

}