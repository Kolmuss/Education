package sample.Controllers;

import Classes.Database.Request;
import Classes.Database.Response;
import Classes.Goods.Good;
import Classes.Users.Client;
import Classes.Users.User;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class MainClient {
    private Client user;
    private Good good;

    public void setUser(Client user) {
        this.user = user;
    }

    @FXML
    private TextField searchBox;

    @FXML
    private Button searchButton;

    @FXML
    private Text nameField;

    @FXML
    private Text balanceField;

    @FXML
    private Text cartSize;

    @FXML
    private Button shoesButton;

    @FXML
    private Button clothingButton;

    @FXML
    private Button accessoriesButton;

    @FXML
    private ChoiceBox<String> sizeBox;

    @FXML
    private ChoiceBox<String> brandBox;

    @FXML
    private ChoiceBox<String> seasonBox;

    @FXML
    private ChoiceBox<String> priceBox;

    @FXML
    private Button filterButton;

    @FXML
    private VBox listPanel;

    @FXML
    private ListView<Good> goodList;

    @FXML
    private Button selectButton;

    @FXML
    private AnchorPane infoPanel;

    @FXML
    private ImageView imageField;

    @FXML
    private TextArea infoArea;

    @FXML
    private Button addButton;

    @FXML
    private Button BuyButton;

    @FXML
    public void initialize(ObjectOutputStream oos, ObjectInputStream ois) {

        nameField.setText(user.getFirst_name() + " " + user.getLast_name());
        balanceField.setText(String.valueOf(user.getAccount()));
        cartSize.setText(String.valueOf(user.getCart().size()));

        Request req = new Request("GOODS");
        Response res = null;
        try {
            oos.writeObject(req);
            res = (Response) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        assert res != null;
        goodList.getItems().addAll(res.getGoods());
        goodList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        shoesButton.setOnAction(e -> {
            listPanel.setVisible(true);
            infoPanel.setVisible(false);
            goodList.getItems().clear();
            sizeBox.getItems().clear();
            brandBox.getItems().clear();
            seasonBox.getItems().clear();
            priceBox.getItems().clear();

            Request req1 = new Request("GOODS");
            Response res1 = null;
            try {
                oos.writeObject(req1);
                res1 = (Response) ois.readObject();
            } catch (IOException | ClassNotFoundException e1) {
                e1.printStackTrace();
            }
            assert res1 != null;
            for (Good g: res1.getGoods()) {
                if(g.getKind().equals("SHOES")){
                    goodList.getItems().add(g);
                }
            }
            goodList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            sizeBox.getItems().addAll("36", "37", "38", "39", "40", "41", "42");
            sizeBox.setValue("36");
            brandBox.getItems().addAll("Nike", "Fila", "Balenciaga", "Addidas", "Gucci");
            brandBox.setValue("Nike");
            seasonBox.getItems().addAll("Winter", "Spring", "Summer", "Autumn");
            seasonBox.setValue("Winter");
            priceBox.getItems().addAll("< 50$", "50 < 100$", "100 < 500$", "500$ <");
            priceBox.setValue("< 50$");
        });

        clothingButton.setOnAction(e -> {
            listPanel.setVisible(true);
            infoPanel.setVisible(false);
            goodList.getItems().clear();
            sizeBox.getItems().clear();
            brandBox.getItems().clear();
            seasonBox.getItems().clear();
            priceBox.getItems().clear();

            Request req1 = new Request("GOODS");
            Response res1 = null;
            try {
                oos.writeObject(req1);
                res1 = (Response) ois.readObject();
            } catch (IOException | ClassNotFoundException e1) {
                e1.printStackTrace();
            }
            assert res1 != null;
            for (Good g: res1.getGoods()) {
                if(g.getKind().equals("CLOTHING")){
                    goodList.getItems().add(g);
                }
            }
            goodList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            sizeBox.getItems().addAll("XS", "S", "M", "L", "XL");
            sizeBox.setValue("XS");
            brandBox.getItems().addAll("D&G", "BOSS", "H&M", "ZARA", "Balenciaga", "PRADA", "Gucci");
            brandBox.setValue("D&G");
            seasonBox.getItems().addAll("Winter", "Spring", "Summer", "Autumn");
            seasonBox.setValue("Winter");
            priceBox.getItems().addAll("< 50$", "50 < 100$", "100 < 500$", "500$ <");
            priceBox.setValue("< 50$");
        });

        accessoriesButton.setOnAction(e -> {
            listPanel.setVisible(true);
            infoPanel.setVisible(false);
            goodList.getItems().clear();
            sizeBox.getItems().clear();
            brandBox.getItems().clear();
            seasonBox.getItems().clear();
            priceBox.getItems().clear();

            Request req1 = new Request("GOODS");
            Response res1 = null;
            try {
                oos.writeObject(req1);
                res1 = (Response) ois.readObject();
            } catch (IOException | ClassNotFoundException e1) {
                e1.printStackTrace();
            }
            assert res1 != null;
            for (Good g: res1.getGoods()) {
                if(g.getKind().equals("ACCESSORY")){
                    goodList.getItems().add(g);
                }
            }
            goodList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            sizeBox.getItems().addAll("12", "12.5", "13", "13.5", "14", "14.5");
            sizeBox.setValue("12");
            brandBox.getItems().addAll("D&G", "BOSS", "PRADA", "Gucci", "Givenchy", "Armany");
            brandBox.setValue("Armany");
            seasonBox.getItems().addAll("Winter", "Spring", "Summer", "Autumn");
            seasonBox.setValue("Winter");
            priceBox.getItems().addAll("< 50$", "50 < 100$", "100 < 500$", "500$ <");
            priceBox.setValue("< 50$");
        });

        filterButton.setOnAction(e -> {
            listPanel.setVisible(true);
            infoPanel.setVisible(false);
            goodList.getItems().clear();
            ArrayList<Good> goods = new ArrayList<>();
            String size = sizeBox.getSelectionModel().getSelectedItem();
            String brand = brandBox.getSelectionModel().getSelectedItem();
            String season = seasonBox.getSelectionModel().getSelectedItem();
            Request req1 = new Request("GOODS");
            Response res1 = null;
            try {
                oos.writeObject(req1);
                res1 = (Response) ois.readObject();
            } catch (IOException | ClassNotFoundException e1) {
                e1.printStackTrace();
            }

            assert res1 != null;
            for (Good g: res1.getGoods()) {
                if(g.getSize().equals(size) && g.getBrand().equals(brand) && g.getSeason().equals(season)){
                    switch (priceBox.getSelectionModel().getSelectedItem()){
                        case "< 50$":
                            if(g.getPrice() < 50){
                                goods.add(g);
                            }
                            break;
                        case "50 < 100$":
                            if(g.getPrice() < 100){
                                goods.add(g);
                            }
                            break;
                        case "100 < 500$":
                            if(g.getPrice() < 500){
                                goods.add(g);
                            }
                            break;
                        case "500$ <":
                            if(g.getPrice() > 500){
                                goods.add(g);
                            }
                            break;
                    }
                }

            }

            goodList.getItems().addAll(goods);

        });

        searchButton.setOnAction(e -> {
            listPanel.setVisible(true);
            infoPanel.setVisible(false);
            goodList.getItems().clear();
            if(!searchBox.getText().isEmpty()){
                ArrayList<Good> goods = new ArrayList<>();
                Request req1 = new Request("GOODS");
                Response res1 = null;
                try {
                    oos.writeObject(req1);
                    res1 = (Response) ois.readObject();
                } catch (IOException | ClassNotFoundException e1) {
                    e1.printStackTrace();
                }
                assert res1 != null;

                for (Good g: res1.getGoods()) {
                    if(g.getName().equals(searchBox.getText())){
                        goods.add(g);
                    }
                }
                goodList.getItems().addAll(goods);
            }
        });

        selectButton.setOnAction(e -> {
            this.good = goodList.getSelectionModel().getSelectedItem();
            listPanel.setVisible(false);
            infoPanel.setVisible(true);

            String info = "---------------------------------Description---------------------------------\n" +
                             "Lorem Ipsum is simply dummy text of the printing and typesett.\n" +
                             "Lorem Ipsum has been the industry's standard dummy \n" +
                             "when an unknown printer took a galley of type and scramble\n" +
                             "specimen book. It has survived not only five centuries,\n" +
                             "electronic typesetting, remaining essentially unchang\n" +
                             "the 1960s with the release of Letraset sheets containing \n" +
                             "and more recently with desktop publishing software like Aldus\n" +
                             "---------------------------------Information---------------------------------\n";
            info += "NAME: " + good.getName() + "\n";
            info += "PRICE: " + good.getPrice() + "\n";
            if(good.getAvailable() == 0){
                info += "SOLD OUT!!!\n";
            }
            infoArea.setText(info);
        });

        addButton.setOnAction(e -> {
            ArrayList<Long> goods = user.getCart();
            goods.add(good.getId());
        });

        BuyButton.setOnAction(e -> {
            ArrayList<Long> goods = user.getCart();
            goods.add(good.getId());
            if(user.getAccount() >= good.getPrice()){
                user.setAccount(good.getPrice()*user.getDiscount());
                good.buy();
            }
        });
    }
}
