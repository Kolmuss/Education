package Classes.Users;

import java.util.ArrayList;

public class Admin extends User {
    private ArrayList<String> workDays;
    private double rate;
    private boolean is_theBest;

    public Admin(Long id, String first_name, String last_name, String login, String password, boolean is_staff, ArrayList<String> history, ArrayList<String> workDays, double rate, boolean is_theBest) {
        super(id, first_name, last_name, login, password, is_staff, history);
        this.workDays = workDays;
        this.rate = rate;
        this.is_theBest = is_theBest;
    }

    public ArrayList<String> getWorkDays() {
        return workDays;
    }

    public void setWorkDays(ArrayList<String> workDays) {
        this.workDays = workDays;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public boolean isBest() {
        return is_theBest;
    }

    public void setBest(boolean is_theBest) {
        this.is_theBest = is_theBest;
    }

    @Override
    public String getAllInfo() {
        return null;
    }

}
