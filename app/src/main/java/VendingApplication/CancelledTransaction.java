package VendingApplication;

public class CancelledTransaction extends Transaction {
    private String reason;

    public CancelledTransaction(String date, String time, String username, String reason) {
        super(date, time, username);
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }
}
