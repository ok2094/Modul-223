package ch.gibm.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import ch.gibm.entity.User;
import ch.gibm.facade.UserFacade;

@SessionScoped
@ManagedBean(name = "userBean")
public class UserBean extends AbstractBean implements Serializable {
	public static final String USER_NAME = "#{userBean}";
	private static final long serialVersionUID = 1L;
	private User user;
	
	private UserFacade userFacade;

	public boolean isAdmin() {
		if (user.getRole().getName().equalsIgnoreCase("admin")) {
			return true;
		} else {
			return false;
		}
	}
	
	public void updateUser() {
		try {
			getUserFacade().updateUser(user);
			closeDialog();
			displayInfoMessageToUser("Updated with success");
			user = getUser();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("A problem occurred while updating. Try again later");
			e.printStackTrace();
		}
	}

	public String logOut() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "/pages/public/login.xhtml";
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public UserFacade getUserFacade() {
		if (userFacade == null) {
			userFacade = new UserFacade();
		}

		return userFacade;
	}
	
}
