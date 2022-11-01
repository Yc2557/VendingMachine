package VendingApplication;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CompletedTransaction extends Transaction {

    private String paymentType;

    private String price;

    private String change;

    private Cart cart;

    public CompletedTransaction(Cart cart, String paymentType, String price, String change) {
        super(date, time, username);
        this.cart = cart;
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String dateString = format.format(date);

        this.paymentType = paymentType;
        this.price = price;
        this.change = change;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public String getPrice() {
        return price;
    }

    public String getChange() {
        return change;
    }

    public Cart getCart() {
        return cart;
    }
}
