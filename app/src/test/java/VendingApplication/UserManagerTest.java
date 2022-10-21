/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package VendingApplication;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserManagerTest {
    @BeforeAll
    public static void buildJson() throws IOException {
        int i = 0;

        JSONObject database = new JSONObject();
        JSONArray users = new JSONArray();

        JSONObject itemObj = new JSONObject();
        itemObj.put("username", "user");
        itemObj.put("purchaseHistory", "");
        itemObj.put("cardName", "Joe");
        itemObj.put("cardNumber", "10000");
        itemObj.put("password", "password");

        users.add(itemObj);

        JSONObject itemObj2 = new JSONObject();

        itemObj2.put("username", "user2");
        itemObj2.put("purchaseHistory", "");
        itemObj2.put("cardName", "Joe");
        itemObj2.put("cardNumber", "10000");
        itemObj2.put("password", "password");

        users.add(itemObj2);

        database.put("users", users);

        File file = new File("src/test/resources/userManagerTest.json");
        file.createNewFile();
        FileWriter writer = new FileWriter(file);
        writer.write(database.toJSONString());

        writer.close();
    }

    @Test
    public void getInformationTest() {
        UserManager userManager = new UserManager("src/test/resources/userManagerTest.json");
        assertEquals("Joe", userManager.getCardName("user"));
        assertEquals("10000", userManager.getCardNumber("user"));
        assertEquals("password", userManager.getPassword("user"));
        assertEquals("user", userManager.getUsername("user"));
    }

    @Test
    public void addUserTest() {
        UserManager userManager = new UserManager("src/test/resources/userManagerTest.json");
        assertTrue(userManager.addUser("user3", "password"));
        assertFalse(userManager.addUser("user3", "password"));
        assertFalse(userManager.addUser("user", "password"));
        assertFalse(userManager.addUser("user", null));
        assertFalse(userManager.addUser(null, "password"));
        assertFalse(userManager.addUser("  ", "password"));
    }

    @Test
    public void addCreditCardTest() {
        UserManager userManager = new UserManager("src/test/resources/userManagerTest.json");
        assertTrue(userManager.addCreditCard("user", "Joe", "10000"));
        assertFalse(userManager.addCreditCard(null, "Joe", "10000"));
        assertFalse(userManager.addCreditCard("user", null, "10000"));
        assertFalse(userManager.addCreditCard("user", "Joe", null));

    }
}
