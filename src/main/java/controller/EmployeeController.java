package controller;

import java.io.IOException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Employee;
import model.Gender;
import model.Requirement;

public class EmployeeController {
	
	Employee employee;
	Employee resultEmployee;
	String mode;
	Logger log;
	
	@FXML
	TableView<Requirement> LanguagesTable;
	
	@FXML
	TableColumn<Requirement,String> LanguageTableColumn;
	
	@FXML
	TableColumn<Requirement,String> LevelTableColumn;
	
	@FXML
	Button OkButton;
	
	@FXML
	Button CancelButton;
	
	@FXML
	Button AddLanguageButton;
	
	@FXML
	Button EditLanguageButton;
	
	@FXML
	Button RemoveLanguageButton;
	
	@FXML
	TextField NameField;
	
	@FXML
	ChoiceBox<Gender> EmployeeGenderPicker;
	
	@FXML
	DatePicker EmployeeDatePicker;
	
	
	@FXML
	public void initialize() {
		log=LoggerFactory.getLogger(EmployeeController.class);
		ObservableList<Gender> genderList=FXCollections.observableArrayList();
		genderList.addAll(Gender.MALE,Gender.FEMALE);
		EmployeeGenderPicker.setItems(genderList);
		
		
		LanguageTableColumn.setCellValueFactory(new PropertyValueFactory<Requirement,String>("languageNameProperty"));
		LevelTableColumn.setCellValueFactory(new PropertyValueFactory<Requirement,String>("languageLevelProperty"));
		
		

	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
		NameField.setText(employee.getName());
		EmployeeGenderPicker.setValue(employee.getGender());
		EmployeeDatePicker.setValue(employee.getAge());
		ObservableList<Requirement> langs=FXCollections.observableArrayList();
		langs.addAll(employee.getKnownLangs());
		LanguagesTable.setItems(langs);
	
		
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public Employee getResultEmployee() {
		return resultEmployee;
	}

	public void setResultEmployee(Employee resultEmployee) {
		this.resultEmployee = resultEmployee;
	}
	
	public void onOkClicked() {
		resultEmployee=new Employee();
		log.info("employee: "+(employee!=null)+" modositott employee:"+(resultEmployee!=null));
		if(mode.equals("edit"))
			resultEmployee.setId(employee.getId());
		resultEmployee.setName(NameField.getText());
		resultEmployee.setAge(EmployeeDatePicker.getValue());
		resultEmployee.setGender(EmployeeGenderPicker.getValue());
		Requirement[] languages=new Requirement[LanguagesTable.getItems().size()];
		int i=0;
		for(Requirement r:LanguagesTable.getItems()){
			languages[i]=r;
			i++;
		}
		resultEmployee.setKnownLangs(languages);
		((Stage)OkButton.getScene().getWindow()).close();
		
	}
	
	public void onCancelClicked() {
		resultEmployee=null;
		((Stage)OkButton.getScene().getWindow()).close();
	}
	
	public void onAddLangClicked() {
		Stage languageStage = new Stage();
		Parent root;
		FXMLLoader loader=new FXMLLoader();
		URL url=getClass().getClassLoader().getResource("add_edit_language.fxml");
		loader.setLocation(url);
		
		try {
			root = loader.load();
			Requirement lang=null;
			
			languageStage.setScene(new Scene(root, 600, 400));
			languageStage.setTitle("Add Language");
			LanguageController controller = loader.getController();
			
			controller.setMode("add");
			languageStage.showAndWait();
			
			lang=controller.getResultRequirement();
			if(lang!=null) {
				ObservableList<Requirement> items=LanguagesTable.getItems();
				items.add(lang);
				LanguagesTable.setItems(items);
			}
			else
				log.info("Cancel was pressed.");	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void onEditLangClicked() {
		Stage languageStage = new Stage();
		Parent root;
		FXMLLoader loader=new FXMLLoader();
		URL url=getClass().getClassLoader().getResource("add_edit_language.fxml");
		loader.setLocation(url);
		int index=LanguagesTable.getSelectionModel().getSelectedIndex();
		Requirement requirement=LanguagesTable.getSelectionModel().getSelectedItem();
		try {
			root = loader.load();
			Requirement lang=null;
			
			languageStage.setScene(new Scene(root, 600, 400));
			languageStage.setTitle("Edit Language");
			LanguageController controller = loader.getController();
			
			controller.setMode("edit");
			controller.setRequirement(requirement);
			languageStage.showAndWait();
			
			lang=controller.getResultRequirement();
			if(lang!=null) {
				ObservableList<Requirement> items=LanguagesTable.getItems();
				items.remove(index);
				items.add(index, lang);
				LanguagesTable.setItems(items);
			}
			else
				log.info("Cancel was pressed.");	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void onRemoveLangClicked() {
		int index=LanguagesTable.getSelectionModel().getSelectedIndex();
		ObservableList<Requirement> items=LanguagesTable.getItems();
		items.remove(index);
		LanguagesTable.setItems(items);
	}
	
}
