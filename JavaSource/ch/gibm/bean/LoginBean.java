package ch.gibm.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import ch.gibm.entity.User;
import ch.gibm.facade.UserFacade;

@RequestScoped
@ManagedBean
public class LoginBean extends AbstractBean {
	@ManagedProperty(value = UserBean.USER_NAME)
	private UserBean userBean; // 1
	private String user; // 2
	private String password;

	// login logic
	public String login() { // 3
		UserFacade userFacade = new UserFacade(); // 4
		try {
			User user = userFacade.isValidLogin(this.user, this.password); // 5
			if (user != null) {
				userBean.setUser(user); // 6
				FacesContext context = FacesContext.getCurrentInstance();
				HttpServletRequest request = (HttpServletRequest)

				context.getExternalContext().getRequest();

				request.getSession().setAttribute("user", user); // 7
				return "/pages/protected/index.xhtml"; // 8
			}
		} catch (Exception e) {
			displayErrorMessageToUser("Check username and/or password");
		}
		return null;
	}

	public void setUserBean(UserBean userBean) { // 9
		this.userBean = userBean;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}