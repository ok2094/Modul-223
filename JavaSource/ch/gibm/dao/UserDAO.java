package ch.gibm.dao;

import java.util.HashMap;
import java.util.Map;

import ch.gibm.entity.User;

public class UserDAO extends GenericDAO<User> {

	private static final long serialVersionUID = 1L;

	public UserDAO() {
		super(User.class);
	}

	public User findUserByNameAndPassword (String username, String password) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("username", username);
		parameters.put("password", password);

		return super.findOneResult(User.FIND_USER_BY_NAME_AND_PASSWORD, parameters);
	}

	public void delete(User user) {
        	super.delete(user.getId(), User.class);
	}
}
