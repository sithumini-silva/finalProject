package lk.ijse.gdse71.dreamlandkids.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.gdse71.dreamlandkids.dto.EmployeeDTO;
import lk.ijse.gdse71.dreamlandkids.dto.SupplierDTO;
import lk.ijse.gdse71.dreamlandkids.dto.tm.EmployeeTM;
import lk.ijse.gdse71.dreamlandkids.dto.tm.SupplierTM;
import lk.ijse.gdse71.dreamlandkids.model.EmployeeModel;
import lk.ijse.gdse71.dreamlandkids.model.SupplierModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class EmployeeController  implements Initializable {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<EmployeeTM,String> colEmail;

    @FXML
    private TableColumn<EmployeeTM,String> colEmpliyeeId;

    @FXML
    private TableColumn<EmployeeTM,String> colName;

    @FXML
    private TableColumn<EmployeeTM,String> colNic;

    @FXML
    private TableColumn<EmployeeTM,String> colPhone;

    @FXML
    private Label lblEmployeeId;

    @FXML
    private TableView<EmployeeTM> tblEmployee;

    @FXML
    private TextField txtEmpName;

    @FXML
    private TextField txtEmpNic;

    @FXML
    private TextField txtEmpPhone;

    @FXML
    private TextField txtEmpmail;

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException {
        String employeeId = lblEmployeeId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {

            boolean isDeleted = employeeModel.deleteEmployee(employeeId);
            if (isDeleted) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Employee deleted...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete Employee...!").show();
            }
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException {
        String employeeId = lblEmployeeId.getText();
        String name = txtEmpName.getText();
        String nic = txtEmpNic.getText();
        String email = txtEmpmail.getText();
        String phone = txtEmpPhone.getText();

        txtEmpName.setStyle(txtEmpName.getStyle() + ";-fx-border-color: #7367F0;");
        txtEmpNic.setStyle(txtEmpNic.getStyle() + ";-fx-border-color: #7367F0;");
        txtEmpmail.setStyle(txtEmpmail.getStyle() + ";-fx-border-color: #7367F0;");
        txtEmpPhone.setStyle(txtEmpPhone.getStyle() + ";-fx-border-color: #7367F0;");

        String namePattern = "^[A-Za-z ]+$";
        String nicPattern = "^[0-9]{9}[vVxX]||[0-9]{12}$";
        String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        String phonePattern = "^(\\d+)||((\\d+\\.)(\\d){2})$";

        boolean isValidName = name.matches(namePattern);
        boolean isValidNic = nic.matches(nicPattern);
        boolean isValidEmail = email.matches(emailPattern);
        boolean isValidPhone = phone.matches(phonePattern);

        if (!isValidName) {
            System.out.println(txtEmpName.getStyle());
            txtEmpName.setStyle(txtEmpName.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid name.............");
        }
        if (!isValidNic) {
            txtEmpNic.setStyle(txtEmpNic.getStyle() + ";-fx-border-color: red;");
        }
        if (!isValidEmail) {
            txtEmpmail.setStyle(txtEmpmail.getStyle() + ";-fx-border-color: red;");
        }
        if (!isValidPhone) {
            txtEmpPhone.setStyle(txtEmpPhone.getStyle() + ";-fx-border-color: red;");
        }


        if (isValidName && isValidNic && isValidEmail && isValidPhone) {
            EmployeeDTO employeeDTO = new EmployeeDTO(
                    employeeId,
                    name,
                    nic,
                    email,
                    phone
            );
            boolean isSaved = employeeModel.saveEmployee(employeeDTO);
            if (isSaved) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Employee saved...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to save Employee...!").show();
            }
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
        String employeeId = lblEmployeeId.getText();
        String name = txtEmpName.getText();
        String nic = txtEmpNic.getText();
        String email = txtEmpmail.getText();
        String phone = txtEmpPhone.getText();

        txtEmpName.setStyle(txtEmpName.getStyle() + ";-fx-border-color: #7367F0;");
        txtEmpNic.setStyle(txtEmpNic.getStyle() + ";-fx-border-color: #7367F0;");
        txtEmpmail.setStyle(txtEmpmail.getStyle() + ";-fx-border-color: #7367F0;");
        txtEmpPhone.setStyle(txtEmpPhone.getStyle() + ";-fx-border-color: #7367F0;");

        String namePattern = "^[A-Za-z ]+$";
        String nicPattern = "^[0-9]{9}[vVxX]||[0-9]{12}$";
        String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        String phonePattern = "^(\\d+)||((\\d+\\.)(\\d){2})$";

        boolean isValidName = name.matches(namePattern);
        boolean isValidNic = nic.matches(nicPattern);
        boolean isValidEmail = email.matches(emailPattern);
        boolean isValidPhone = phone.matches(phonePattern);

        if (!isValidName) {
            System.out.println(txtEmpName.getStyle());
            txtEmpName.setStyle(txtEmpName.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid name.............");
        }
        if (!isValidNic) {
            txtEmpNic.setStyle(txtEmpNic.getStyle() + ";-fx-border-color: red;");
        }
        if (!isValidEmail) {
            txtEmpmail.setStyle(txtEmpmail.getStyle() + ";-fx-border-color: red;");
        }
        if (!isValidPhone) {
            txtEmpPhone.setStyle(txtEmpPhone.getStyle() + ";-fx-border-color: red;");
        }


        if (isValidName && isValidNic && isValidEmail && isValidPhone) {
            EmployeeDTO employeeDTO = new EmployeeDTO(
                    employeeId,
                    name,
                    nic,
                    email,
                    phone
            );
            boolean isUpdate = employeeModel.updateEmployee(employeeDTO);
            if (isUpdate) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Employee update...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to update Employee...!").show();
            }
        }
    }

    @FXML
    void onClickTable(MouseEvent event) {
        EmployeeTM employeeTM = tblEmployee.getSelectionModel().getSelectedItem();
        if (employeeTM != null) {
            lblEmployeeId.setText(employeeTM.getEmployeeId());
            txtEmpName.setText(employeeTM.getName());
            txtEmpNic.setText(employeeTM.getNic());
            txtEmpmail.setText(employeeTM.getEmail());
            txtEmpPhone.setText(employeeTM.getPhone());

            btnSave.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }

    @FXML
    void resetOnAction(ActionEvent event) throws SQLException {
        refreshPage();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colEmpliyeeId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colNic.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));

        try {
            refreshPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load employee id").show();
        }
    }

    private void refreshPage() throws SQLException {
        loadNextEmployeeId();
        loadTableData();

        btnSave.setDisable(false);

        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);

        txtEmpName.setText("");
        txtEmpNic.setText("");
        txtEmpmail.setText("");
        txtEmpPhone.setText("");
    }

    EmployeeModel employeeModel = new EmployeeModel();

    private void loadTableData() throws SQLException {
        ArrayList<EmployeeDTO> employeeDTOS = employeeModel.getAllEmployee();
        ObservableList<EmployeeTM> employeeTMS = FXCollections.observableArrayList();

        for (EmployeeDTO employeeDTO : employeeDTOS) {
            EmployeeTM employeeTM = new EmployeeTM(
                    employeeDTO.getEmployeeId(),
                    employeeDTO.getName(),
                    employeeDTO.getNic(),
                    employeeDTO.getEmail(),
                    employeeDTO.getPhone()
            );
            employeeTMS.add(employeeTM);
        }
        tblEmployee.setItems(employeeTMS);
    }

    private void loadNextEmployeeId() throws SQLException {
        String nextEmployeeId = employeeModel.getNextEmployeeId();
        lblEmployeeId.setText(nextEmployeeId);
    }
}
