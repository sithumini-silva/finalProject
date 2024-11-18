package lk.ijse.gdse71.dreamlandkids.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.gdse71.dreamlandkids.dto.CustomerDTO;
import lk.ijse.gdse71.dreamlandkids.dto.ItemDTO;
import lk.ijse.gdse71.dreamlandkids.dto.OrderDTO;
import lk.ijse.gdse71.dreamlandkids.dto.OrderDetailsDTO;
import lk.ijse.gdse71.dreamlandkids.dto.tm.CartTM;
import lk.ijse.gdse71.dreamlandkids.model.CustomerModel;
import lk.ijse.gdse71.dreamlandkids.model.ItemModel;
import lk.ijse.gdse71.dreamlandkids.model.OrderModel;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class OrdersController implements Initializable {

    @FXML
    private ComboBox<String> cmbCustomerId;
    @FXML
    private ComboBox<String> cmbItemId;
    @FXML
    private TableView<CartTM> tblCart;
    @FXML
    private TableColumn<CartTM, String> colItemId;
    @FXML
    private TableColumn<CartTM, String> colName;
    @FXML
    private TableColumn<CartTM, Integer> colQuantity;
    @FXML
    private TableColumn<CartTM, Double> colPrice;
    @FXML
    private TableColumn<CartTM, Double> colTotal;
    @FXML
    private TableColumn<?, ?> colAction;
    @FXML
    private Label lblCustomerName;
    @FXML
    private Label lblItemName;
    @FXML
    private Label lblItemPrice;
    @FXML
    private Label lblItemQty;
    @FXML
    private Label lblOrderId;
    @FXML
    private Label orderDate;
    @FXML
    private TextField txtAddToCartQty;

    private final OrderModel orderModel = new OrderModel();
    private final CustomerModel customerModel = new CustomerModel();
    private final ItemModel itemModel = new ItemModel();

    private final ObservableList<CartTM> cartTMS = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValues();

        try {
            refreshPage();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Fail to load data..!").show();
        }
    }

    private void setCellValues() {
        colItemId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("cartQuantity"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("removeBtn"));

        tblCart.setItems(cartTMS);
    }

    private void refreshPage() throws SQLException {
        lblOrderId.setText(orderModel.getNextOrderId());

        orderDate.setText(LocalDate.now().toString());

        loadCustomerIds();
        loadItemId();

        cmbCustomerId.getSelectionModel().clearSelection();
        cmbItemId.getSelectionModel().clearSelection();
        lblItemName.setText("");
        lblItemQty.setText("");
        lblItemPrice.setText("");
        txtAddToCartQty.setText("");
        lblCustomerName.setText("");

        cartTMS.clear();

        tblCart.refresh();
    }

    private void loadItemId() throws SQLException {
        ArrayList<String> itemIds = itemModel.getAllItemIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(itemIds);
        cmbItemId.setItems(observableList);
    }

    private void loadCustomerIds() throws SQLException {
        ArrayList<String> customerIds = customerModel.getAllCustomerIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(customerIds);
        cmbCustomerId.setItems(observableList);
    }

    @FXML
    void btnAddToCartOnAction(ActionEvent event) {
        String selectedItemId = cmbItemId.getValue();

        if (selectedItemId == null) {
            new Alert(Alert.AlertType.ERROR, "Please select item..!").show();
            return;
        }

        String cartQtyString = txtAddToCartQty.getText();

        String qtyPattern = "^[0-9]+$";

        if (!cartQtyString.matches(qtyPattern)){
            new Alert(Alert.AlertType.ERROR, "Please enter valid quantity..!").show();
            return;
        }

        String itemName = lblItemName.getText();
        int cartQty = Integer.parseInt(cartQtyString);
        int qtyOnHand = Integer.parseInt(lblItemQty.getText());

        if (qtyOnHand < cartQty) {
            new Alert(Alert.AlertType.ERROR, "Not enough items..!").show();
            return;
        }

        txtAddToCartQty.setText("");

        double unitPrice = Double.parseDouble(lblItemPrice.getText());
        double total = unitPrice * cartQty;

        for (CartTM cartTM : cartTMS) {

            if (cartTM.getItemId().equals(selectedItemId)) {
                int newQty = cartTM.getCartQuantity() + cartQty;
                cartTM.setCartQuantity(newQty);
                cartTM.setTotal(unitPrice * newQty);

                tblCart.refresh();
                return;
            }
        }

        Button btn = new Button("Remove");

        CartTM newCartTM = new CartTM(
                selectedItemId,
                itemName,
                cartQty,
                unitPrice,
                total,
                btn
        );

        btn.setOnAction(actionEvent -> {

            cartTMS.remove(newCartTM);

            tblCart.refresh();
        });

        cartTMS.add(newCartTM);
    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) throws SQLException {
        if (tblCart.getItems().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please add items to cart..!").show();
            return;
        }
        if (cmbCustomerId.getSelectionModel().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please select customer for place order..!").show();
            return;
        }

        String orderId = lblOrderId.getText();
        Date dateOfOrder = Date.valueOf(orderDate.getText());
        String customerId = cmbCustomerId.getValue();

        ArrayList<OrderDetailsDTO> orderDetailsDTOS = new ArrayList<>();

        for (CartTM cartTM : cartTMS) {

            OrderDetailsDTO orderDetailsDTO = new OrderDetailsDTO(
                    orderId,
                    cartTM.getItemId(),
                    cartTM.getCartQuantity(),
                    cartTM.getUnitPrice()
            );
            orderDetailsDTOS.add(orderDetailsDTO);
        }

        OrderDTO orderDTO = new OrderDTO(
                orderId,
                customerId,
                dateOfOrder,
                orderDetailsDTOS
        );

        boolean isSaved = orderModel.saveOrder(orderDTO);

        if (isSaved) {
            new Alert(Alert.AlertType.INFORMATION, "Order saved..!").show();

            refreshPage();
        } else {
            new Alert(Alert.AlertType.ERROR, "Order fail..!").show();
        }
    }

    @FXML
    void btnResetOnAction(ActionEvent event) throws SQLException {
        refreshPage();
    }

    @FXML
    void cmbCustomerOnAction(ActionEvent event) throws SQLException {
        String selectedCustomerId = cmbCustomerId.getSelectionModel().getSelectedItem();
        CustomerDTO customerDTO = customerModel.findById(selectedCustomerId);

        if (customerDTO != null) {

            lblCustomerName.setText(customerDTO.getName());
        }
    }

    @FXML
    void cmbItemOnAction(ActionEvent event) throws SQLException {
        String selectedItemId = cmbItemId.getSelectionModel().getSelectedItem();
        ItemDTO itemDTO = itemModel.findById(selectedItemId);

        if (itemDTO != null) {

            lblItemName.setText(itemDTO.getItemName());
            lblItemQty.setText(String.valueOf(itemDTO.getQuantity()));
            lblItemPrice.setText(String.valueOf(itemDTO.getPrice()));
        }
    }

}

