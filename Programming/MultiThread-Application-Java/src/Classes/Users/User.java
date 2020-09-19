package Classes.Users;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class User implements Serializable {
    protected Long id;
    protected String first_name;
    protected String last_name;
    protected String login;
    protected String password;
    protected boolean is_staff;
    protected ArrayList<String> history;

    public User(Long id, String first_name, String last_name, String login, String password, boolean is_staff, ArrayList<String> history) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.login = login;
        this.password = password;
        this.is_staff = is_staff;
        this.history = history;
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isStaff() {
        return is_staff;
    }

    public void setStaff(boolean is_staff) {
        this.is_staff = is_staff;
    }

    public abstract String getAllInfo();

    public ArrayList<String> getHistory() {
        return history;
    }

    public void setHistory(ArrayList<String> history) {
        this.history = history;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", is_staff=" + is_staff +
                ", history=" + history +
                '}';
    }
}

