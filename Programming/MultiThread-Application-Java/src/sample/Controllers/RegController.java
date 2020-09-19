package sample.Controllers;

import Classes.Database.Request;
import Classes.Database.Response;
import Classes.Users.Admin;
import Classes.Users.Client;
import Classes.Users.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class RegController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button signUpButton;

    @FXML
    private Label warningsLabel;

    @FXML
    private Label workLabel;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField LastNameField;

    @FXML
    private ListView<String> daysList;

    @FXML
    private CheckBox staffBox;

    @FXML
    private Button loginButton;

    @FXML
    private TextField promoField;

    @FXML
    void initialize(ObjectOutputStream oos, ObjectInputStream ois) {
        daysList.getItems().addAll("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");
        daysList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        staffBox.setOnAction(e -> {
            if (staffBox.isSelected()) {
                promoField.setVisible(false);
                workLabel.setVisible(true);
                daysList.setVisible(true);
            }
            else {
                promoField.setVisible(true);
                workLabel.setVisible(false);
                daysList.setVisible(false);
            }
        });

        signUpButton.setOnAction(e -> {
            if(passwordField.getText().isEmpty() || firstNameField.getText().isEmpty() || usernameField.getText().isEmpty()
                    || LastNameField.getText().isEmpty()) {
                warningsLabel.setText("You missed some field!");
                return;
            }
            if(passwordField.getText().length() < 8){
                warningsLabel.setText("Your password is too short! At least 8 characters");
                return;
            }
            if(usernameField.getText().length() < 4 || usernameField.getText().length() > 15){
                warningsLabel.setText("Your login should be 5-15!");
                return;
            }
            try {
                Request req = new Request("USERS");
                oos.writeObject(req);
                Response res = (Response)ois.readObject();

                System.out.println("1");

                ArrayList<User> users = res.getUsers();
                for (User u: users){
                    if(usernameField.getText().equals(u.getLogin())){
                        warningsLabel.setText("This login already exists!");
                        return;
                    }
                }
                req = new Request("SIGN_UP");
                if(staffBox.isSelected()){
                    ArrayList<String> days = new ArrayList<> (daysList.getSelectionModel().getSelectedItems());
                    if(days.isEmpty()){
                        warningsLabel.setText("You missed some field!");
                        return;
                    }
                    double rate = days.size() * 0.075;
                    req.setUser(new Admin(null, firstNameField.getText(), LastNameField.getText(), usernameField.getText(), passwordField.getText(), true, new ArrayList<String>(), days, rate, false));
                } else {
                    double discount = 0;
                    if(!promoField.getText().isEmpty()){
                        Request req1 = new Request("PROMOS");
                        oos.writeObject(req);
                        Response res1 = (Response)ois.readObject();
                        boolean bb = true;
                        for (String promo: res1.getPromos()) {
                            if(promoField.getText().equals(promo)){
                                discount = 0.15;
                                bb = false;
                                break;
                            }
                        }
                        if(bb){
                            warningsLabel.setText("Your promo is not valid!");
                            return;
                        }
                    }
                    req.setUser(new Client(null, firstNameField.getText(), LastNameField.getText(), usernameField.getText(), passwordField.getText(), false, new ArrayList<String>(), 0, discount, new ArrayList<>()));
                }
                oos.writeObject(req);
            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
            signUpButton.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/Styles/FxStyles/authorization.fxml"));
            try {
                loader.load();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            Parent root = loader.getRoot();
            AuthController auth = loader.getController();
            auth.initialize(oos,ois);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        });

        loginButton.setOnAction(e -> {
            loginButton.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/Styles/FxStyles/authorization.fxml"));
            try {
                loader.load();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            Parent root = loader.getRoot();
            AuthController auth = loader.getController();
            auth.initialize(oos,ois);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        });
    }
}
