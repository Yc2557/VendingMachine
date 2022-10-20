package VendingApplication;

import java.util.List;

public class Account {
    private String username;
    private String password;
    private String cardNumber;
    private String cardName;

    private List<String> history;

    public Account(String u, String p, String cardNumber,  String cardName, List<String> history) {
        this.username = u;
        this.password = p;
        this.cardNumber = cardNumber;
        this.cardName = cardName;
        this.history = history;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }



    public boolean verifyPassword(String p) {
        return p.equals(password);
    }
}
