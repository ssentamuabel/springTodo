package org.pahappa.systems.todo.backend.core.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.sers.webutils.model.security.Permission;

/**
 * This class is responsible for converting lists and sets into well formatted strings with commas.
 * @author ttc
 *
 */
public class ListConverter {

	public static String permissionListConverter(Set<Permission> permissions) {
    	if(!permissions.isEmpty() && permissions!=null) {
    		List<String> newPermissions = new ArrayList<String>();
        	for(Permission thisPermission : permissions) {
        		newPermissions.add(thisPermission.getName());
        	}
        	return StringUtils.join(newPermissions, ", ");
    	}else {
    		return "No Access Controls Available";
    	}
    	
    }
	
}
