package lk.ijse.gdse71.dreamlandkids.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainLayoutController implements Initializable {

    @FXML
    private AnchorPane anchomeDisplay;

    @FXML
    private AnchorPane anchomepage;

    @FXML
    private Button btncustomer;

    @FXML
    private Button btnemployee;

    @FXML
    private Button btnorders;

    @FXML
    private Button btnHome;

    @FXML
    private Button btnitem;

    @FXML
    private Button btnsupplier;

    @FXML
    private ImageView homeImage;

    @FXML
    private VBox homeMenu;

    @FXML
    private Label lblhome;

    @FXML
    private Label lblhome2;

    @FXML
    void actcustomer(ActionEvent event) {navigateTo("/view/CustomerView.fxml");}

    @FXML
    void actemployee(ActionEvent event) {navigateTo("/view/EmployeeView.fxml");}

    @FXML
    void actorders(ActionEvent event) {navigateTo("/view/OrdersView.fxml");}

    @FXML
    void actitem(ActionEvent event) {navigateTo("/view/ItemView.fxml");}

    @FXML
    void actsupplier(ActionEvent event) {navigateTo("/view/SupplierView.fxml");}

    @FXML
    void actHome(ActionEvent event) {navigateTo("/view/HomeView.fxml");}

    @Override
    public void initialize(URL location, ResourceBundle resources) {navigateTo("/view/DashView.fxml");}

    public void navigateTo(String fxmlPath) {
        try {
            anchomeDisplay.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource(fxmlPath));

            load.prefWidthProperty().bind(anchomeDisplay.widthProperty());
            load.prefHeightProperty().bind(anchomeDisplay.heightProperty());


            anchomeDisplay.getChildren().add(load);
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load page!").show();
        }
    }
}
