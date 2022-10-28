package VendingApplication;

import java.util.List;

public class Account {
    private final String username;
    private final String password;
    private String cardNumber;
    private String cardName;

    private String CVV;

    private String expiryDate;

    private List<String> history;
    private String role;

    public Account(String u, String p, String cardNumber, String cardName, String cvv, String expiryDate, List<String> history, String role) {
        this.username = u;
        this.password = p;
        this.cardNumber = cardNumber;
        this.cardName = cardName;
        this.CVV = cvv;
        this.expiryDate = expiryDate;
        this.history = history;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public boolean verifyPassword(String p) {
        return p.equals(password);
    }
}
