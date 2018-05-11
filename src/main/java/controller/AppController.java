package controller;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.time.*;
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
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import main.Main;
import model.Contract;
import model.Department;
import model.Employee;
import model.Gender;
import model.Requirement;
import persistence.PersistenceManager;

/**
 * The controller class controlling the employees tab and departments tab of the GUI.
 * 
 * @author Erik Kakócz
 *
 */

public class AppController {
	
	PersistenceManager persistenceManager;
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
	TableColumn<Requirement, String> EmployeProgLangTableName;
	
	@FXML
	TableColumn<Requirement, String> EmployeProgLangTableLevel;
	
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
	Label BirthDayValueLabel;
	
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
	TableColumn<Requirement,String> DepartmentProgLangTableName;
	
	@FXML
	TableColumn<Requirement,String> DepartmentProgLangTableLevel;
	
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

	ObservableList<Department> departmentsList;
	
	
	@FXML
    public void initialize(){
		
		try {
			employeesList=FXCollections.observableArrayList();
			log.info("Instantiated observablearraylist");
			EmployeeList.setItems(employeesList);
			log.info("EmployeeList added");
			log.info("Appcontroller instantiated");
			
			EmployeProgLangTableName.setCellValueFactory(new PropertyValueFactory<Requirement,String>("languageNameProperty"));
			EmployeProgLangTableLevel.setCellValueFactory(new PropertyValueFactory<Requirement,String>("languageLevelProperty"));
			
			
			departmentsList=FXCollections.observableArrayList();
			log.info("Instantiated observablearraylist");
			DepartmentListView.setItems(departmentsList);
			
			DepartmentProgLangTableName.setCellValueFactory(new PropertyValueFactory<Requirement,String>("languageNameProperty"));
			DepartmentProgLangTableLevel.setCellValueFactory(new PropertyValueFactory<Requirement,String>("languageLevelProperty"));
			
			}catch(Exception e) {
				log.error(e.toString());
			}
	}
	
	
	/*
	 * Employees tab functions
	 * */
	
	public AppController() {
		log=LoggerFactory.getLogger(AppController.class);
	}
	
	@FXML
	public void selectElement() {
		try {
			Employee selected=EmployeeList.getSelectionModel().getSelectedItem();
			if(selected!=null) {
				log.debug("employee Name: "+selected.getName());
				NameValueLabel.setText(selected.getName());
				log.debug("employee Gender: "+selected.getGender());
				GenderValueLabel.setText(selected.getGender().toString());
				log.debug("employee DoB: "+selected.getAge().toString());
				BirthDayValueLabel.setText(selected.getAge().toString());
				
				
				ObservableList<Requirement> list=ProgLanguagesTable.getItems();
				list.clear();
				list.addAll(selected.getKnownLangs());
				ProgLanguagesTable.setItems(list);
				
				DepartmentValueLabel.setText("not yet");
			}
		}catch(Exception e) {
			log.error("Error: "+e.getStackTrace());
			
		}
	}

	/**
	 * The method responsible for refreshing the database.
	 * 
	 * @author Erik Kakócz
	 *
	 */
	
	public void refreshDatabase() {
		ObservableList<Employee> list=persistenceManager.getAllEmployees();
		if(list!=null) {
			employeesList.addAll(persistenceManager.getAllEmployees());
			for(Employee e:list)
				log.debug("employeeData: "+e);
		
		}
		
	}
	
	/**
	 * Returns the name of the department given employee works at.
	 * 
	 * @author Erik Kakócz
	 *
	 */
	
	private String getDeptOf(Employee selected) {
		long id=persistenceManager.findContractByEmployee(selected).getDeptId();
		return persistenceManager.getDepartmentById(id).getDepartmentName(); 
	}

	
	
	public PersistenceManager getPersistenceManager() {
		return persistenceManager;
	}

	public void setPersistenceManager(PersistenceManager persistenceManager) {
		this.persistenceManager = persistenceManager;
		refreshDatabase();
		refreshDepartmentDatabase();
	}
	
	/**
	 * Method running when the add button is clicked on the employees tab. 
	 * Brings up a window where an employee can be added to the database.
	 * 
	 * @author Erik Kakócz
	 *
	 */

