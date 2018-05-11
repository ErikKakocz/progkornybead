package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Requirement;
import model.RequirementLevel;

public class LanguageController {

	Requirement requirement;
	Requirement resultRequirement;
	
	String mode="add";
	
	@FXML
	public void initialize() {
		
		ObservableList<RequirementLevel> levels=FXCollections.observableArrayList();
		levels.addAll(RequirementLevel.NOOB,RequirementLevel.BEGINNER,RequirementLevel.INTERMEDIATE,RequirementLevel.PROFESSIONAL);
		LevelChoiceBox.setItems(levels);
		
		
	}
	
	@FXML
	TextField LanguageField;
	
	@FXML
	ChoiceBox<RequirementLevel> LevelChoiceBox;
	
	@FXML
	Button OkButton;
	
	@FXML
	Button CancelButton;
	
	@FXML
	public void onOkClicked() {
		resultRequirement=new Requirement();
		resultRequirement.setProgLanguage(LanguageField.getText());
		resultRequirement.setLevel(LevelChoiceBox.getValue());
		
		
		((Stage)OkButton.getScene().getWindow()).close();
	}
	
	public void onCancelClicked() {
		resultRequirement=null;
		((Stage)OkButton.getScene().getWindow()).close();
	}

	public Requirement getRequirement() {
		return requirement;
	}

	public void setRequirement(Requirement requirement) {
		this.requirement = requirement;
		LanguageField.setText(requirement.getProgLanguage().toString());
		LevelChoiceBox.setValue(requirement.getLevel());
	}

	public Requirement getResultRequirement() {
		return resultRequirement;
	}

	public void setResultRequirement(Requirement resultRequirement) {
		this.resultRequirement = resultRequirement;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}
	
	
	
}
