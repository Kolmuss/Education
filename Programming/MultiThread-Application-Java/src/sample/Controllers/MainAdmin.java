package sample.Controllers;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ResourceBundle;

import Classes.Database.Request;
import Classes.Database.Response;
import Classes.Goods.Accessory;
import Classes.Goods.Clothing;
import Classes.Goods.Good;
import Classes.Goods.Shoes;
import Classes.Users.Admin;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class MainAdmin {

    private Admin admin;

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Text name;

    @FXML
    private TextField nameField;

    @FXML
    private TextField priceField;

    @FXML
    private TextField posterField;

    @FXML
    private TextField availableField;

    @FXML
    private ChoiceBox<String> sizeBox;

    @FXML
    private ChoiceBox<String> typeBox;

    @FXML
    private ChoiceBox<String> seasonBox;

    @FXML
    private ChoiceBox<String> brandBox;

    @FXML
    private ChoiceBox<String> propBox;

    @FXML
    private Button createButton;

    @FXML
    public void initialize(ObjectOutputStream oos, ObjectInputStream ois) {
        name.setText(admin.getFirst_name() + " " + admin.getLast_name());
        sizeBox.getItems().addAll("XS", "S", "M", "L", "XL", "36", "37", "38", "39", "40", "41", "42");
        brandBox.getItems().addAll("Gucci", "ZARA", "BOSS", "Fila", "Givenchy", "D&G", "H&M", "Addidas", "Nike", "Balenciaga", "PRADA");
        propBox.getItems().addAll("Blue", "Red", "Green", "Black", "White");
        typeBox.getItems().addAll("SHOES", "CLOTHING", "ACCESSORY");
        seasonBox.getItems().addAll("Winter", "Spring", "Summer", "Autumn");

        createButton.setOnAction(e -> {
            if(nameField.getText().isEmpty() || priceField.getText().isEmpty() || availableField.getText().isEmpty() || posterField.getText().isEmpty()){
                return;
            }else {
                String name = nameField.getText();
                double price = Double.parseDouble(priceField.getText());
                int available = Integer.parseInt(availableField.getText());

                String poster = posterField.getText();

                String size = sizeBox.getSelectionModel().getSelectedItem();

                String type = typeBox.getSelectionModel().getSelectedItem();

                String brand = brandBox.getSelectionModel().getSelectedItem();

                String season = seasonBox.getSelectionModel().getSelectedItem();

                String prop = propBox.getSelectionModel().getSelectedItem();
                Good g = null;
                if(type.equals("SHOES")){
                    g = new Shoes(null, price, available, 0, brand, name, size, season, type, poster, prop);
                }else
                if(type.equals("CLOTHING")){
                    g = new Clothing(null, price, available, 0, brand, name, size, season, type, poster, prop);
                }else{
                    g = new Accessory(null, price, available, 0, brand, name, size, season, type, poster, prop);
                }
                Request req = new Request("ADD");
                req.setGood(g);
                try {
                    oos.writeObject(req);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

    }
}
