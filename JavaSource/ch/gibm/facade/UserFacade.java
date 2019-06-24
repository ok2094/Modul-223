package ch.gibm.facade;

import java.io.Serializable;

import ch.gibm.dao.EntityManagerHelper;
import ch.gibm.dao.UserDAO;
import ch.gibm.entity.User;

public class UserFacade implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private UserDAO userDAO = new UserDAO();
	
	public User isValidLogin(String username, String password) {
		EntityManagerHelper.beginTransaction();
		User validUser = userDAO.findUserByNameAndPassword(username, password);
		EntityManagerHelper.commitAndCloseTransaction();
		return validUser;
	}
	
	public void updateUser(User user) {
		EntityManagerHelper.beginTransaction();
		userDAO.update(user);
		EntityManagerHelper.commitAndCloseTransaction();
	}
}
