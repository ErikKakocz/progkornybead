package model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Class used in representing and persisting the departments of given 
 * company where the employees work.  
 * @author Erik Kakócz a.k.a. Shadowwolf
 *
 */
@Entity
@Table(name="department")
public class Department implements Serializable{

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	private String requiredLanguage;
	
	@Enumerated
	private RequirementLevel requiredLevel;
	
	private String departmentName;

	
	
	public Department() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Department(long id, String requiredLanguage, RequirementLevel requiredLevel, String departmentName) {
		super();
		this.id = id;
		this.requiredLanguage = requiredLanguage;
		this.requiredLevel = requiredLevel;
		this.departmentName = departmentName;
	}

	
	/**
	 * Returns the programming language which is required for filling 
	 * positions at this department.
	 * 
	 * @return String
	 */
	public String getRequiredLanguage() {
		return requiredLanguage;
	}

	/**
	 * Takes a string argument and sets it as the language required for this department.
	 * 
	 * @param requiredLanguage
	 */
	public void setRequiredLanguage(String requiredLanguage) {
		this.requiredLanguage = requiredLanguage;
	}

	/**
	 * Returns the level of knowledge/expertise this department requires from it's employees.
	 * 
	 * @return RequirementLevel
	 */
	public RequirementLevel getRequiredLevel() {
		return requiredLevel;
	}

	/**
	 * 
	 * @param requiredLevel
	 */
	
	public void setRequiredLevel(RequirementLevel requiredLevel) {
		this.requiredLevel = requiredLevel;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public long getId() {
		return id;
	}
	
	
}
