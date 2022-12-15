package org.pahappa.systems.todo.backend.core.services.impl;

import org.pahappa.systems.todo.backend.core.services.TodoServices;
import java.util.List;
import org.pahappa.systems.todo.backend.models.Task;
import java.util.ArrayList;





public class TodoServiceImpl implements TodoServices {

	
	    @Override
	    public void addItem(Task item) {

	  
	    	
	    	
	    	

	        
	    }

	   
	    @Override
	    public void deleteItem( String id){



	    }

	 

	    @Override
	    public List<Task> showItems(String ownerId){
	    	List<Task> items = new ArrayList<>();
	       

	        return items
	    }
}
