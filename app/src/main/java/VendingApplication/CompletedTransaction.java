package VendingApplication;

public class CompletedTransaction extends Transaction {

    private String paymentType;

    private String price;

    private String change;

    private Cart cart;

    public CompletedTransaction(String username, Cart cart, String paymentType, String price, String change) {
        super(username);
        this.cart = cart;
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

    public void setDateTime(String date, String time) {
        this.date = date;
        this.time = time;
    }

}
