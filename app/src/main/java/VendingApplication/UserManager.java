package VendingApplication;

import java.io.*;
import java.util.List;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

public class UserManager {
    private String filePath;

    public UserManager(String filePath) {
        this.filePath = filePath;
    }

    public UserManager() {
        this.filePath = "src/main/resources/data/user.json";
    }

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

    public String getCardName(String username) {
        JSONObject user = findUser(username);
        return (String) user.get("cardName");
    }

    public String getCardNumber(String username) {
        JSONObject user = findUser(username);
        return (String) user.get("cardNumber");
    }

    public String getRole(String username) {
        JSONObject user = findUser(username);
        return (String) user.get("userRole");
    }

    public List<String> getHistory(String username) {
        JSONObject user = findUser(username);
        List<String> history = new ArrayList<String>();
        JSONArray purchaseHistory = (JSONArray) user.get("purchaseHistory");
        for (int i = 0; i < purchaseHistory.size(); i++) {
            String purchase = (String) purchaseHistory.get(i);
            history.add(purchase);
        }
        return history;
    }

    public boolean addUser(String username, String password, String role) {
        // Check that both username and password are not null
        if (username == null || username.contains(" ") || password == null || role == null) {
            return false;
        }
        // Check if username already present in database
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
            newUser.put("cardName", "");
            newUser.put("cardNumber", "");
            newUser.put("purchaseHistory", new JSONArray());
            newUser.put("userRole", role);
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

    public boolean addCreditCard(String username, String cardName, String cardNumber) {
        // Check that both username and password are not null
        if (username == null || cardName == null || cardNumber == null) {
            return false;
        }

        try {
            FileReader reader = new FileReader(filePath);
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
            JSONArray users = (JSONArray) jsonObject.get("users");

            for (Object user : users) {
                JSONObject userObject = (JSONObject) user;
                String userUsername = (String) userObject.get("username");
                if (userUsername.equals(username)) {
                    userObject.put("cardName", cardName);
                    userObject.put("cardNumber", cardNumber);
                }
            }

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

    public boolean addHistory(String username, List<String> history) {
        // Check that history is not null
        if (username == null | history == null) {
            return false;
        }

        try {
            FileReader reader = new FileReader(filePath);
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
            JSONArray users = (JSONArray) jsonObject.get("users");

            for (Object user : users) {
                JSONObject userObject = (JSONObject) user;
                String userUsername = (String) userObject.get("username");
                if (userUsername.equals(username)) {
                    JSONArray newHistory = new JSONArray();
                    for (String item : history) {
                        newHistory.add(item);
                    }
                    userObject.put("purchaseHistory", newHistory);
                }
            }

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
