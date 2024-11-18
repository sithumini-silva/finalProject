package lk.ijse.gdse71.dreamlandkids.controller;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private AnchorPane ancLogin;

    @FXML
    private ToggleButton btnShowId;

    @FXML
    private Button btnloginId;

    @FXML
    private ImageView loginImage;

    @FXML
    private Label passwordShow;

    @FXML
    private TextField txtnameId;

    @FXML
    private TextField txtpasswordId;

    @FXML
    void actLogin(ActionEvent event) {
        String userName = txtnameId.getText();
        String pw = txtpasswordId.getText();

        // Check if username and password match
        if (userName.equals("sithu") && pw.equals("1234")) {
            navigateTo("/view/MainLayoutView.fxml");
        } else {
            new Alert(Alert.AlertType.INFORMATION, "Invalid userName or password!").show();
        }
    }

    private void navigateTo(String fxmlPath) {
        try {
            // Clear existing content in AnchorPane
            ancLogin.getChildren().clear();

            // Load the new FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            AnchorPane load = loader.load();

            // Check if loading was successful
            if (load == null) {
                new Alert(Alert.AlertType.ERROR, "Failed to load FXML file: " + fxmlPath).show();
                return;
            }

            // Add the loaded AnchorPane to the login view
            ancLogin.getChildren().add(load);

        } catch (IOException e) {
            // Handle IOExceptions (file loading issues)
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load page! Please check the FXML file path.").show();
        }
    }

    @FXML
    void actShow(ActionEvent event) {
        if (btnShowId.isSelected()) {
            passwordShow.setVisible(true);
            passwordShow.textProperty().bind(Bindings.concat(txtpasswordId.getText()));
            btnShowId.setText("Hide");
        } else {
            passwordShow.setVisible(false);
            btnShowId.setText("Show");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initially hide the password display label
        passwordShow.setVisible(false);

        // Set up event handlers for the text fields
        txtnameId.setOnAction(event -> txtpasswordId.requestFocus());
        txtpasswordId.setOnAction(this::actLogin);
    }
}
