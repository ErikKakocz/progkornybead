package model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Entity class for persisting Employee objects.
 * 
 * @author Shadowwolf
 *
 */

@Entity
@Table(name="Employee")
public class Employee implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	private String name;
	
	private Gender gender;
	
	private Date birthDate;
	
	
	public Employee() {
		super();
	}

	public Employee(long id, String name, Gender gender, Date BirthDate, long deptId) {
		super();
		this.id = id;
		this.name = name;
		this.gender = gender;
		this.birthDate = BirthDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Date getAge() {
		return birthDate;
	}

	public void setAge(Date age) {
		this.birthDate = age;
	}

	public long getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", gender=" + gender + ", birthDate=" + birthDate + "]";
	}
	
	
	
	
}
