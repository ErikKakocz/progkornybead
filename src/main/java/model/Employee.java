package model;

import java.io.Serializable;
import java.util.Date;
import java.time.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


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
	
	private LocalDate birthDate;
	
	private Requirement[] knownLangs;
	
	
	public Employee() {
		super();
	}

	public Employee(long id, String name, Gender gender, LocalDate BirthDate, long deptId) {
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

	public LocalDate getAge() {
		return birthDate;
	}

	public void setAge(LocalDate date) {
		this.birthDate = date;
	}

	public long getId() {
		return id;
	}
	
	

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", gender=" + gender + ", birthDate=" + birthDate + "]";
	}

	public Requirement[] getKnownLangs() {
		return knownLangs;
	}

	public void setKnownLangs(Requirement[] knownLangs) {
		this.knownLangs = knownLangs;
	}



	
	
	
}
