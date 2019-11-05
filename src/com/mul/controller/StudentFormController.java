package com.mul.controller;

import com.mul.Main;
import com.mul.dao.DepartmentDaoImpl;
import com.mul.dao.MahasiswaDaoImpl;
import com.mul.entity.Mahasiswa;
import com.mul.entity.ProgramStudi;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class StudentFormController implements Initializable {
    @FXML
    private Button btnSave;
    @FXML
    private Button btnReset;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnUpdate;

    @FXML
    private TextField txtNRP;
    @FXML
    private TextField txtNamaDepan;
    @FXML
    private TextField txtNamaBelakang;
    @FXML
    private TextField txtTempatLahir;
    @FXML
    private TextField txtAlamat;
    @FXML
    private TextField txtEmail;
    @FXML
    private DatePicker datepicker;
    @FXML
    private ComboBox<ProgramStudi> comboBox;
    @FXML
    private TableView<Mahasiswa> tableDepartment;
    @FXML
    private TableColumn<Mahasiswa, String> col01;
    @FXML
    private TableColumn<Mahasiswa, String> col02;
    @FXML
    private TableColumn<Mahasiswa, String> col03;
    @FXML
    private TableColumn<Mahasiswa, String> col04;
    @FXML
    private TableColumn<Mahasiswa, String> col05;
    @FXML
    private TableColumn<Mahasiswa, String> col06;
    @FXML
    private TableColumn<Mahasiswa, String> col07;
    @FXML
    private TableColumn<Mahasiswa, String> col08;
    private ObservableList<Mahasiswa> students;
    private MahasiswaDaoImpl studentsDao;
    private Mahasiswa selectedItems;
    private ObservableList<ProgramStudi> departments;
    private DepartmentDaoImpl departmentDao;

    public ObservableList<Mahasiswa> getMahasiswa() {
        if (students == null) {
            students = FXCollections.observableArrayList();
            students.addAll(getMahasiswaDao().showAll());
        }
        return students;
    }

    public MahasiswaDaoImpl getMahasiswaDao() {
        if (studentsDao == null) {
            studentsDao = new MahasiswaDaoImpl();
        }
        return studentsDao;
    }

    public ObservableList<ProgramStudi> getDepartment() {
        if (departments == null) {
            departments = FXCollections.observableArrayList();
            departments.addAll(getDepartmentDao().showAll());
        }
        return departments;
    }

    public DepartmentDaoImpl getDepartmentDao() {
        if (departmentDao == null) {
            departmentDao = new DepartmentDaoImpl();
        }
        return departmentDao;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
        tableDepartment.setItems(getMahasiswa());
        getDepartment();
        comboBox.setItems(getDepartment());
        col01.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getId()));
        col02.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNamaDepan()));
        col03.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNamaBelakang()));
        col04.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(Date.valueOf(data.getValue().getTanggalLahir().toLocalDate()))));
        col05.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTempatLahir()));
        col06.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAlamat()));
        col07.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEmail()));
        col08.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getProgramStudi().getNama()));
    }

    @FXML
    private void savedAct(ActionEvent actionEvent) {
        Mahasiswa m = new Mahasiswa();
        m.setId(txtNRP.getText());
        m.setNamaDepan(txtNamaDepan.getText());
        m.setNamaBelakang(txtNamaBelakang.getText());
        m.setTempatLahir(txtTempatLahir.getText());
        m.setTanggalLahir(Date.valueOf(datepicker.getValue()));
        m.setAlamat(txtAlamat.getText());
        m.setEmail(txtEmail.getText());
        m.setProgramStudi(comboBox.getValue());
        boolean notDuplicate = getMahasiswa().stream().filter(d -> d.getId() == m.getId()).count() == 0;
        if (notDuplicate) {
            getMahasiswaDao().addData(m);
            txtNRP.clear();
            txtNamaDepan.clear();
            txtNamaBelakang.clear();
            txtTempatLahir.clear();
            datepicker.setValue(null);
            txtAlamat.clear();
            txtEmail.clear();
            refreshTable();
        }
        clearForm();
    }

    @FXML
    private void resetAct(ActionEvent actionEvent) {
        clearForm();
    }

    @FXML
    private void updateAct(ActionEvent actionEvent) {
        if (!txtNamaBelakang.getText().trim().isEmpty() || !txtNamaDepan.getText().trim().isEmpty() || !txtTempatLahir.getText().trim().isEmpty() || txtAlamat.getText().trim().isEmpty()) {
            selectedItems.setNamaDepan(txtNamaDepan.getText());
            selectedItems.setNamaBelakang(txtNamaBelakang.getText());
            selectedItems.setTempatLahir(txtTempatLahir.getText());
            selectedItems.setTanggalLahir(Date.valueOf(datepicker.getValue()));
            selectedItems.setAlamat(txtAlamat.getText());
            selectedItems.setEmail(txtEmail.getText());
            selectedItems.setProgramStudi(comboBox.getValue());
            getMahasiswaDao().updateData(selectedItems);
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
            getMahasiswaDao().deleteData(selectedItems);
            refreshTable();
            clearForm();
        }
    }

    private void refreshTable() {
        getMahasiswa().clear();
        getMahasiswa().addAll(getMahasiswaDao().showAll());
    }

    private void clearForm() {
        txtNRP.clear();
        txtNamaDepan.clear();
        txtNamaBelakang.clear();
        txtEmail.clear();
        txtAlamat.clear();
        datepicker.setValue(null);
        txtTempatLahir.clear();
        btnSave.setDisable(false);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
        tableDepartment.getSelectionModel().clearSelection();
        txtNRP.setDisable(false);
        selectedItems = null;
    }

    @FXML
    private void tableClicked(MouseEvent mouseEvent) {
        selectedItems = tableDepartment.getSelectionModel().getSelectedItem();
        txtNRP.setText(String.valueOf(selectedItems.getId()));
        txtNamaDepan.setText(selectedItems.getNamaDepan());
        txtNamaBelakang.setText(selectedItems.getNamaBelakang());
        txtEmail.setText(selectedItems.getEmail());
        txtAlamat.setText(selectedItems.getAlamat());
        txtTempatLahir.setText(selectedItems.getTempatLahir());
        datepicker.setValue((selectedItems.getTanggalLahir().toLocalDate()));
        comboBox.setValue(selectedItems.getProgramStudi());
        txtNRP.setDisable(true);
        btnDelete.setDisable(false);
        btnSave.setDisable(true);
        btnUpdate.setDisable(false);
    }

    @FXML
    private void showDepartmentFormController(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/DepartmentForm.fxml"));
            VBox root = loader.load();
            DepartmentFormController departmentFormController = loader.getController();
            departmentFormController.setController(this);
            Stage mainStage = new Stage();
            mainStage.initModality(Modality.WINDOW_MODAL);
            mainStage.setTitle("Category Management Form");
            mainStage.setScene(new Scene(root));
            mainStage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
