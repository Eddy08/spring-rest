package pay;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 
 * @author Harsh Bhardwaj 08
 * Never Delete a Column in a database
 *
 */

@Entity
class Employee {
	private @Id @GeneratedValue Long id;
//	private String name;
	private String firstName;
	private String lastName;
	private String role;
	Employee(){}
	
	
	public Employee(String firstName,String lastName, String role) {
		super();
//		this.name = name;
		this.firstName=firstName;
		this.lastName=lastName;
		this.role = role;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return this.firstName+" "+this.lastName;
	}
	public void setName(String name) {
		String[] parts=name.split(" ");
		this.firstName=parts[0];
		this.lastName=parts[1];
	}
	
	
	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}


	@Override
	public int hashCode() {
		return Objects.hash(firstName, id, lastName, role);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		return Objects.equals(firstName, other.firstName) && Objects.equals(id, other.id)
				&& Objects.equals(lastName, other.lastName) && Objects.equals(role, other.role);
	}


	@Override
	public String toString() {
		return "Employee [id=" + id + ", firstName=" + firstName + ", lasteName=" + lastName + ", role=" + role + "]";
	}

	
	
	
	
}
