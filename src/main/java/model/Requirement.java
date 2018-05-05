package model;

public class Requirement {

	String ProgLanguage;
	
	RequirementLevel level;

	
	
	public Requirement() {
		super();
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
		ProgLanguage = progLanguage;
	}

	public RequirementLevel getLevel() {
		return level;
	}

	public void setLevel(RequirementLevel level) {
		this.level = level;
	}
	
	
	
}
