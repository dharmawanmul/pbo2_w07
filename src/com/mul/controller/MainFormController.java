package com.mul.controller;

import com.mul.dao.DepartmentDaoImpl;
import com.mul.entity.ProgramStudi;
import com.mul.util.DBHelper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;

public class MainFormController implements Initializable {

    @FXML
    private ComboBox<ProgramStudi> cmbxProdi;
    private ObservableList<ProgramStudi> departments;
    private DepartmentDaoImpl departmentDao;

    @FXML
    private void actionViewSelected(ActionEvent actionEvent) {
        try {
            HashMap param = new HashMap<>();
            param.put("prodi",cmbxProdi.getValue().getId());
            JasperPrint jp = JasperFillManager.fillReport("report/report2.jasper", param, DBHelper.createMySQLConnection());
            JasperViewer view = new JasperViewer(jp, false);
            view.setTitle("Report Name");
            view.setVisible(true);
        } catch (JRException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void actionViewAll(ActionEvent actionEvent) {
        try {
            HashMap param = new HashMap<>();
            JasperPrint jp = JasperFillManager.fillReport("report/report1.jasper", param, DBHelper.createMySQLConnection());
            JasperViewer view = new JasperViewer(jp, false);
            view.setTitle("Report Name");
            view.setVisible(true);
        } catch (JRException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public DepartmentDaoImpl getDepartmentDao() {
        if (departmentDao == null) {
            departmentDao = new DepartmentDaoImpl();
        }
        return departmentDao;
    }
    public ObservableList<ProgramStudi> getDepartments() {
        if (departments == null) {
            departments = FXCollections.observableArrayList();
            departments.addAll(getDepartmentDao().showAll());
        }

        return departments;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cmbxProdi.setItems(getDepartments());
    }
}
