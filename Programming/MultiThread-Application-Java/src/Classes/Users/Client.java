package Classes.Users;

import java.util.ArrayList;

public class Client extends User {
    private double account;
    private double discount;
    private ArrayList<Long> cart;


    public Client(Long id, String first_name, String last_name, String login, String password, boolean is_staff, ArrayList<String> history, double account, double discount, ArrayList<Long> cart) {
        super(id, first_name, last_name, login, password, is_staff, history);
        this.account = account;
        this.discount = discount;
        this.cart = cart;
    }

    public Client(String login, String password) {
        super(login, password);
    }

    public double getAccount() {
        return account;
    }

    public void setAccount(double account) {
        this.account = account;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public ArrayList<Long> getCart() {
        return cart;
    }

    public void setCart(ArrayList<Long> cart) {
        this.cart = cart;
    }

    @Override
    public String getAllInfo() {
        return null;
    }
}
