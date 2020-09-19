package Classes.Database;

import Classes.Goods.Good;
import Classes.Users.User;

import java.io.Serializable;
import java.util.ArrayList;


public class Response implements Serializable {
    private String response;
    private ArrayList<User> users;
    private ArrayList<String> promos;
    private ArrayList<Good> goods;
    private User user;

    public Response(String response) {
        this.response = response;
    }

    public Response() {
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
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

    public ArrayList<String> getPromos() {
        return promos;
    }

    public void setPromos(ArrayList<String> promos) {
        this.promos = promos;
    }

    public ArrayList<Good> getGoods() {
        return goods;
    }

    public void setGoods(ArrayList<Good> goods) {
        this.goods = goods;
    }
}
