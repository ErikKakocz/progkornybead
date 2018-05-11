package controller;

import java.io.IOException;
import java.net.URL;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Department;
import model.Requirement;

public class DepartmentController {

	Department department;
	Department resultDepartment;
	
	@FXML
	TableView<Requirement> LanguagesTable;
	
	@FXML
	TextField NameField;
	
	@FXML
	TextField DeptLeaderField;
	
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

	public Department getResultDepartment() {
		return resultDepartment;
	}

	public Department getDepartment() {
		return department;
	}

	@FXML
	public void initialize() {
		department=new Department();
		ObservableList<Requirement> requirementList=FXCollections.observableArrayList();
		LanguagesTable.setItems(requirementList);
	}
	
	public void setDepartment(Department department) {
		this.department = department;
		NameField.setText(department.getDepartmentName());
		DeptLeaderField.setText(department.getDepartmentLeader());
		Requirement[] reqs=department.getRequiredLevel();
		LanguagesTable.getItems().addAll(reqs);
	}
	
	public void onOkClicked() {
		resultDepartment=new Department();
		if(department!=null)
		resultDepartment.setId(department.getId());
		resultDepartment.setDepartmentName(NameField.getText());
		resultDepartment.setDepartmentLeader(DeptLeaderField.getText());
		Requirement[] languages=new Requirement[LanguagesTable.getItems().size()];
		int i=0;
		for(Requirement r:LanguagesTable.getItems()){
			languages[i]=r;
			i++;
		}
		resultDepartment.setRequiredLevel(languages);
		((Stage)OkButton.getScene().getWindow()).close();
	}
	
	public void onCancelClicked() {
		resultDepartment=null;
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
