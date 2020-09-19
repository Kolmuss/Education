package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Controllers.AuthController;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Socket socket = new Socket("localhost", 3003);
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Styles/FxStyles/authorization.fxml"));
        loader.load();
        Parent root = loader.getRoot();
        AuthController controller = loader.getController();
        controller.initialize(oos,ois);
        primaryStage.setScene(new Scene(root, 669, 568));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
