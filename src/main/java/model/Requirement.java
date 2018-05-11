package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import javafx.beans.property.SimpleStringProperty;

import java.io.Serializable;

@Entity
public class Requirement implements Serializable{


	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	String ProgLanguage;
	
	RequirementLevel level;
	
	@Transient
	transient SimpleStringProperty languageNameProperty;
	
	@Transient
	transient SimpleStringProperty languageLevelProperty;
	
	public Requirement() {
		super();
		languageNameProperty=new SimpleStringProperty();
		languageLevelProperty=new SimpleStringProperty();
	}

	public Requirement(String progLanguage, RequirementLevel level) {
		super();
		ProgLanguage = progLanguage;
		this.level = level;
	}

	public String getProgLanguage() {
		return ProgLanguage;
	}

	public void setProgLanguage(String progLanguage) {
		this.ProgLanguage = progLanguage;
		
	}

	public RequirementLevel getLevel() {
		return level;
	}

	public void setLevel(RequirementLevel level) {
		this.level = level;
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLanguageNameProperty() {
		languageNameProperty=new SimpleStringProperty();
		languageNameProperty.set(ProgLanguage.toString());
		return languageNameProperty.get();
	}

	public void setLanguageNameProperty(SimpleStringProperty languageNameProperty) {
		this.languageNameProperty = languageNameProperty;
	}

	public String getLanguageLevelProperty() {
		languageLevelProperty=new SimpleStringProperty();
		languageLevelProperty.set(level.toString());
		return languageLevelProperty.get();
	}

	public void setLanguageLevelProperty(SimpleStringProperty languageLevelProperty) {
		this.languageLevelProperty = languageLevelProperty;
	}
	
	
}
