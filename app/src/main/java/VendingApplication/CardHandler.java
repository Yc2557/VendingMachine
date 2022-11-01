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

    private UserManager userManager;
    private boolean validCard = false;

    public CardHandler(String cardPath) {
        this.cardPath = cardPath;
        this.userManager = new UserManager();
    }

    public void checkCreditCard(String cardName, String cardNumber, String expiryDate, String CVV) {

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
        this.userManager.addCreditCard(username, cardName, cardNumber, expiryDate, CVV);
    }

    public String findCard(String username) {
        return userManager.findCard(username);
    }

    public boolean checkCVV(String CVV) {
        return (CVV.length() == 3) && (isNumeric(CVV));
    }

    public boolean checkExpiry(String expiryDate) {
        int eLen = expiryDate.length();
        String[] dates = expiryDate.split("/");
        return ( (eLen == 4 || eLen == 5) && isNumeric(dates[0]) && isNumeric(dates[1]) && (Integer.parseInt(dates[0])<13));
    }

    public void validateCard() {this.validCard = true;}

    public void invalidateCard() {this.validCard = false;}

    public boolean isValidCard() {return this.validCard; }

    public boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

}