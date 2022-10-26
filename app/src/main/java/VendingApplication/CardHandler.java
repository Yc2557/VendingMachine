package VendingApplication;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CardHandler {

    private String cardPath;
    private String userPath;

    private boolean validCard = false;

    public CardHandler(String cardPath, String userPath) {
        this.cardPath = cardPath;
        this.userPath = userPath;
    }

    public void checkCreditCard(String cardName, String cardNumber) {

        try {
            JSONParser parser = new JSONParser();
            JSONArray cardsDB = (JSONArray) parser.parse(new FileReader(cardPath));

            //Check all cards for valid details
            for (int i = 0; i < cardsDB.size(); i++) {

                JSONObject cardDetails = (JSONObject) cardsDB.get(i);
                String dbName = cardDetails.get("name").toString();
                String dbNumber = cardDetails.get("number").toString();

                if (dbName.equals(cardName) && dbNumber.equals(cardNumber)) {
                    validateCard();
                    return;
                } else {
                    invalidateCard();
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
            throw new RuntimeException(e);
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveCardDetails(String username, String cardName, String cardNumber) {

        try {
            JSONParser parser = new JSONParser();
            JSONObject usersObject = (JSONObject) parser.parse(new FileReader(userPath));
            JSONArray usersArray = (JSONArray) usersObject.get("users");

            for (int i = 0; i < usersArray.size(); i++) {
                JSONObject userDetails = (JSONObject) usersArray.get(i);
                if (userDetails.get("username").equals(username)) {
                    userDetails.put("cardName", cardName);
                    userDetails.put("cardNumber", cardNumber);

                    FileWriter writer = new FileWriter(userPath);
                    writer.write(usersObject.toJSONString());
                    writer.flush();
                    writer.close();
                    return;
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
            throw new RuntimeException(e);
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public String findCard(String username) {
        try {
            JSONParser parser = new JSONParser();
            JSONObject usersObject = (JSONObject) parser.parse(new FileReader(userPath));
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


    public void validateCard() {this.validCard = true;}

    public void invalidateCard() {this.validCard = false;}

    public boolean isValidCard() {return this.validCard; }

}