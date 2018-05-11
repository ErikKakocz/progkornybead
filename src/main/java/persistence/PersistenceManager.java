package persistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.Main;
import model.Contract;
import model.Department;
import model.Employee;



public class PersistenceManager {
	
	
	private EntityManager manager;
	private EntityManagerFactory factory;
	Logger log;
	
	public PersistenceManager() {
		log=LoggerFactory.getLogger(Main.class);
		factory=Persistence.createEntityManagerFactory("ProgrammerJPA");
		manager=factory.createEntityManager();
	}
	
	public void disconnect() {
		try {
		log.info("Closing entityManager connection...");
		manager.close();
		factory.close();
		}catch(Exception e) {
			log.error(e.getMessage());
		}		
	}
	
	public void persistEmployee(Employee emp) {
		log.info("Persisting "+emp);
		manager.getTransaction().begin();
		manager.persist(emp);
		manager.getTransaction().commit();
	}
	
	public void persistDepartment(Department dept) {
		try {
		log.info("Persisting " + dept);
		manager.getTransaction().begin();
		manager.persist(dept);
		manager.getTransaction().commit();
		}catch(Exception e) {
			log.error(e.getStackTrace().toString());
			
		}
	}
	
	
	public void updateEmployee(Employee emp){
		try {
		Employee employee=manager.find(Employee.class, emp.getId());
		if(employee!=null) {
		manager.getTransaction().begin();
		if(!(employee.getAge().equals(emp.getAge()))) 
			employee.setAge(emp.getAge());
		if(!(employee.getGender().equals(emp.getGender())))
			employee.setGender(emp.getGender());
		
		if(!(employee.getName().equals(emp.getName())))
			employee.setName(emp.getName());
		if(!(employee.getKnownLangs().equals(emp.getKnownLangs())))
			employee.setKnownLangs(emp.getKnownLangs());
		manager.getTransaction().commit();
		}
		}catch(Exception e) {
			log.error(e.getStackTrace().toString());
			
		}
	}
	
	public void updateDepartment(Department dept) {
		Department department=manager.find(Department.class, dept.getId());
		
		manager.getTransaction().begin();
		if(!(department.getDepartmentName().equals(dept.getDepartmentName())))
			department.setDepartmentName(dept.getDepartmentName());
		if(!(department.getDepartmentLeader().equals(dept.getDepartmentLeader())))
			department.setDepartmentLeader(dept.getDepartmentLeader());
		if(!(department.getRequiredLevel().equals(dept.getRequiredLevel())))
			department.setRequiredLevel(dept.getRequiredLevel());
		manager.getTransaction().commit();
	}
	
	public void deleteEmployee(Employee emp) {
		Employee employee = manager.find(Employee.class, emp.getId());

		manager.getTransaction().begin();
		manager.remove(employee);
		manager.getTransaction().commit();
		
	}
	
	public void deleteDepartment(Department dept) {
		Department department = manager.find(Department.class, dept.getId());

		manager.getTransaction().begin();
		manager.remove(department);
		manager.getTransaction().commit();
		
	}
	
	public ObservableList<Employee> getAllEmployees(){
		try {
			log.info("Persistence: Doing the workworkworkworkwork");
			ObservableList<Employee> result=FXCollections.observableArrayList();
			manager.getTransaction().begin();
			Query query=manager.createQuery("select e from Employee e");
			result.addAll(query.getResultList());
			log.debug(""+result.size());
			manager.getTransaction().commit();
			
			if(result.size()>0) {
				log.info("FOUND IT!");
				return result;
			}
		
		}catch(Exception e) {
			log.error("persistence:\n"+e.getStackTrace());
			
		}
		return null;
	}
	
	public ObservableList<Department> getAllDepartments(){
		ObservableList<Department> result=FXCollections.observableArrayList();
		manager.getTransaction().begin();
		Query query=manager.createQuery("select d from Department d");
		result.addAll(query.getResultList());
		log.debug(""+result.size());
		manager.getTransaction().commit();
		
		if(result.size()>0) {
			log.info("FOUND IT!");
			return result;
		}
		return null;
	}

	public void persistContract(Contract cont) {
		manager.getTransaction().begin();
		manager.persist(cont);
		manager.getTransaction().commit();
	}
	
	public void updateContract(Contract cont) {
		Contract contract=manager.find(Contract.class, cont.getId());
		
		manager.getTransaction().begin();
		if(cont.getDeptId()!=(contract.getDeptId()))
			contract.setDeptId(cont.getDeptId());
		manager.getTransaction().commit();	
	}
	
	public void removeContract(Contract cont) {
		manager.getTransaction().begin();
		manager.remove(cont);	
		manager.getTransaction().commit();	
	}
	
	public Contract findContractByEmployee(Employee emp) {
		manager.getTransaction().begin();
		Query query=manager.createQuery("select c from Contract c where employeeid=?1");
		query.setParameter("?1", emp.getId());
		Contract contract=(Contract)query.getSingleResult();
		manager.getTransaction().commit();
		
		return contract;
	}

	public Department getDepartmentById(long id) {
		// TODO Auto-generated method stub
		return null;
	}
}
