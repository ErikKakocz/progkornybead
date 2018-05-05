package controller;

import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ModifiableObservableListBase;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import main.Main;
import model.Department;
import model.Employee;
import model.Gender;
import model.Requirement;

/**
 * The controller class controlling the employees tab of the GUI.
 * 
 * @author Shadowwolf
 *
 */

public class AppController {
	

	Logger log;
	/*
	 * Employee tab
	 * */
	ObservableList<Employee> employeesList;
	
	@FXML
	ListView<Employee> EmployeeList;
	
	@FXML
	TableView<Requirement> ProgLanguagesTable;
	
	@FXML
	Button AddButton;

	@FXML
	Button EditButton;

	@FXML
	Button DeleteButton;
	
	@FXML
	Button AssignDeptButton;
	
	@FXML
	Button DismissFromDeptButton;
	
	@FXML
	Label NameValueLabel;
	
	@FXML
	Label GenderValueLabel;
	
	@FXML
	Label BirthDateValueLabel;
	
	@FXML
	Label DepartmentValueLabel;
	
	/*
	 * Department tab
	 * */
	
	@FXML
	ListView<Department> DepartmentListView;
	
	@FXML
	TableView<Requirement> LanguagesTableDept;
	
	@FXML
	Button AddDepartmentButton;

	@FXML
	Button EditDepartmentButton;

	@FXML
	Button DeleteDepartmentButton;
	
	@FXML
	Label DeptnameValueLabel;

	@FXML
	Label DeptLeaderValueLabel;

	/*
	 * Employees tab functions
	 * */
	
	public AppController() {
		log=LoggerFactory.getLogger(Main.class);
	}
	
	@FXML
    public void initialize(){
		
		try {
			
			employeesList=FXCollections.observableArrayList();
			log.info("Instantiated observablearraylist");
			EmployeeList.setItems(employeesList);
			log.info("EmployeeList added");
			EmployeeList.getItems().add(new Employee(0,"Shadow",Gender.MALE,new Date(1991,07,20),30));
			log.info("Appcontroller instantiated");
			
			}catch(Exception e) {
				log.error(e.toString());
				
				
			}
	}
	
	public void selectElement() {
		log.info(EmployeeList.getSelectionModel().getSelectedItem().toString());
		
	}
	
}
