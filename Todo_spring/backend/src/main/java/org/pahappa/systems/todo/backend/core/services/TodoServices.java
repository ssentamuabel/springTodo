package org.pahappa.systems.todo.backend.core.services;
import org.pahappa.systems.todo.backend.models.Task;
import java.util.List;

public interface  TodoServices extends GenericService<Task> {
	
	void addItem(Task task);
	 void deleteItem(String Id);
	 List<Task>showItems(String ownerId);
}
