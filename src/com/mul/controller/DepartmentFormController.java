package com.mul.controller;

import com.mul.entity.ProgramStudi;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class DepartmentFormController implements Initializable {
    @FXML
    private TextField txtID;
    @FXML
    private TextField txtNama;
    @FXML
    private TableView<ProgramStudi> tableDepartment;
    @FXML
    private TableColumn<ProgramStudi, Integer> col01;
    @FXML
    private TableColumn<ProgramStudi, String> col02;
//    private ObservableList<ProgramStudi> departments;
//    private DepartmentDaoImpl departmentDao;

    private ProgramStudi selectedItems;

    public DepartmentFormController departmentFormController ;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnReset;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;
    private StudentFormController controller;

    public void setController(StudentFormController controller) {
        this.controller = controller;
        tableDepartment.setItems(controller.getDepartment());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
/*        tableDepartment.setItems(controller.getDepartment());*/
        col01.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getId()).asObject());
        col02.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNama()));
    }

    @FXML
    private void tableClicked(MouseEvent mouseEvent) {
        selectedItems = tableDepartment.getSelectionModel().getSelectedItem();
        txtID.setText(String.valueOf(selectedItems.getId()));
        txtNama.setText(selectedItems.getNama());
        txtID.setDisable(true);
        btnDelete.setDisable(false);
        btnSave.setDisable(true);
        btnUpdate.setDisable(false);
    }

    private void clearForm() {
        txtID.clear();
        txtNama.clear();
        btnSave.setDisable(false);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
        tableDepartment.getSelectionModel().clearSelection();
        txtID.setDisable(false);
        selectedItems = null;
    }

    @FXML
    private void saveAct(ActionEvent actionEvent) {
        ProgramStudi ps = new ProgramStudi();
        ps.setId(Integer.parseInt(txtID.getText()));
        ps.setNama(txtNama.getText());
        boolean notDuplicate = controller.getDepartment().stream().filter(d -> d.getId() == ps.getId()).count() == 0;
        if (notDuplicate) {
            controller.getDepartmentDao().addData(ps);
            txtID.clear();
            txtNama.clear();
            refreshTable();
        }
        clearForm();
        controller.getMahasiswa().clear();
        controller.getMahasiswa().addAll(controller.getMahasiswaDao().showAll());
    }

    @FXML
    private void resetAct(ActionEvent actionEvent) {
        clearForm();
    }

    @FXML
    private void updateAct(ActionEvent actionEvent) {
        if (!txtNama.getText().trim().isEmpty()) {
            selectedItems.setNama(txtNama.getText());
            controller.getDepartmentDao().updateData(selectedItems);
            clearForm();
            refreshTable();
        }
    }

    @FXML
    private void deleteAct(ActionEvent actionEvent) {
        Alert deleteConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
        deleteConfirmation.setContentText("Apakah Anda yakin akan menghapus data");
        deleteConfirmation.setTitle("Konfirmasi Hapus");
        deleteConfirmation.showAndWait();
        if (deleteConfirmation.getResult() == ButtonType.OK) {
            selectedItems = tableDepartment.getSelectionModel().getSelectedItem();
            controller.getDepartmentDao().deleteData(selectedItems);
            refreshTable();
            clearForm();
        }
    }

    private void refreshTable() {
        controller.getDepartment().clear();
        controller.getDepartment().addAll(controller.getDepartmentDao().showAll());
    }
}
