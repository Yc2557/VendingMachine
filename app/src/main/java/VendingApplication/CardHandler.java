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

    private final String cardPath = "src/main/resources/credit_cards.json";
    private final String userPath = "src/main/resources/user.json";

    private boolean validCard = false;

    public void checkCreditCard(String cardName, int cardNumber) {

        try {
            JSONParser parser = new JSONParser();
            JSONArray cardsDB = (JSONArray) parser.parse(new FileReader(cardPath));

            //Check all cards for valid details
            for (int i = 0; i < cardsDB.size(); i++) {
                JSONObject cardDetails = (JSONObject) cardsDB.get(i);
                if (cardDetails.get("name").equals(cardName) && (int) cardDetails.get("number") == cardNumber) {
                    validateCard();
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

    public void saveCardDetails(String username, String cardName, int cardNumber) {

        try {
            JSONParser parser = new JSONParser();
            JSONObject usersObject = (JSONObject) parser.parse(new FileReader(userPath));
            JSONArray usersDB = (JSONArray) usersObject.get("users");

            for (int i = 0; i < usersDB.size(); i++) {
                JSONObject userDetails = (JSONObject) usersDB.get(i);
                if (userDetails.get("username").equals(username)) {
                    userDetails.put("cardName", cardName);
                    userDetails.put("cardNumber", cardNumber);
                }
            }


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