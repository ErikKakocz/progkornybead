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
	
	Requirement requirement;
	
	private String departmentName;

	
	
	public Department() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Department(long id, Requirement required, String departmentName) {
		super();
		this.id = id;
		this.requirement = required;
		this.departmentName = departmentName;
	}

	/**
	 * Returns the knowledge/expertise and it's required level 
	 * this department requires from it's employees.
	 * 
	 * @return RequirementLevel
	 */
	public Requirement getRequiredLevel() {
		return requirement;
	}

	/**
	 * 
	 * @param requiredLevel
	 */
	
	public void setRequiredLevel(Requirement requirement) {
		this.requirement = requirement;
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

	@Override
	public String toString() {
		return "Department [id=" + id + ", departmentName=" + departmentName + "]";
	}
	
	
}
