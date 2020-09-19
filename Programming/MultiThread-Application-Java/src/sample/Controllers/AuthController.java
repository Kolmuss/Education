package sample.Controllers;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ResourceBundle;

import Classes.Database.Request;
import Classes.Database.Response;
import Classes.Users.Admin;
import Classes.Users.Client;
import Classes.Users.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Main;

public class AuthController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordVisible;

    @FXML
    private PasswordField passwordInvisible;

    @FXML
    private CheckBox showPassword;

    @FXML
    private Button loginButton;

    @FXML
    private Button signUpButton;

    @FXML
    private Label warningsLabel;

    @FXML
    public void initialize(ObjectOutputStream oos, ObjectInputStream ois){

        showPassword.setOnAction(e -> {
            if (showPassword.isSelected()) {
                passwordVisible.setText(passwordInvisible.getText().trim());
                passwordInvisible.setVisible(false);
                passwordVisible.setVisible(true);
            }
            else {
                passwordInvisible.setText(passwordVisible.getText().trim());
                passwordInvisible.setVisible(true);
                passwordVisible.setVisible(false);
            }
        });

        signUpButton.setOnAction(e -> {
            signUpButton.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/Styles/FxStyles/registration.fxml"));
            try {
                loader.load();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            Parent root = loader.getRoot();
            RegController reg = loader.getController();
            reg.initialize(oos,ois);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        });

        loginButton.setOnAction(e -> {
            if (usernameField.getText().isEmpty()) {
                warningsLabel.setText("You missed some field!");
                return;
            } else if (showPassword.isSelected())
                if (passwordVisible.getText().isEmpty()) {
                    warningsLabel.setText("You missed some field!");
                    return;
                } else if (passwordInvisible.getText().isEmpty()) {
                    warningsLabel.setText("You missed some field!");
                    return;
                }

            String username = usernameField.getText().trim();
            String password;
            if (showPassword.isSelected())
                password = passwordVisible.getText().trim();
            else
                password = passwordInvisible.getText().trim();

            try {
                Request req = new Request("AUTHORIZATION");
                req.setUser(new Client(username, password));

                oos.writeObject(req);
                Response rep = (Response) ois.readObject();
                User user = rep.getUser();

                if (user == null) {
                    warningsLabel.setText("User has been not found!");
                } else {
                    loginButton.getScene().getWindow().hide();
                    FXMLLoader loader = new FXMLLoader();
                    if(user.isStaff()){
                        loader.setLocation(getClass().getResource("/sample/Styles/FxStyles/mainAdmin.fxml"));
                    }else{
                        loader.setLocation(getClass().getResource("/sample/Styles/FxStyles/mainClient.fxml"));
                    }
                    try {
                        loader.load();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    Parent root = loader.getRoot();
                    if(user.isStaff()){
                        MainAdmin admin = loader.getController();
                        admin.setAdmin((Admin) user);
                        admin.initialize(oos,ois);
                    }else{
                        MainClient client = loader.getController();
                        client.setUser((Client) user);
                        client.initialize(oos,ois);
                    }

                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.show();
                }
            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        });

    }
}