	public void onAddEmployeeClicked() {
		Stage employeeStage = new Stage();
		Parent root;
		FXMLLoader loader=new FXMLLoader();
		URL url=getClass().getClassLoader().getResource("add_edit_employee.fxml");
		loader.setLocation(url);
		
		try {
			root = loader.load();
			Employee emp=null;
			
			employeeStage.setScene(new Scene(root, 600, 400));
			employeeStage.setTitle("Add employee");
			EmployeeController controller = loader.getController();
			
			controller.setMode("add");
			employeeStage.showAndWait();
			
			emp=controller.getResultEmployee();
			if(emp!=null) {
				persistenceManager.persistEmployee(emp);
				employeesList.add(emp);
			}	
			else
				log.info("Cancel was pressed.");	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	/**
	 * Method running when the edit button is clicked on the employees tab. 
	 * Brings up a window where a selected employee can be edited.
	 * 
	 * @author Erik Kakócz
	 *
	 */
	
	public void onEditEmployeeClicked() {
		Stage employeeStage = new Stage();
		Parent root;
		FXMLLoader loader=new FXMLLoader();
		URL url=getClass().getClassLoader().getResource("add_edit_employee.fxml");
		loader.setLocation(url);
		
		try {
			root = loader.load();
			Employee emp=null;
			
			employeeStage.setScene(new Scene(root, 600, 400));
			employeeStage.setTitle("Edit employee");
			EmployeeController controller = loader.getController();
			
			Employee employee=employeesList.get(EmployeeList.getSelectionModel().getSelectedIndex());
			
			controller.setEmployee(employee);
			controller.setMode("edit");
			employeeStage.showAndWait();
			
			emp=controller.getResultEmployee();
			if(emp!=null) {
				persistenceManager.updateEmployee(emp);
				selectElement();
			}	
			else
				log.info("Cancel was pressed.");	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	/**
	 * Method running when the delete button is clicked on the employees tab. 
	 * Deletes selected employee.
	 * 
	 * @author Erik Kakócz
	 *
	 */
	
	public void onDeleteClicked() {
		Employee employee=EmployeeList.getSelectionModel().getSelectedItem();
		persistenceManager.deleteEmployee(employee);
		employeesList.remove(EmployeeList.getSelectionModel().getSelectedIndex());
		
		
	}


	/**
	 * Method running when the assign button is clicked on the employees tab. 
	 * Brings up a window where an employee can assigned to a department.
	 * 
	 * @author Erik Kakócz
	 *
	 */
	
	public void assignDepartment() {
		
		Stage departmentAssignStage = new Stage();
		Parent root;
		FXMLLoader loader=new FXMLLoader();
		URL url=getClass().getClassLoader().getResource("assign_department.fxml");
		loader.setLocation(url);
		
		try {
			root = loader.load();
			
			
			departmentAssignStage.setScene(new Scene(root, 600, 400));
			departmentAssignStage.setTitle("Assign department");
			ContractController controller = loader.getController();
			
			Employee selected=EmployeeList.getSelectionModel().getSelectedItem();
			Contract contract = persistenceManager.findContractByEmployee(selected);
			log.error("<---------------------------------------------------------------------------------->");
			if(contract!=null)
				controller.setContract(contract);
			departmentAssignStage.showAndWait();
			log.error("sasd2");
			Contract cont=controller.getResultContract();
			if(contract==null && cont!=null) {
				persistenceManager.persistContract(cont);
				log.error("sasd3");	
			}else if(contract!=null && cont!=null){
				persistenceManager.updateContract(cont);
			}
			else
				log.info("Cancel was pressed.");
			
			
		}catch(Exception e) {
			log.error("sasd");
			
		}
	}


	/**
	 * Method running when the dismiss button is clicked on the employees tab. 
	 * Deletes the contract object from database.
	 * 
	 * @author Erik Kakócz
	 *
	 */
	
	public void dismissFromDepartment() {

		Employee selected=EmployeeList.getSelectionModel().getSelectedItem();
		Contract contract = persistenceManager.findContractByEmployee(selected);;
		persistenceManager.removeContract(contract);
		
	}
	
	/*
	 * department functions
	 * 
	 * */
	
	/**
	 * Method running when the assign button is clicked on the employees tab. 
	 * Brings up a window where an employee can assigned to a department.
	 * 
	 * @author Erik Kakócz
	 *
	 */
	
	@FXML
	public void selectDepartmentElement() {
		try {
			Department selected=DepartmentListView.getSelectionModel().getSelectedItem();
			log.debug(selected.toString());
			if(selected!=null) {
				log.debug("department Name: "+selected.getDepartmentName());
				DeptnameValueLabel.setText(selected.getDepartmentName());
				log.debug("department leader: "+selected.getDepartmentLeader());
				DeptLeaderValueLabel.setText(selected.getDepartmentLeader());
				
				
				ObservableList<Requirement> list=LanguagesTableDept.getItems();
				list.clear();
				list.addAll(selected.getRequiredLevel());
				LanguagesTableDept.setItems(list);
			}
		}catch(Exception e) {
			log.error("Error: "+e.getStackTrace());
			
		}
	}
	
	private void refreshDepartmentDatabase() {
		ObservableList<Department> list=persistenceManager.getAllDepartments();
		if(list!=null)
			departmentsList.addAll(list);
	}
	
	
	
	public void onDeptAddClicked() {
		Stage deptStage = new Stage();
		Parent root;
		FXMLLoader loader=new FXMLLoader();
		URL url=getClass().getClassLoader().getResource("add_edit_department.fxml");
		loader.setLocation(url);
		
		try {
			root = loader.load();
			Department dept=null;
			
			deptStage.setScene(new Scene(root, 600, 400));
			deptStage.setTitle("Add department");
			DepartmentController controller = loader.getController();
			
			//controller.setMode("add");
			deptStage.showAndWait();
			
			dept=controller.getResultDepartment();
			if(dept!=null) {
				persistenceManager.persistDepartment(dept);
				departmentsList.add(dept);
			}	
			else
				log.info("Cancel was pressed.");	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void onDeptEditClicked() {
		Stage deptStage = new Stage();
		Parent root;
		FXMLLoader loader=new FXMLLoader();
		URL url=getClass().getClassLoader().getResource("add_edit_department.fxml");
		loader.setLocation(url);
		
		try {
			root = loader.load();
			Department dept=null;
			dept=DepartmentListView.getSelectionModel().getSelectedItem();
			
			deptStage.setScene(new Scene(root, 600, 400));
			deptStage.setTitle("Add department");
			DepartmentController controller = loader.getController();
			
			controller.setDepartment(dept);
			deptStage.showAndWait();
			
			dept=controller.getResultDepartment();
			if(dept!=null) {
				persistenceManager.updateDepartment(dept);
				
			}	
			else
				log.info("Cancel was pressed.");	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public void onDeptDeleteClicked() {
		Department department=DepartmentListView.getSelectionModel().getSelectedItem();
		persistenceManager.deleteDepartment(department);
		departmentsList.remove(DepartmentListView.getSelectionModel().getSelectedIndex());
	}
	
	
}


