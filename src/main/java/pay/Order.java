package pay;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CUSTOMER_ORDER")
class Order {
	private @Id @GeneratedValue Long id;
	private String description;
	private Status status;
	Order() {
	}
	public Order(String description, Status status) {
		super();
		this.description = description;
		this.status = status;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	@Override
	public int hashCode() {
		return Objects.hash(description, id);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		return Objects.equals(description, other.description) && Objects.equals(id, other.id);
	}
	@Override
	public String toString() {
		return "Orders [id=" + id + ", description=" + description + ", status"+status+"]";
	}
	
	
	
}
