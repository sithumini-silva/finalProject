package lk.ijse.gdse71.dreamlandkids.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.gdse71.dreamlandkids.dto.CustomerDTO;
import lk.ijse.gdse71.dreamlandkids.dto.ItemDTO;
import lk.ijse.gdse71.dreamlandkids.dto.tm.ItemTM;
import lk.ijse.gdse71.dreamlandkids.model.ItemModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class ItemController implements Initializable {

    @FXML
    private Button btnDeleteItem;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSaveItem;

    @FXML
    private Button btnUpdateItem;

    @FXML
    private TableColumn<ItemTM,String> colItemId;

    @FXML
    private TableColumn<ItemTM,String> colName;

    @FXML
    private TableColumn<ItemTM,Double> colPrice;

    @FXML
    private TableColumn<ItemTM,Integer> colQuantity;

    @FXML
    private Label lblItemId;

    @FXML
    private TableView<ItemTM> tblItem;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPrice;

    @FXML
    private TextField txtQuantity;

    @FXML
    void btnDeleteItemOnAction(ActionEvent event) throws SQLException {
        String itemId = lblItemId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {

            boolean isDeleted = itemModel.deleteItem(itemId);
            if (isDeleted) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Item deleted...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete Item...!").show();
            }
        }
    }

    @FXML
    void btnResetOnAction(ActionEvent event) throws SQLException {
        refreshPage();
    }

    @FXML
    void btnSaveItemOnAction(ActionEvent event) throws SQLException {
        String itemId = lblItemId.getText();
        String name = txtName.getText();
        Integer quantity = Integer.valueOf(txtQuantity.getText());
        Double price = Double.valueOf(txtPrice.getText());

        txtName.setStyle(txtName.getStyle() + ";-fx-border-color: #7367F0;");
        txtQuantity.setStyle(txtQuantity.getStyle() + ";-fx-border-color: #7367F0;");
        txtPrice.setStyle(txtPrice.getStyle() + ";-fx-border-color: #7367F0;");

        String namePattern = "^[A-Za-z ]+$";

        boolean isValidName = name.matches(namePattern);

        if (!isValidName) {
            System.out.println(txtName.getStyle());
            txtName.setStyle(txtName.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid name.............");
//            return;
        }

        if (isValidName) {
            ItemDTO itemDTO = new ItemDTO(
                    itemId,
                    name,
                    quantity,
                    price
            );
            boolean isSaved = itemModel.saveItem(itemDTO);
            if (isSaved) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Item saved...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to save Item...!").show();
            }
        }
    }

    @FXML
    void btnUpdateItemOnAction(ActionEvent event) throws SQLException {
        String itemId = lblItemId.getText();
        String name = txtName.getText();
        Integer quantity = Integer.valueOf(txtQuantity.getText());
        Double price = Double.valueOf(txtPrice.getText());

        txtName.setStyle(txtName.getStyle() + ";-fx-border-color: #7367F0;");
        txtQuantity.setStyle(txtQuantity.getStyle() + ";-fx-border-color: #7367F0;");
        txtPrice.setStyle(txtPrice.getStyle() + ";-fx-border-color: #7367F0;");

        String namePattern = "^[A-Za-z ]+$";

        boolean isValidName = name.matches(namePattern);

        if (!isValidName) {
            System.out.println(txtName.getStyle());
            txtName.setStyle(txtName.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid name.............");
//            return;
        }

        if (isValidName) {
            ItemDTO itemDTO = new ItemDTO(
                    itemId,
                    name,
                    quantity,
                    price
            );
            boolean isUpdate = itemModel.updateCustomer(itemDTO);
            if (isUpdate) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Item update...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to update Item...!").show();
            }
        }
    }

    @FXML
    void onClickTable(MouseEvent event) {
        ItemTM itemTM = tblItem.getSelectionModel().getSelectedItem();
        if (itemTM != null) {
            lblItemId.setText(itemTM.getItemId());
            txtName.setText(itemTM.getName());
            txtQuantity.setText(String.valueOf(itemTM.getQuantity()));
            txtPrice.setText(String.valueOf(itemTM.getPrice()));

            btnSaveItem.setDisable(true);

            btnDeleteItem.setDisable(false);
            btnUpdateItem.setDisable(false);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colItemId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        try {
            refreshPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load customer id").show();
        }
    }

    private void refreshPage() throws SQLException {
        loadNextItemId();
        loadTableData();

        btnSaveItem.setDisable(false);
        btnUpdateItem.setDisable(true);
        btnDeleteItem.setDisable(true);

        txtName.setText("");
        txtQuantity.setText("");
        txtPrice.setText("");

    }

    ItemModel itemModel = new ItemModel();

    private void loadTableData() throws SQLException {
        ArrayList<ItemDTO> itemDTOS = itemModel.getAllItems();
        ObservableList<ItemTM> itemTMS = FXCollections.observableArrayList();

        for (ItemDTO itemDTO : itemDTOS) {
            ItemTM itemTM = new ItemTM(
                    itemDTO.getItemId(),
                    itemDTO.getItemName(),
                    (double) itemDTO.getPrice(),
                    itemDTO.getQuantity()
            );
            itemTMS.add(itemTM);
        }
        tblItem.setItems(itemTMS);
    }

    private void loadNextItemId() throws SQLException {
        String nextItemId = itemModel.getNextItemId();
        lblItemId.setText(nextItemId);
    }
}
