package Classes.Database;

import Classes.Goods.Good;
import Classes.Users.User;

import java.io.Serializable;
import java.util.ArrayList;


public class Request  implements Serializable {
    private String request;
    private ArrayList<User> users;
    private Good good;
    private User user;

    public Request(String request) {
        this.request = request;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Good getGood() {
        return good;
    }

    public void setGood(Good good) {
        this.good = good;
    }
}

