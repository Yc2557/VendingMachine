package VendingApplication;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

public class CardHandler {

    private String cardPath;

    private UserManager userManager;
    private boolean validCard = false;

    public CardHandler(String cardPath) {
        this.cardPath = cardPath;
        this.userManager = new UserManager();
    }

    public void setUserManager(String userManagerPath) {
        this.userManager = new UserManager(userManagerPath);
    }

    public void checkCreditCard(String cardName, String cardNumber, String expiryDate, String CVV) {

        if (!checkCardNumber(cardNumber) || !checkCVV(CVV) || !checkExpiry(expiryDate)) {
            return;
        }

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

    public boolean checkCardNumber(String cardNumber) {
        return isNumeric(cardNumber);
    }

    public boolean checkCVV(String CVV) {
        return (CVV.length() == 3) && (isNumeric(CVV));
    }

    public boolean checkExpiry(String expiryDate) {
        int eLen = expiryDate.length();
        String[] dates = expiryDate.split("/");
        if (isNumeric(dates[0]) && isNumeric(dates[1])) {
            int m = Integer.parseInt(dates[0]);
            int y = Integer.parseInt(dates[1]);
            return (eLen==4 |eLen==5) && m>0 && m<13 && y>-1;
        }
        return false;
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

    public boolean addNewValidCard(String cardName, String cardNumber, String expiryDate, String CVV) {

        if (!checkCardNumber(cardNumber) || !checkCVV(CVV) || !checkExpiry(expiryDate)) {
            return false;
        }

        try {
            JSONParser parser = new JSONParser();
            JSONArray cardsDB = (JSONArray) parser.parse(new FileReader(cardPath));

            JSONObject newCard = new JSONObject();
            newCard.put("name", cardName);
            newCard.put("number", cardNumber);
            newCard.put("expiryDate", expiryDate);
            newCard.put("CVV", CVV);
            cardsDB.add(newCard);

            File file = new File(cardPath);
            file.createNewFile();
            FileWriter writer = new FileWriter(file);
            writer.write(cardsDB.toJSONString());
            writer.flush();
            writer.close();
            return true;

        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
            throw new RuntimeException(e);
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

}