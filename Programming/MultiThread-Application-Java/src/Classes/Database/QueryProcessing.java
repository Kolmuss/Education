package Classes.Database;

import Classes.Goods.Accessory;
import Classes.Goods.Clothing;
import Classes.Goods.Good;
import Classes.Goods.Shoes;
import Classes.Users.Admin;
import Classes.Users.Client;
import Classes.Users.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class QueryProcessing extends Thread {

    private Socket socket;
    private ObjectOutputStream oos = null;
    private ObjectInputStream ois = null;
    private Connection conn;

    public QueryProcessing(Socket socket, Connection conn){
        this.conn = conn;
        this.socket = socket;
        try {
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<User> getUsers(){
        ArrayList<User> list = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM users");
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                Long id = rs.getLong("user_id");
                String first_name = rs.getString("first_name");
                String last_name = rs.getString("last_name");
                String login = rs.getString("login");
                String password = rs.getString("password");
                boolean is_staff = rs.getBoolean("staff_status");
                if(is_staff) {
                    PreparedStatement ps1 = conn.prepareStatement("SELECT * FROM admins WHERE admin_id = ?");
                    ps1.setLong(1, id);
                    ResultSet rs1 = ps1.executeQuery();
                    rs1.next();
                    double rate = rs1.getDouble("rate");
                    boolean isBest = rs1.getBoolean("is_best");
                    ArrayList<String> days = new ArrayList<>(Arrays.asList(rs1.getString("days").split(",")));
                    ArrayList<String> history = new ArrayList<>(Arrays.asList(rs1.getString("history").split(",")));
                    list.add(new Admin(id, first_name, last_name, login, password, true, history, days, rate, isBest));
                    ps1.close();
                }else {
                    PreparedStatement ps1 = conn.prepareStatement("SELECT * FROM clients WHERE client_id = ?");
                    ps1.setLong(1, id);
                    ResultSet rs1 = ps1.executeQuery();
                    rs1.next();
                    double account = rs1.getDouble("account");
                    double discount = rs1.getDouble("discount");
                    ArrayList<String> history = new ArrayList<>(Arrays.asList(rs1.getString("history").split(",")));
                    ArrayList<Long> cart = new ArrayList<>();
                    for (String goodId : rs1.getString("cart").split(",")) {
                        cart.add((Long.parseLong(goodId)));
                    }
                    list.add(new Client(id, first_name, last_name, login, password, false, history, account, discount, cart));
                    ps1.close();
                }
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void addUser(User user){
        System.out.println(user);
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO users (user_id, first_name, last_name, login, password, staff_status) VALUES(NULL, ?, ?, ?, ?, ?)");
            ps.setString(1, user.getFirst_name());
            ps.setString(2, user.getLast_name());
            ps.setString(3, user.getLogin());
            ps.setString(4, user.getPassword());
            ps.setBoolean(5, user.isStaff());
            ps.executeUpdate();
            ps = conn.prepareStatement("SELECT * FROM users WHERE login = ?");
            ps.setString(1, user.getLogin());
            ResultSet rs = ps.executeQuery();
            rs.next();
            Long userId = rs.getLong("user_id");
            if(user.isStaff()){
                Admin admin = (Admin) user;
                ps = conn.prepareStatement("INSERT INTO admins (admin_id, rate, is_best, days, history) VALUES(?, ?, ?, ?, ?)");
                ps.setLong(1,userId);
                ps.setDouble(2, admin.getRate());
                ps.setBoolean(3, admin.isBest());
                StringBuilder days = new StringBuilder();
                for (String s: admin.getWorkDays()) {
                    days.append(s).append(",");
                }
                ps.setString(4, String.valueOf(days));
                StringBuilder history = new StringBuilder();
                for (String s: admin.getHistory()) {
                    history.append(s).append(",");
                }
                ps.setString(5, String.valueOf(history));
                ps.executeUpdate();
            }else{
                Client client = (Client) user;
                ps = conn.prepareStatement("INSERT INTO clients (client_id, account, discount, history, cart) VALUES(?, ?, ?, ?, ?)");
                ps.setLong(1, userId);
                ps.setDouble(2, client.getAccount());
                ps.setDouble(3, client.getDiscount());
                StringBuilder history = new StringBuilder();
                for (String s: client.getHistory()) {
                    history.append(s).append(",");
                }
                ps.setString(4, String.valueOf(history));
                StringBuilder cart = new StringBuilder();
                for (Long s: client.getCart()) {
                    cart.append(s).append(",");
                }
                ps.setString(5, String.valueOf(cart));
                ps.executeUpdate();
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addGood(Good good){
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO goods (good_id, name, price, available, sold, brand, size, season, kind, poster) VALUES(NULL, ?, ?, ?, ?, ?, ?, ?, ? ,?)");
            ps.setString(1, good.getName());
            ps.setDouble(2, good.getPrice());
            ps.setInt(3, good.getAvailable());
            ps.setInt(4, good.getSold());
            ps.setString(5, good.getBrand());
            ps.setString(6, good.getSize());
            ps.setString(7, good.getSeason());
            ps.setString(8, good.getKind());
            ps.setString(9, good.getPoster());
            ps.executeUpdate();
            ps = conn.prepareStatement("SELECT * FROM goods WHERE name = ?");
            ps.setString(1, good.getName());
            ResultSet rs = ps.executeQuery();
            rs.next();
            Long goodId = rs.getLong("good_id");
            if(good.getKind().equals("SHOES")){
                Shoes shoes = (Shoes) good;
                ps = conn.prepareStatement("INSERT INTO shoes (shoes_id, material) VALUES(?, ?)");
                ps.setLong(1, goodId);
                ps.setString(2, shoes.getMaterial());
                ps.executeUpdate();
            }else
            if(good.getKind().equals("CLOTHING")){
                Clothing clothing = (Clothing) good;
                ps = conn.prepareStatement("INSERT INTO clothing (clothing_id, color) VALUES(?, ?)");
                ps.setLong(1, goodId);
                ps.setString(2, clothing.getColor());
                ps.executeUpdate();
            }else{
                Accessory accessory = (Accessory) good;
                ps = conn.prepareStatement("INSERT INTO accessories (accessory_id, type) VALUES(?, ?)");
                ps.setLong(1, goodId);
                ps.setString(2, accessory.getType());
                ps.executeUpdate();
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public ArrayList<Good> getGoods(){
        ArrayList<Good> list = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM goods");
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                Long id = rs.getLong("good_id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                int available = rs.getInt("available");
                int sold = rs.getInt("sold");
                String brand = rs.getString("brand");
                String size = rs.getString("size");
                String season = rs.getString("season");
                String kind = rs.getString("kind");
                String poster = rs.getString("poster");

                if(kind.equals("SHOES")) {
                    PreparedStatement ps1 = conn.prepareStatement("SELECT * FROM shoes WHERE shoes_id = ?");
                    ps1.setLong(1, id);
                    ResultSet rs1 = ps1.executeQuery();
                    rs1.next();
                    String material = rs1.getString("material");
                    list.add(new Shoes(id, price, available, sold, brand, name, size, season, kind, poster, material));
                    ps1.close();
                }else
                if(kind.equals("CLOTHING")){
                    PreparedStatement ps1 = conn.prepareStatement("SELECT * FROM clothing WHERE clothing_id = ?");
                    ps1.setLong(1, id);
                    ResultSet rs1 = ps1.executeQuery();
                    rs1.next();
                    String color = rs1.getString("color");
                    list.add(new Clothing(id, price, available, sold, brand, name, size, season, kind, poster, color));
                    ps1.close();
                }else {
                    PreparedStatement ps1 = conn.prepareStatement("SELECT * FROM accessories WHERE accessory_id = ?");
                    ps1.setLong(1, id);
                    ResultSet rs1 = ps1.executeQuery();
                    rs1.next();
                    String type = rs1.getString("type");
                    list.add(new Accessory(id, price, available, sold, brand, name, size, season, kind, poster, type));
                    ps1.close();
                }
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


    public void run(){
        while(true){
            Request req = null;
            try {
                req = (Request)ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            assert req != null;

            switch (req.getRequest()) {
                case "AUTHORIZATION": {
                    Response rep = new Response("SUCCESS");
                    User user = req.getUser();
                    boolean b = true;
                    for (User u: getUsers()) {
                        if(u.getLogin().equals(user.getLogin()) && u.getPassword().equals(user.getPassword())){
                            rep.setUser(u);
                            b = false;
                        }
                    }
                    if(b){
                        rep.setUser(null);
                    }
                    try {
                        oos.writeObject(rep);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case "SIGN_UP": {
                    addUser(req.getUser());
                    break;
                }
                case "ADD": {
                    addGood(req.getGood());
                    break;
                }
                case "USERS": {
                    Response res = new Response();
                    res.setUsers(getUsers());
                    try {
                        oos.writeObject(res);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case "GOODS": {
                    Response res = new Response();
                    res.setGoods(getGoods());
                    try {
                        oos.writeObject(res);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }
    }
}
