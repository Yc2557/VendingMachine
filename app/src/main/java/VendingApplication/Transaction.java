package VendingApplication;

import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class Transaction {
    protected String date;
    protected String time;
    protected String username;

    public Transaction(String date, String time, String username) {
        this.date = date;
        this.time = time;
        this.username = username;
    }

    public Transaction(String username) {
        //Alternate constructor using current date/time
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        this.date = dateFormat.format(date);
        this.time = timeFormat.format(date);
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
