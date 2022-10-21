package VendingApplication;

import java.io.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

public class UserManager {
    private final String filePath = "src/main/resources/data/user.json";

    /**
     * Finds the user JSONObject based on username.
     * 
     * @param username the username of the user
     * @return user's JSONObject
     */
    public JSONObject findUser(String username) {
        try {
            FileReader reader = new FileReader(filePath);
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
            JSONArray users = (JSONArray) jsonObject.get("users");

            for (Object user : users) {
                JSONObject userObject = (JSONObject) user;
                String userUsername = (String) userObject.get("username");
                if (userUsername.equals(username)) {
                    System.out.println("User found");
                    return userObject;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
            throw new RuntimeException(e);
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public String getPassword(String username) {
        JSONObject user = findUser(username);
        return (String) user.get("password");
    }

    public String getUsername(String username) {
        JSONObject user = findUser(username);
        return (String) user.get("username");
    }

    public boolean addUser(String username, String password) {
        // Check that both username and password are not null
        if (username == null || password == null) {
            return false;
        }
        // Check is username already present in database
        if (findUser(username) != null) {
            return false;
        }

        try {
            FileReader reader = new FileReader(filePath);
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
            JSONArray users = (JSONArray) jsonObject.get("users");

            JSONObject newUser = new JSONObject();
            newUser.put("username", username);
            newUser.put("password", password);
            newUser.put("creditCard", "");
            newUser.put("purchaseHistory", "");
            users.add(newUser);

            FileWriter file = new FileWriter(filePath);
            file.write(jsonObject.toJSONString());
            file.flush();
            file.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
            throw new RuntimeException(e);
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }

        return true;
    }
}
