package org.pahappa.systems.todo.backend.models;
import javax.persistence.*;
import org.sers.webutils.model.BaseEntity;
import org.sers.webutils.model.security.User; 

@Entity
@Table(name="tasks")
public class Task extends BaseEntity{
	
	private static final long serialVersionUID = 1L;

	
	private String task;
	
	private Boolean status;
	
	// the user to which the task belongs
	private User user;
  


	@Column(name="Tasks", length=255)
	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	@Column(name="Status",length=45)
	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	@ManyToOne(targetEntity = User.class)
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	
	
	
	
}
