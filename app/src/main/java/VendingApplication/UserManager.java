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
        if (user != null) {
            return (String) user.get("password");
        }
        return null;
    }

    public String getUsername(String username) {
        JSONObject user = findUser(username);
        if (user != null) {
            return (String) user.get("username");
        }
        return null;
    }

    public String getCardName(String username) {
        JSONObject user = findUser(username);
        if (user != null) {
            return (String) user.get("cardName");
        }
        return null;
    }

    public String getCardNumber(String username) {
        JSONObject user = findUser(username);
        if (user != null) {
            return (String) user.get("cardNumber");
        }
        return null;
    }

    public String getExpiryDate(String username) {
        JSONObject user = findUser(username);
        if (user != null) {
            return (String) user.get("expiryDate");
        }
        return null;
    }

    public String getCVV(String username) {
        JSONObject user = findUser(username);
        if (user != null) {
            return (String) user.get("CVV");
        }
        return null;
    }

    public String getRole(String username) {
        JSONObject user = findUser(username);
        if (user != null) {
            return (String) user.get("userRole");
        }
        return null;
    }

    public List<String> getHistory(String username) {
        JSONObject user = findUser(username);
        List<String> history = new ArrayList<>();
        JSONArray purchaseHistory = (JSONArray) user.get("purchaseHistory");
        for (Object o : purchaseHistory) {
            String purchase = (String) o;
            System.out.println(purchase);
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
            newUser.put("CVV", "");
            newUser.put("expiryDate", "");
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

    public boolean addCreditCard(String username, String cardName, String cardNumber, String expiryDate, String CVV) {
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
                    userObject.put("expiryDate", expiryDate);
                    userObject.put("CVV", CVV);
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

    public String findCard(String username) {
        try {
            JSONParser parser = new JSONParser();
            JSONObject usersObject = (JSONObject) parser.parse(new FileReader(filePath));
            JSONArray usersArray = (JSONArray) usersObject.get("users");

            for (int i = 0; i < usersArray.size(); i++) {
                JSONObject userDetails = (JSONObject) usersArray.get(i);
                if (userDetails.get("username").toString().equals(username)) {
                    return userDetails.get("cardName").toString();
                }
            }
            return null;

        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
            throw new RuntimeException(e);
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
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
            System.out.println("History added");
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
            throw new RuntimeException(e);
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }

        return true;
    }

    public List<Account> getAllUsers() {
        List<Account> users = new ArrayList<Account>();
        try {
            FileReader reader = new FileReader(filePath);
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
            JSONArray usersArray = (JSONArray) jsonObject.get("users");

            for (Object user : usersArray) {
                JSONObject userObject = (JSONObject) user;
                String username = (String) userObject.get("username");
                String password = (String) userObject.get("password");
                String cardName = (String) userObject.get("cardName");
                String cardNumber = (String) userObject.get("cardNumber");
                String expiryDate = (String) userObject.get("expiryDate");
                String cvv = (String) userObject.get("CVV");
                String role = (String) userObject.get("userRole");
                JSONArray purchaseHistory = (JSONArray) userObject.get("purchaseHistory");
                List<String> history = new ArrayList<String>();
                for (int i = 0; i < purchaseHistory.size(); i++) {
                    String purchase = (String) purchaseHistory.get(i);
                    history.add(purchase);
                }
                Account account = new Account(username, password, cardName, cardNumber, expiryDate, cvv, history, role);
                users.add(account);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
            throw new RuntimeException(e);
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }

        return users;
    }
}
