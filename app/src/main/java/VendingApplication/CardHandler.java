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

    private final String cardPath = "src/main/resources/data/credit_cards.json";
    private final String userPath = "src/main/resources/data/user.json";

    private boolean validCard = false;

    public void checkCreditCard(String cardName, String cardNumber, String CVV, String expiryDate) {

        try {
            JSONParser parser = new JSONParser();
            JSONArray cardsDB = (JSONArray) parser.parse(new FileReader(cardPath));

            //Check all cards for valid details
            for (int i = 0; i < cardsDB.size(); i++) {

                JSONObject cardDetails = (JSONObject) cardsDB.get(i);

                String dbName = cardDetails.get("name").toString();
                String dbNumber = cardDetails.get("number").toString();
                String dbCVV = cardDetails.get("CVV").toString();
                String dbExpiryDate = cardDetails.get("expiryDate").toString();

                if (dbName.equals(cardName) && dbNumber.equals(cardNumber) && dbCVV.equals(CVV) && dbExpiryDate.equals(expiryDate)) {
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

    public void saveCardDetails(String username, String cardName, String cardNumber, String expiryDate, String CVV) {

        UserManager userManager = new UserManager();
        userManager.addCreditCard(username, cardName, cardNumber, expiryDate, CVV);

    }

    public void validateCard() {this.validCard = true;}

    public void invalidateCard() {this.validCard = false;}

    public boolean isValidCard() {return this.validCard; }

}