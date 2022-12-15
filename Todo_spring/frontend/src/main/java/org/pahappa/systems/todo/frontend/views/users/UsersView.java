package org.pahappa.systems.todo.frontend.views.users;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.pahappa.systems.todo.backend.core.utils.CustomSearchUtils;
import org.pahappa.systems.todo.backend.core.utils.EmailClient;
import org.pahappa.systems.todo.frontend.security.HyperLinks;
import org.pahappa.systems.todo.frontend.security.UiUtils;
import org.primefaces.PrimeFaces;
import org.sers.webutils.client.views.presenters.PaginatedTableView;
import org.sers.webutils.client.views.presenters.ViewPath;
import org.sers.webutils.model.Gender;
import org.sers.webutils.model.exception.OperationFailedException;
import org.sers.webutils.model.exception.ValidationFailedException;
import org.sers.webutils.model.security.Permission;
import org.sers.webutils.model.security.Role;
import org.sers.webutils.model.security.User;
import org.sers.webutils.server.core.service.RoleService;
import org.sers.webutils.server.core.service.UserService;
import org.sers.webutils.server.core.service.excel.reports.ExcelReport;
import org.sers.webutils.server.core.utils.ApplicationContextProvider;
import org.sers.webutils.server.core.utils.MailService;
import org.sers.webutils.server.core.utils.TelephoneNumberUtils;
import org.sers.webutils.server.shared.SharedAppData;

import com.google.common.collect.Sets;

@ManagedBean(name = "usersView")
@SessionScoped
@ViewPath(path = HyperLinks.USERS_VIEW)
public class UsersView extends PaginatedTableView<User, UsersView, UsersView> {

    private static final long serialVersionUID = 1L;
    private UserService userService;
    private String searchTerm;
    private List<Gender> genders= new ArrayList<>();
    private User selectedUser;
     private MailService mailservice;
    private double customPropOneNumber;
    
    @PostConstruct
    @Override
    public void init() {
        this.userService = ApplicationContextProvider.getApplicationContext().getBean(UserService.class);
        super.setMaximumresultsPerpage(10);
        this.genders= Arrays.asList(Gender.values());
        this.customPropOneNumber = 0;
    }

    @Override
    public void reloadFromDB(int offset, int limit, Map<String, Object> filters) throws Exception {
        if (SharedAppData.getLoggedInUser() != null && SharedAppData.getLoggedInUser().hasPermission(org.sers.webutils.model.security.PermissionConstants.PERM_ADMINISTRATOR)) {
            super.setDataModels(userService.getUsers(CustomSearchUtils.generateSearchObjectForUsers(this.searchTerm, null), offset, limit));
        }
    }

    @Override
    public void reloadFilterReset() {
        if (SharedAppData.getLoggedInUser() != null && SharedAppData.getLoggedInUser().hasPermission(org.sers.webutils.model.security.PermissionConstants.PERM_ADMINISTRATOR)) {
            super.setTotalRecords(
                    this.userService.countUsers(CustomSearchUtils.generateSearchObjectForUsers(this.searchTerm, null)));
            this.resetInput();
        }
    }

    @Override
    public List<ExcelReport> getExcelReportModels() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getFileName() {
        // TODO Auto-generated method stub
        return null;
    }

    public void saveSelectedUser() {
		Set<Role> selectedRolesList = new HashSet<Role>();
		selectedRolesList.add(ApplicationContextProvider.getBean(RoleService.class).getRoleByName("Normal User"));
        this.selectedUser.setRoles(selectedRolesList);
        try {
        	final String subject = "DOCUMENT REPOSITORY ACCOUNT";
        	final String emailContent = "Hey " + this.selectedUser.getFirstName()+"! your account has been created successfully! "
        			+ "Username: " + this.selectedUser.getUsername() 
        			+ "Password: " + this.selectedUser.getClearTextPassword();
        	final String recipient = this.selectedUser.getEmailAddress();
        	
        	//this.selectedUser.setCustomPropOne(Integer.toString(customPropOneNumber));
        	this.selectedUser.setPhoneNumber(TelephoneNumberUtils.
        			getValidTelephoneNumber(this.selectedUser.getPhoneNumber()));

            //save the user
            User ourUser = userService.saveUser(this.selectedUser);
            
            ExecutorService service = Executors.newFixedThreadPool(1);
            service.submit(new Runnable() {
				@Override
				public void run() {
					try {
						//mailservice.sendMail(subject, emailContent, recipient, addressFrom, fromPassword, smtpHost, smtpPort);
						EmailClient.sendMail(recipient, subject, emailContent);
					} catch (Exception e) {
						e.printStackTrace();
					} 
				}
			});
            
            PrimeFaces.current().executeScript("PF('selected_user_dialog').hide()");
            PrimeFaces.current().ajax().update("usersView:usersTable");
            UiUtils.showMessageBox("Action successful","User account created. Log-in details sent to user's email");
           // mailservice.sendPasswordChangeMail(this.selectedUser);
            
        } catch (ValidationFailedException ex) {
            UiUtils.ComposeFailure("Action failed",ex.getLocalizedMessage());
            Logger.getLogger(UsersView.class.getName()).log(Level.SEVERE, null, ex);
        }
        reloadFilterReset();
    }
    
	public String getHostUrl() {
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		HttpServletRequest request = (HttpServletRequest) ec.getRequest();
		String hostUrl = request.getRequestURL().toString().replace(request.getRequestURI(), "")
				+ request.getContextPath();
		return hostUrl;
	}
    
    public void deleteSelectedUser(User user) throws ValidationFailedException, OperationFailedException {
       
        user.setUsername(user.getUsername()+"_deleted");
        userService.deleteUser(user);
    }

    public void loadSelectedUser(User user) {
        if (user != null) {
            this.selectedUser = user;
        } else {
            this.selectedUser = new User();
            this.customPropOneNumber = 0;
        }
    }
    
    

    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    private void resetInput() {
        this.searchTerm = "";
    }

    public User getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
    }

    public List<Gender> getGenders() {
        return genders;
    }

    public void setGenders(List<Gender> genders) {
        this.genders = genders;
    }

	public double getCustomPropOneNumber() {
		return customPropOneNumber;
	}

	public void setCustomPropOneNumber(double customPropOneNumber) {
		this.customPropOneNumber = customPropOneNumber;
	}

	public MailService getMailservice() {
		return mailservice;
	}

	public void setMailservice(MailService mailservice) {
		this.mailservice = mailservice;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
    
}
