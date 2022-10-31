package VendingApplication;

public abstract class Transaction {
    private String date;
    private String time;
    private String username;

    public Transaction(String date, String time, String username) {
        this.date = date;
        this.time = time;
        this.username = username;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getUsername() {
        return username;
    }
}
